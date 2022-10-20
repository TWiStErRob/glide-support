package com.bumptech.glide.supportapp.stackoverflow._36642052_stetho_integration;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import android.text.TextUtils;
import android.util.Log;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.util.ContentLengthInputStream;
import com.facebook.stetho.urlconnection.StethoURLConnectionManager;

/**
 * Copy of {@link com.bumptech.glide.load.data.HttpUrlFetcher} with {@link StethoURLConnectionManager} integration.
 * Changes to original include inlining {@link com.bumptech.glide.load.data.HttpUrlFetcher.HttpUrlConnectionFactory}
 * and adding the {@link #stethoManager} calls where required.
 */
public class StethoHttpUrlFetcher implements DataFetcher<InputStream> {
	private static final String TAG = "HttpUrlFetcher";
	private static final int MAXIMUM_REDIRECTS = 5;

	private final GlideUrl glideUrl;
	private final StethoURLConnectionManager stethoManager;
	private HttpURLConnection urlConnection;
	private InputStream stream;
	private volatile boolean isCancelled;

	public StethoHttpUrlFetcher(GlideUrl glideUrl) {
		this.glideUrl = glideUrl;
		this.stethoManager = new StethoURLConnectionManager("Glide");
	}

	@Override
	public InputStream loadData(Priority priority) throws Exception {
		return loadDataWithRedirects(glideUrl.toURL(), 0 /*redirects*/, null /*lastUrl*/, glideUrl.getHeaders());
	}

	private InputStream loadDataWithRedirects(URL url, int redirects, URL lastUrl, Map<String, String> headers)
			throws IOException {
		if (redirects >= MAXIMUM_REDIRECTS) {
			throw new IOException("Too many (> " + MAXIMUM_REDIRECTS + ") redirects!");
		} else {
			// Comparing the URLs using .equals performs additional network I/O and is generally broken.
			// See http://michaelscharf.blogspot.com/2006/11/javaneturlequals-and-hashcode-make.html.
			try {
				if (lastUrl != null && url.toURI().equals(lastUrl.toURI())) {
					throw new IOException("In re-direct loop");
				}
			} catch (URISyntaxException e) {
				// Do nothing, this is best effort.
			}
		}
		urlConnection = (HttpURLConnection)url.openConnection();
		for (Map.Entry<String, String> headerEntry : headers.entrySet()) {
			urlConnection.addRequestProperty(headerEntry.getKey(), headerEntry.getValue());
		}
		//urlConnection.setRequestProperty("Accept-Encoding", "gzip"); // don't request, it's wasteful for images
		urlConnection.setConnectTimeout(2500);
		urlConnection.setReadTimeout(2500);
		urlConnection.setUseCaches(false);
		urlConnection.setDoInput(true);

		stethoManager.preConnect(urlConnection, null);
		try {
			// Connect explicitly to avoid errors in decoders if connection fails.
			urlConnection.connect();
			stethoManager.postConnect();
		} catch (IOException ex) {
			stethoManager.httpExchangeFailed(ex);
			throw ex;
		}
		if (isCancelled) {
			return null;
		}
		final int statusCode = urlConnection.getResponseCode();
		if (statusCode / 100 == 2) {
			return getStreamForSuccessfulRequest(urlConnection);
		} else if (statusCode / 100 == 3) {
			String redirectUrlString = urlConnection.getHeaderField("Location");
			if (TextUtils.isEmpty(redirectUrlString)) {
				throw new IOException("Received empty or null redirect url");
			}
			URL redirectUrl = new URL(url, redirectUrlString);
			return loadDataWithRedirects(redirectUrl, redirects + 1, url, headers);
		} else {
			if (statusCode == -1) {
				throw new IOException("Unable to retrieve response code from HttpUrlConnection.");
			}
			throw new IOException("Request failed " + statusCode + ": " + urlConnection.getResponseMessage());
		}
	}

	private InputStream getStreamForSuccessfulRequest(HttpURLConnection urlConnection) throws IOException {
		try {
			InputStream responseStream = stethoManager.interpretResponseStream(urlConnection.getInputStream());
			if (TextUtils.isEmpty(urlConnection.getContentEncoding())) {
				int contentLength = urlConnection.getContentLength();
				stream = ContentLengthInputStream.obtain(responseStream, contentLength);
			} else {
				if (Log.isLoggable(TAG, Log.DEBUG)) {
					Log.d(TAG, "Got non empty content encoding: " + urlConnection.getContentEncoding());
				}
				stream = responseStream;
			}
			return stream;
		} catch (IOException ex) {
			stethoManager.httpExchangeFailed(ex);
			throw ex;
		}
	}

	@Override public void cleanup() {
		if (stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				// Ignore
			}
		}
		if (urlConnection != null) {
			urlConnection.disconnect();
		}
		if (isCancelled) {
			stethoManager.httpExchangeFailed(new IOException("Cancelled"));
		}
	}

	@Override public String getId() {
		return glideUrl.getCacheKey();
	}

	@Override public void cancel() {
		// TODO: we should consider disconnecting the url connection here, but we can't do so directly because cancel is
		// often called on the main thread.
		isCancelled = true;
	}
}

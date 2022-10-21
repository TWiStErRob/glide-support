package com.bumptech.glide.supportapp.github._864_staggered_grid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.supportapp.utils.UsefulAsyncTaskLoader;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

class PicasaFeedLoader extends UsefulAsyncTaskLoader<JSONArray> {
	private static final String TAG = "FeedLoader";

	public PicasaFeedLoader(Context context) {
		super(context);
	}
	@Override public JSONArray loadInBackground() {
		try {
			Log.i(TAG, "Loading featured feed...");
			// TODO change to loadFile and download the feed once into src/main/assets/feed.json if you want faster loading
			String json = loadNetwork();
			JSONObject feed = new JSONObject(json);
			return feed.getJSONObject("feed").getJSONArray("entry");
		} catch (Exception ex) {
			Log.w(TAG, ex.toString(), ex);
			return new JSONArray();
		}
	}

	private String loadNetwork() throws IOException {
		OkHttpClient client = new OkHttpClient.Builder()
				.connectTimeout(5, TimeUnit.SECONDS)
				.readTimeout(5, TimeUnit.SECONDS)
				.writeTimeout(5, TimeUnit.SECONDS)
				.build();
		Request request = new Request.Builder()
				.url("https://picasaweb.google.com/data/feed/api/featured?alt=json")
				.build();
		Response response = client.newCall(request).execute();
		Log.d(TAG, "Got response: " + response);
		if (!response.isSuccessful()) {
			throw new IOException("Unexpected response: " + response);
		}
		return response.body().string();
	}

	private String loadFile() throws IOException {
		InputStream stream = null;
		try {
			stream = getContext().getAssets().open("feed.json");
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException ignore) {
				}
			}
		}
	}
}

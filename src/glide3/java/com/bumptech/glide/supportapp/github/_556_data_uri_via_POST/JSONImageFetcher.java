package com.bumptech.glide.supportapp.github._556_data_uri_via_POST;

import java.io.*;
import java.net.*;

import javax.net.ssl.HttpsURLConnection;

import org.json.*;

import android.util.Base64;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.supportapp.*;

class JSONImageFetcher implements DataFetcher<InputStream> {
	private final Image model;

	public JSONImageFetcher(Image model) {
		this.model = model;
	}

	private HttpURLConnection conn;
	private InputStream response;

	@Override public InputStream loadData(Priority priority) throws Exception {
		// This implementation is totally up to you
		// TODO produce an InputStream that contains the raw image bytes that can be decoded (below is just an example)
		URL url = new URL(model.getUri());
		conn = (HttpURLConnection)url.openConnection();
		try {
			conn.setReadTimeout(15000);
			conn.setConnectTimeout(15000);
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");

			JSONObject payload = model.getPayload();
			// TODO remove this, only for testing with httpbin to have a proper response
			payload.put("imageData", "data:image/png,base64," + App.getInstance().getString(R.string.glide_base64));
			OutputStream request = conn.getOutputStream();
			try {
				request.write(payload.toString().getBytes("UTF-8"));
			} finally {
				request.close();
			}
			int responseCode = conn.getResponseCode();
			if (responseCode == HttpsURLConnection.HTTP_OK) {
				response = conn.getInputStream();
				try {
					JSONObject object = readJSON(response);
					// TODO remove this, only needed for testing with httpbin
					object = new JSONObject(object.getString("data"));
					String dataUri = object.getString("imageData");
					String base64 = dataUri.substring("data:image/png,base64,".length());
					byte[] raw = Base64.decode(base64, Base64.NO_WRAP);
					return new ByteArrayInputStream(raw);
				} finally {
					response.close();
				}
			} else {
				throw new IOException("Invalid response: " + responseCode);
			}
		} finally {
			conn.disconnect();
		}
	}

	private JSONObject readJSON(InputStream stream) throws IOException, JSONException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
		StringBuilder builder = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			builder.append(line);
		}
		return new JSONObject(builder.toString());
	}
	@Override public void cleanup() {
		if (response != null) {
			try {
				response.close();
			} catch (IOException e) {
				// Ignore
			}
		}
		if (conn != null) {
			conn.disconnect();
		}
	}
	@Override public String getId() {
		return model.getKey();
	}
	@Override public void cancel() {

	}
}

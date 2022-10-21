package com.bumptech.glide.supportapp.github._556_data_uri_via_POST;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class Image {
	private final String key;
	public Image(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public String getUri() throws IOException {
		return "http://httpbin.org/post";
	}

	public JSONObject getPayload() throws IOException {
		try {
			JSONObject object = new JSONObject();
			object.put("imageKey", key);
			return object;
		} catch (JSONException e) {
			throw new IOException("Invalid implementation", e);
		}
	}

	@Override public String toString() {
		return "Image for key=" + key;
	}
}

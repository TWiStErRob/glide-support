package com.bumptech.glide.supportapp.github._556_data_uri_from_string;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import android.util.Base64;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;

class Base64StreamDataFetcher implements DataFetcher<InputStream> {
	private final String base64;
	public Base64StreamDataFetcher(String base64) {
		this.base64 = base64;
	}
	@Override public InputStream loadData(Priority priority) throws Exception {
		// depending on how you encoded it, the below is just a guess:
		// here the full base64 is decoded into bytes and the bytes will be parsed by Glide
		// TODO match the flags based on your possible inputs: e.g. add | Base64.URL_SAFE
		byte[] raw = Base64.decode(base64, Base64.NO_WRAP);
		return new ByteArrayInputStream(raw);
		// ---- alternative
		// if you don't want to delay decoding you can use something like:
		// Base64InputStream (http://stackoverflow.com/a/19981216/253468)
		// here the base64 bytes are passed to Base64InputStream and that to Glide
		// so base64 decoding will be done later when Glide reads from the stream.
		//return new Base64InputStream(new ByteArrayInputStream(base64.getBytes("utf-8")), Base64.NO_WRAP | Base64.URL_SAFE);
	}
	@Override public String getId() {
		// not well-cacheable, I suggest to use .diskCacheStrategy(NONE)
		return base64;
	}
	@Override public void cancel() {
	}
	@Override public void cleanup() {
	}
}

package com.bumptech.glide.supportapp.github._921_different_headers;

import java.io.InputStream;

import android.content.Context;

import com.bumptech.glide.load.model.*;
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;

public class OtherModel extends UrlModel {
	public OtherModel(String url) {
		super(url);
	}
}

class OtherLoader extends BaseGlideUrlLoader<AuthModel> {
	private LazyHeaders headers;
	public OtherLoader(final Context context) {
		super(context);
		this.headers = createHeaders(context).build();
	}
	@Override protected String getUrl(AuthModel model, int width, int height) {
		return model.url;
	}
	@Override protected Headers getHeaders(AuthModel model, int width, int height) {
		return headers;
	}

	public static LazyHeaders.Builder createHeaders(final Context context) {
		return new LazyHeaders.Builder()
				.addHeader(HeadersContract.HEADER_CLIENT_ID, HeadersContract.CLIENT_ID)
				.addHeader(HeadersContract.HEADER_AUTHORIZATION, "static auth");
	}

	public static class Factory implements ModelLoaderFactory<AuthModel, InputStream> {
		@Override public ModelLoader<AuthModel, InputStream> build(Context context, GenericLoaderFactory factories) {
			return new OtherLoader(context);
		}
		@Override public void teardown() {
		}
	}
}

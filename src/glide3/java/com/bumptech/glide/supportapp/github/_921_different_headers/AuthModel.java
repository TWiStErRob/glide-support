package com.bumptech.glide.supportapp.github._921_different_headers;

import java.io.InputStream;

import android.content.Context;

import com.bumptech.glide.load.model.*;
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;

public class AuthModel extends UrlModel {
	public AuthModel(String url) {
		super(url);
	}
}

class AuthLoader extends BaseGlideUrlLoader<AuthModel> {
	private LazyHeaders.Builder authHeaders;
	public AuthLoader(final Context context) {
		super(context);
		this.authHeaders = createHeaders(context);
	}
	@Override protected String getUrl(AuthModel model, int width, int height) {
		return model.url;
	}
	@Override protected Headers getHeaders(AuthModel model, int width, int height) {
		return authHeaders.build();
	}

	public static LazyHeaders.Builder createHeaders(final Context context) {
		return new LazyHeaders.Builder()
				.addHeader(HeadersContract.HEADER_CLIENT_ID, HeadersContract.CLIENT_ID)
				.addHeader(HeadersContract.HEADER_AUTHORIZATION, new LazyHeaderFactory() {
					private final TokenManager manager = TokenManager.getInstance(context);
					@Override public String buildHeader() {
						try {
							String accessToken = manager.getToken();
							return HeadersContract.O_AUTH_AUTHENTICATION + accessToken;
						} catch (InterruptedException ex) {
							Thread.currentThread().interrupt(); // see http://stackoverflow.com/a/3976377/253468
							ex.printStackTrace();
							return null; // you may want to throw an unchecked exception here to stop the loading process
							// no need for a server roundtrip to get a HTTP 403, we know here the token is bad!
						}
					}
				});
	}

	public static class Factory implements ModelLoaderFactory<AuthModel, InputStream> {
		@Override public ModelLoader<AuthModel, InputStream> build(Context context, GenericLoaderFactory factories) {
			return new AuthLoader(context);
		}
		@Override public void teardown() {
		}
	}
}


package com.bumptech.glide.supportapp.github._471_global_header;

import java.io.InputStream;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;
import com.bumptech.glide.load.model.stream.StreamModelLoader;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class GlideModule implements com.bumptech.glide.module.GlideModule {

	@Override public void applyOptions(Context context, GlideBuilder builder) {
	}

	@Override public void registerComponents(Context context, Glide glide) {
		// just to see the headers actually went through, Stetho or proxy can also be used for this
		glide.register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(new OkHttpClient.Builder()
				.addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
				.build()));
		// override default loader with one that attaches headers
		glide.register(String.class, InputStream.class, new HeaderedLoader.Factory());
	}

	private static class HeaderedLoader extends BaseGlideUrlLoader<String> {

		private static final String APP_AGENT = "My App Agent";
		private static final String USER_AGENT = "My User Agent";
		public static final Headers HEADERS = new LazyHeaders.Builder()
				.addHeader("User-Agent", USER_AGENT)
				.addHeader("App-Agent", APP_AGENT)
				.build();

		public HeaderedLoader(Context context) {
			super(context);
		}

		@Override protected String getUrl(String model, int width, int height) {
			return model;
		}

		@Override protected Headers getHeaders(String model, int width, int height) {
			return HEADERS;
		}

		public static class Factory implements ModelLoaderFactory<String, InputStream> {

			@Override public StreamModelLoader<String> build(Context context, GenericLoaderFactory factories) {
				return new HeaderedLoader(context);
			}

			@Override public void teardown() { /* nothing to free */ }
		}
	}
}

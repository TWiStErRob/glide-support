package com.bumptech.glide.supportapp.github._471_global_header;

import java.io.InputStream;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;
import com.bumptech.glide.module.LibraryGlideModule;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@com.bumptech.glide.annotation.GlideModule
public class GlideModule extends LibraryGlideModule implements com.bumptech.glide.module.GlideModule {

	@Override public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
	}

	@Override public void registerComponents(
			@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
		// just to see the headers actually went through, Stetho or proxy can also be used for this
		registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(new OkHttpClient.Builder()
				.addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
				.build()));
		// override default loader with one that attaches headers
		registry.replace(String.class, InputStream.class, new HeaderedLoader.Factory());
	}

	private static class HeaderedLoader extends BaseGlideUrlLoader<String> {

		private static final String APP_AGENT = "My App Agent";
		private static final String USER_AGENT = "My User Agent";
		public static final Headers HEADERS = new LazyHeaders.Builder()
				.addHeader("User-Agent", USER_AGENT)
				.addHeader("App-Agent", APP_AGENT)
				.build();

		public HeaderedLoader(ModelLoader<GlideUrl, InputStream> concreteLoader) {
			super(concreteLoader);
		}

		@Override public boolean handles(@NonNull String model) {
			return true;
		}

		@Override protected String getUrl(String model, int width, int height, Options options) {
			return model;
		}

		@Override protected Headers getHeaders(String model, int width, int height, Options options) {
			return HEADERS;
		}

		public static class Factory implements ModelLoaderFactory<String, InputStream> {

			@Override public @NonNull ModelLoader<String, InputStream> build(
					@NonNull MultiModelLoaderFactory multiFactory) {
				return new HeaderedLoader(multiFactory.build(GlideUrl.class, InputStream.class));
			}

			@Override public void teardown() { /* nothing to free */ }
		}
	}
}

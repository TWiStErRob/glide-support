package com.bumptech.glide.supportapp.github._1257_cache_304;

import java.io.*;

import android.content.Context;

import com.bumptech.glide.*;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;

public class GlideModule implements com.bumptech.glide.module.GlideModule {
	public static final int IMAGE_CACHE_SIZE = 10 * 1024 * 1024;

	@Override public void applyOptions(Context context, GlideBuilder builder) {
	}

	@Override public void registerComponents(Context context, Glide glide) {
		Stetho.initializeWithDefaults(context);
		final Cache cache = new Cache(new File(context.getCacheDir(), "okhttp"), IMAGE_CACHE_SIZE);

		HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
//		logger.setLevel(Level.BASIC);

		OkHttpClient client = new OkHttpClient()
				.newBuilder()
				.cache(cache)
				.addNetworkInterceptor(new StethoInterceptor())
				.addInterceptor(logger)
				.build();

		glide.register(CachedGlideUrl.class, InputStream.class,
				superFactory(new OkHttpUrlLoader.Factory(client), CachedGlideUrl.class));
		glide.register(ForceLoadGlideUrl.class, InputStream.class,
				superFactory(new OkHttpUrlLoader.Factory(client), ForceLoadGlideUrl.class));
	}

	/**
	 * {@link Glide#register(Class, Class, ModelLoaderFactory) Register}'s params should be
	 * {@code (Class<T>, Class<Y>, ModelLoaderFactory<? super T, Y>)}. This method works around that.
	 */
	@SuppressWarnings({"unchecked", "unused"})
	private static <T> ModelLoaderFactory<T, InputStream> superFactory(
			ModelLoaderFactory<? super T, InputStream> factory, Class<T> modelType) {
		return (ModelLoaderFactory<T, InputStream>)factory;
	}
}

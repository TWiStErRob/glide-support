package com.bumptech.glide.supportapp.github._1133_stetho_integration;

import java.io.InputStream;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.LibraryGlideModule;

@com.bumptech.glide.annotation.GlideModule
public class GlideModule extends LibraryGlideModule implements com.bumptech.glide.module.GlideModule {

	@Override public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
	}

	@Override public void registerComponents(
			@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
		super.registerComponents(context, glide, registry);
		com.facebook.stetho.Stetho.initializeWithDefaults(context);

		okhttp3.OkHttpClient client = new okhttp3.OkHttpClient.Builder()
				.addNetworkInterceptor(new com.facebook.stetho.okhttp3.StethoInterceptor())
				.build();
		registry.replace(GlideUrl.class, InputStream.class,
				new com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader.Factory(client));
	}
}

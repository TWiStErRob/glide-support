package com.bumptech.glide.supportapp.github._1133_stetho_integration;

import java.io.InputStream;

import android.content.Context;

import com.bumptech.glide.*;
import com.bumptech.glide.load.model.GlideUrl;

// http://arnaud-camus.fr/combining-glide-and-stetho-to-easily-debug-your-image-loading-system/
public class GlideModule implements com.bumptech.glide.module.GlideModule {
	@Override public void applyOptions(Context context, GlideBuilder builder) {
	}

	@Override public void registerComponents(Context context, Glide glide) {
		com.facebook.stetho.Stetho.initializeWithDefaults(context);

		okhttp3.OkHttpClient client = new okhttp3.OkHttpClient.Builder()
				.addNetworkInterceptor(new com.facebook.stetho.okhttp3.StethoInterceptor())
				.build();
		glide.register(GlideUrl.class, InputStream.class,
				new com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader.Factory(client));
	}
}

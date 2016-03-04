package com.bumptech.glide.supportapp;

import android.content.Context;

import com.bumptech.glide.*;
import com.bumptech.glide.module.GlideModule;

public class QuickModule implements GlideModule {
	@Override public void applyOptions(Context context, GlideBuilder builder) {
//		builder.setDiskCache(new ExternalCacheDiskCacheFactory(context));
	}
	@Override public void registerComponents(Context context, Glide glide) {
//		glide.register(GlideUrl.class, InputStream.class, new HttpUrlGlideUrlLoader.Factory());
//		OkHttpClient client = new OkHttpClient.Builder()
//				.connectTimeout(15, TimeUnit.SECONDS)
//				.readTimeout(15, TimeUnit.SECONDS)
//				.build();
//		glide.register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(client));
	}
}

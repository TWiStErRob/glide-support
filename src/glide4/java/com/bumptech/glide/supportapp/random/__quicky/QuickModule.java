package com.bumptech.glide.supportapp.random.__quicky;

import android.content.Context;

import com.bumptech.glide.*;
import com.bumptech.glide.module.GlideModule;

public class QuickModule implements GlideModule {
	@Override public void applyOptions(Context context, GlideBuilder builder) {
//		builder.setDiskCache(new ExternalCacheDiskCacheFactory(context));
	}
	@Override public void registerComponents(Context context, Glide glide, Registry registry) {
//		registry.replace(GlideUrl.class, InputStream.class, new HttpGlideUrlLoader.Factory());
//		OkHttpClient client = new OkHttpClient.Builder()
//				.connectTimeout(15, TimeUnit.SECONDS)
//				.readTimeout(15, TimeUnit.SECONDS)
//				.build();
//		registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(client));
	}
}

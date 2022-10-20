package com.bumptech.glide.supportapp.random.__quicky;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.module.GlideModule;
import com.bumptech.glide.module.LibraryGlideModule;

import androidx.annotation.NonNull;

@com.bumptech.glide.annotation.GlideModule
public class QuickModule extends LibraryGlideModule implements GlideModule {

	@Override public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
//		builder.setDiskCache(new ExternalCacheDiskCacheFactory(context));
	}

	@Override public void registerComponents(
			@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
//		registry.replace(GlideUrl.class, InputStream.class, new HttpGlideUrlLoader.Factory());
//		OkHttpClient client = new OkHttpClient.Builder()
//				.connectTimeout(15, TimeUnit.SECONDS)
//				.readTimeout(15, TimeUnit.SECONDS)
//				.build();
//		registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(client));
	}
}

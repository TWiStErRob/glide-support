package com.bumptech.glide.supportapp.github._1272_palette_cache;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;

public class GlideModule implements com.bumptech.glide.module.GlideModule {
	@Override public void applyOptions(Context context, GlideBuilder builder) {
		// just so it's easier to access the cache (public)
		builder.setDiskCache(new ExternalCacheDiskCacheFactory(context));
	}
	@Override public void registerComponents(Context context, Glide glide) {
	}
}

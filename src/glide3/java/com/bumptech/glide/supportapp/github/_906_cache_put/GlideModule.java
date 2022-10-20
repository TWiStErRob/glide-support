package com.bumptech.glide.supportapp.github._906_cache_put;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.supportapp.utils.StealingDiskCacheFactory;

public class GlideModule implements com.bumptech.glide.module.GlideModule {
	@Override public void applyOptions(Context context, GlideBuilder builder) {
		builder.setDiskCache(new StealingDiskCacheFactory(new InternalCacheDiskCacheFactory(context)));
	}
	@Override public void registerComponents(Context context, Glide glide) {

	}
}

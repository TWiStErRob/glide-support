package com.bumptech.glide.supportapp.utils;

import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskCache.Factory;
import com.bumptech.glide.supportapp.App;

public class StealingDiskCacheFactory implements DiskCache.Factory {
	private final Factory factory;
	public StealingDiskCacheFactory(Factory factory) {
		this.factory = factory;
	}
	@Override public DiskCache build() {
		DiskCache cache = factory.build();
		App.getInstance().setDiskCache(cache);
		return cache;
	}
}

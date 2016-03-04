package com.bumptech.glide.supportapp.github._886_custom_memory_cache;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.*;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;

public class GlideModule implements com.bumptech.glide.module.GlideModule {
	@Override public void applyOptions(Context context, GlideBuilder builder) {
		MemorySizeCalculator calculator = new MemorySizeCalculator(context);
		int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
		int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

		Log.i("vipin_apply", String.valueOf(defaultMemoryCacheSize));
		builder.setMemoryCache(new LruResourceCacheInternal(defaultMemoryCacheSize / 2));
		builder.setBitmapPool(new LruBitmapPool(defaultBitmapPoolSize / 2));
	}

	@Override public void registerComponents(Context context, Glide glide) {
	}
}


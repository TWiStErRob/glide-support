package com.bumptech.glide.supportapp.github._601_wrong_position;

import android.content.Context;

import com.bumptech.glide.*;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.MemoryCacheAdapter;

public class GlideModule implements com.bumptech.glide.module.GlideModule {
	@Override public void applyOptions(Context context, GlideBuilder builder) {
		builder.setMemoryCache(new MemoryCacheAdapter()); // makes it easier to reproduce
		//builder.setBitmapPool(new BitmapPoolAdapter()); // fixes the issue, but not in a good way
		builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565); // default, but seems important to the issue
	}
	@Override public void registerComponents(Context context, Glide glide) {

	}
}

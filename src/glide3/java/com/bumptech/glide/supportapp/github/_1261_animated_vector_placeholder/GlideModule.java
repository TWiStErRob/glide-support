package com.bumptech.glide.supportapp.github._1261_animated_vector_placeholder;

import android.content.Context;

import com.bumptech.glide.*;
import com.bumptech.glide.load.engine.executor.FifoPriorityThreadPoolExecutor;

public class GlideModule implements com.bumptech.glide.module.GlideModule {
	@Override public void applyOptions(Context context, GlideBuilder builder) {
		// debug: make sure there's enough threads so delay transformations don't block the others
		builder.setDiskCacheService(new FifoPriorityThreadPoolExecutor(20));
		builder.setResizeService(new FifoPriorityThreadPoolExecutor(20));
	}
	@Override public void registerComponents(Context context, Glide glide) {

	}
}

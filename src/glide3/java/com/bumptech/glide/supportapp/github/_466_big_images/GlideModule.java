package com.bumptech.glide.supportapp.github._466_big_images;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.executor.FifoPriorityThreadPoolExecutor;

public class GlideModule implements com.bumptech.glide.module.GlideModule {
	@Override public void applyOptions(Context context, GlideBuilder builder) {
		//if (VERSION.SDK_INT == VERSION_CODES.KITKAT)
		builder.setResizeService(new FifoPriorityThreadPoolExecutor(4));
	}
	@Override public void registerComponents(Context context, Glide glide) {

	}
}

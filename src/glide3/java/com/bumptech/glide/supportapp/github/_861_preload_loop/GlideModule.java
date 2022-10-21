package com.bumptech.glide.supportapp.github._861_preload_loop;

import java.io.InputStream;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;

public class GlideModule implements com.bumptech.glide.module.GlideModule {
	@Override public void applyOptions(Context context, GlideBuilder builder) {

	}
	@Override public void registerComponents(Context context, Glide glide) {
		glide.register(Item.class, InputStream.class, new ItemLoader.Factory());
	}
}

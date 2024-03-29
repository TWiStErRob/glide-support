package com.bumptech.glide.supportapp.github._832_raw;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.bumptech.glide.module.GlideModule;

public class RawModule implements GlideModule {
	@Override public void applyOptions(Context context, GlideBuilder builder) {
		builder.setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context));
	}
	@Override public void registerComponents(Context context, Glide glide, Registry registry) {
		registry.prepend(File.class, Bitmap.class, new RawFileDecoder(glide.getBitmapPool()));
	}
}

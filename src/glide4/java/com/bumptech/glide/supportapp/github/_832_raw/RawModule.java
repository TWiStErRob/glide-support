package com.bumptech.glide.supportapp.github._832_raw;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.*;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.module.GlideModule;

public class RawModule implements GlideModule {
	@Override public void applyOptions(Context context, GlideBuilder builder) {
		builder.setDiskCache(new ExternalCacheDiskCacheFactory(context));
	}
	@Override public void registerComponents(Context context, Registry registry) {
		registry.prepend(File.class, Bitmap.class, new RawFileDecoder(Glide.get(context).getBitmapPool()));
	}
}

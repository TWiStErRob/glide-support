package com.bumptech.glide.supportapp.github._699_mp3_cover;

import java.io.InputStream;

import android.content.Context;

import com.bumptech.glide.*;
import com.bumptech.glide.load.engine.cache.MemoryCacheAdapter;
import com.bumptech.glide.module.GlideModule;

public class AudioCoverModule implements GlideModule {
	@Override public void applyOptions(Context context, GlideBuilder builder) {
		builder.setMemoryCache(new MemoryCacheAdapter());
	}
	@Override public void registerComponents(Context context, Glide glide) {
		glide.register(AudioCover.class, InputStream.class, new AudioCoverLoader.Factory());
	}
}

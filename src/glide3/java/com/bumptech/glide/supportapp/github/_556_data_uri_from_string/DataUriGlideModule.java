package com.bumptech.glide.supportapp.github._556_data_uri_from_string;

import java.io.InputStream;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.module.GlideModule;

// TODO https://github.com/bumptech/glide/wiki/Configuration#creating-a-glidemodule
public class DataUriGlideModule implements GlideModule {
	@Override public void applyOptions(Context context, GlideBuilder builder) {

	}
	@Override public void registerComponents(Context context, Glide glide) {
		glide.register(String.class, InputStream.class, new DataUriModelLoader.Factory());
	}
}

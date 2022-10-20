package com.bumptech.glide.supportapp.github._556_data_uri_via_POST;

import java.io.InputStream;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.module.GlideModule;

// TODO https://github.com/bumptech/glide/wiki/Configuration#creating-a-glidemodule
public class RemoteImageGlideModule implements GlideModule {
	@Override public void applyOptions(Context context, GlideBuilder builder) {

	}
	@Override public void registerComponents(Context context, Glide glide) {
		glide.register(Image.class, InputStream.class, new ImageModelLoader.Factory());
	}
}

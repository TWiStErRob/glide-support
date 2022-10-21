package com.bumptech.glide.supportapp.stackoverflow._36642052_stetho_integration;

import java.io.InputStream;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.model.GlideUrl;

public class GlideModule implements com.bumptech.glide.module.GlideModule {
	@Override public void applyOptions(Context context, GlideBuilder builder) {
	}

	@Override public void registerComponents(Context context, Glide glide) {
		com.facebook.stetho.Stetho.initializeWithDefaults(context);
		glide.register(GlideUrl.class, InputStream.class, new StethoHttpUrlGlideUrlLoader.Factory());
	}
}

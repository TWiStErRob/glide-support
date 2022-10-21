package com.bumptech.glide.supportapp.github._876_wifi_only;

import java.io.InputStream;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.model.GlideUrl;

public class GlideModule implements com.bumptech.glide.module.GlideModule {
	@Override public void applyOptions(Context context, GlideBuilder builder) {

	}
	@Override public void registerComponents(Context context, Glide glide) {
		glide.register(GlideUrl.class, InputStream.class, new WifiOnlyLoader.Factory());
	}
}

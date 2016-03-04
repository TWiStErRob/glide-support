package com.bumptech.glide.supportapp.github._921_different_headers;

import java.io.InputStream;

import android.content.Context;

import com.bumptech.glide.*;

public class GlideModule implements com.bumptech.glide.module.GlideModule {
	@Override public void applyOptions(Context context, GlideBuilder builder) {
	}
	@Override public void registerComponents(Context context, Glide glide) {
		glide.register(AuthModel.class, InputStream.class, new AuthLoader.Factory());
		//glide.register(OtherModel.class, InputStream.class, new OtherLoader.Factory());
	}
}

package com.bumptech.glide.supportapp.github._1214_abstractmethoderror;

import java.io.InputStream;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.integration.okhttp3.OkHttpGlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;

import okhttp3.OkHttpClient;

public class GlobalOkHttpGlideModule extends OkHttpGlideModule {
	@Override public void registerComponents(Context context, Glide glide, Registry registry) {
		okhttp3.OkHttpClient client = new OkHttpClient.Builder().build();
		registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(client));
	}
}

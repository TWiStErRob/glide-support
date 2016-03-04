package com.bumptech.glide.supportapp.github._556_data_uri;

import java.io.InputStream;

import android.content.Context;

import com.bumptech.glide.*;
import com.bumptech.glide.module.GlideModule;
import com.firebase.client.Firebase;

// TODO https://github.com/bumptech/glide/wiki/Configuration#creating-a-glidemodule
class FirebaseGlideModule implements GlideModule {
	@Override public void applyOptions(Context context, GlideBuilder builder) {

	}
	@Override public void registerComponents(Context context, Glide glide) {
		Firebase fb = new Firebase("https://<YOUR-FIREBASE-APP>.firebaseio.com");
		fb.authWithPassword("", "", null); // TODO fire auth to background if needed, see FirebaseImageModelLoader ctor
		glide.register(FirebaseImage.class, InputStream.class, new FirebaseModelLoader.Factory(fb));
	}
}

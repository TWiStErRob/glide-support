package com.bumptech.glide.supportapp.github._1133_stetho_integration;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.supportapp.GlideDualImageFragment;
import com.bumptech.glide.supportapp.utils.LoggingListener;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.*;

public class TestFragment extends GlideDualImageFragment {
	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setUsage("To try this out:"
				+ LI + "Enable remote debugging (probably already have):" 
				+ "https://developer.chrome.com/devtools/docs/remote-debugging"
				+ LI + "go to <a href=#>chrome://inspect/#devices</a> on your computer"
				+ LI + "click inspect under application and go to the Network tab"
				+ "<p>Caches are disabled for the first image so the request will always show up,"
				+ "the second is served from RESULT cache, so it may show up on rotate.");
	}

	@Override protected void load1(Context context, ImageView imageView) throws Exception {
		Glide
				.with(this)
				.load("http://facebook.github.io/stetho/static/logo.png?first")
				.diskCacheStrategy(NONE)
				.skipMemoryCache(true)
				.listener(new LoggingListener<String, GlideDrawable>())
				.into(imageView)
		;
	}

	@Override protected void load2(Context context, ImageView imageView) throws Exception {
		Glide
				.with(this)
				.load("http://facebook.github.io/stetho/static/logo.png?second")
				.listener(new LoggingListener<String, GlideDrawable>())
				.into(imageView)
		;
	}
}

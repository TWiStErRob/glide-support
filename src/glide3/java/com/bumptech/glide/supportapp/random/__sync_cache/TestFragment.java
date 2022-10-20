package com.bumptech.glide.supportapp.random.__sync_cache;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.supportapp.GlideImageFragment;
import com.bumptech.glide.supportapp.R;
import com.bumptech.glide.supportapp.utils.LoggingListener;

public class TestFragment extends GlideImageFragment {
	@Override protected void load(Context context) {
		SyncLoadImageViewTarget target = Glide
				.with(this)
				.load("http://placehold.it/1600x900/ff0000/00ff00&text=image")
				.diskCacheStrategy(DiskCacheStrategy.ALL)
				.placeholder(R.drawable.glide_placeholder)
				.listener(new LoggingListener<String, GlideDrawable>())
				.into(new SyncLoadImageViewTarget(imageView));

		Log.d("isLoaded", target.isLoaded() + "");
		if (!target.isLoaded()) {
			//Glide.clear(target); NOT CLEARING ANYMORE
		}
	}
}

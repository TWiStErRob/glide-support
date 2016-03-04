package com.bumptech.glide.supportapp.random;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.supportapp.*;
import com.bumptech.glide.supportapp.utils.SyncLoadImageViewTarget;

public class Test_SyncCacheLoad extends GlideImageFragment {
	@Override protected void load(Context context) {
		SyncLoadImageViewTarget target = Glide
				.with(this)
				.load("http://placehold.it/1600x900/ff0000/00ff00&text=image")
				.diskCacheStrategy(DiskCacheStrategy.ALL)
				.placeholder(R.drawable.glide_placeholder)
				.listener(new RequestListener<String, GlideDrawable>() {
					@Override public boolean onException(Exception e, String model, Target<GlideDrawable> target,
							boolean isFirstResource) {
						return false;
					}

					@Override public boolean onResourceReady(GlideDrawable resource, String model,
							Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
						Log.d("resource ready", "isFromMemoryCache: " + isFromMemoryCache);
						return false;
					}
				})
				.into(new SyncLoadImageViewTarget(imageView));

		Log.d("isLoaded", target.isLoaded() + "");
		if (!target.isLoaded()) {
			//Glide.clear(target); NOT CLEARING ANYMORE
		}
	}
}

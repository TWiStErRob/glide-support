package com.bumptech.glide.supportapp.github._913_gif_stops;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.*;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.supportapp.*;
import com.bumptech.glide.supportapp.utils.LoggingListener;

public class TestFragment extends GlideImageFragment {
	@Override public @Nullable View onCreateView(
			LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.github_913, container, false);
	}
	@Override protected void load(Context context) throws Exception {
		Glide
				.with(this)
				.load("http://searchengineland.com/figz/wp-content/seloads/2012/04/penguin.jpg")
				.listener(new LoggingListener<>(new RequestListener<String, GlideDrawable>() {
					@Override public boolean onException(Exception e, String model,
							Target<GlideDrawable> target, boolean isFirstResource) {
						return false;
					}
					@Override public boolean onResourceReady(GlideDrawable resource, String model,
							Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
						Glide
								.with(TestFragment.this)
								.load("http://bestanimations.com/Animals/Birds/Penguins/animated-penguin-gif-5.gif")
								.diskCacheStrategy(DiskCacheStrategy.SOURCE)
								.placeholder(resource)
								.skipMemoryCache(true)
								.listener(new LoggingListener<String, GlideDrawable>())
								.into(imageView);
						return false;
					}
				}))
				.into(imageView);
	}
}


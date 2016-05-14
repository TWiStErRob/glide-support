package com.bumptech.glide.supportapp.github._556_data_uri_from_string;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.supportapp.*;
import com.bumptech.glide.supportapp.utils.LoggingListener;

public class TestFragment extends GlideImageFragment {
	@Override protected void load(final Context context) {
		Glide
				.with(context)
				.load("data:image/png;base64," + getString(R.string.glide_base64))
				.placeholder(R.drawable.glide_placeholder)
				.error(R.drawable.glide_error)
				.diskCacheStrategy(DiskCacheStrategy.NONE)
				.skipMemoryCache(false)
				.listener(new LoggingListener<String, GlideDrawable>())
				.into(imageView);
	}
}


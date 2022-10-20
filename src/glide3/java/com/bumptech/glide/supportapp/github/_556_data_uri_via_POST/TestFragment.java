package com.bumptech.glide.supportapp.github._556_data_uri_via_POST;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.supportapp.GlideImageFragment;
import com.bumptech.glide.supportapp.R;
import com.bumptech.glide.supportapp.utils.LoggingListener;

public class TestFragment extends GlideImageFragment {
	@Override protected void load(final Context context) {
		Glide
				.with(context)
				.load(new Image("identifier_referencing_an_image"))
				.placeholder(R.drawable.glide_placeholder)
				.error(R.drawable.glide_error)
				//.diskCacheStrategy(DiskCacheStrategy.NONE)
				//.skipMemoryCache(true)
				.listener(new LoggingListener<Image, GlideDrawable>())
				.into(imageView);
	}
}

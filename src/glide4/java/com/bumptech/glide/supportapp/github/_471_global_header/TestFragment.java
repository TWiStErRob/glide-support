package com.bumptech.glide.supportapp.github._471_global_header;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.supportapp.GlideImageFragment;
import com.bumptech.glide.supportapp.utils.LoggingListener;

public class TestFragment extends GlideImageFragment {

	@Override protected void load(final Context context) {
		Glide
				.with(this)
				.load("http://via.placeholder.com/350x150")
				.apply(new RequestOptions()
						.diskCacheStrategy(DiskCacheStrategy.NONE) // force reload
						.skipMemoryCache(true) // force reload
				)
				.listener(new LoggingListener<Drawable>())
				.into(imageView)
		;
	}
}

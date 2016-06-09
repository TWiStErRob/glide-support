package com.bumptech.glide.supportapp.github._1257_cache_304;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.supportapp.*;
import com.bumptech.glide.supportapp.utils.LoggingListener;

import jp.wasabeef.glide.transformations.GrayscaleTransformation;

public class TestFragment extends GlideImageFragment {
	@Override protected void load(Context context) throws Exception {
		String urlString = "http://web.twisterrob.net/glide/1257_changing.php";
		Glide
				.with(this)
				.load(new ForceLoadGlideUrl(urlString))
				.fitCenter()
				.diskCacheStrategy(DiskCacheStrategy.NONE)
				.skipMemoryCache(true)
				//.placeholder(R.drawable.glide_placeholder)
				//.error(R.drawable.glide_error)
				.listener(new LoggingListener<GlideUrl, GlideDrawable>("full"))
				.thumbnail(Glide
						.with(this)
						.load(new CachedGlideUrl(urlString))
						.diskCacheStrategy(DiskCacheStrategy.NONE)
						.skipMemoryCache(true)
						.listener(new LoggingListener<GlideUrl, GlideDrawable>("thumbnail"))
						.bitmapTransform(new FitCenter(context), new GrayscaleTransformation(context))
						.sizeMultiplier(0.25f)
				)
				.into(imageView);
	}
}

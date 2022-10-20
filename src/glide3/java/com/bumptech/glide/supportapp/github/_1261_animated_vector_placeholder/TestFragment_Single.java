package com.bumptech.glide.supportapp.github._1261_animated_vector_placeholder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.supportapp.GlideImageFragment;
import com.bumptech.glide.supportapp.R;
import com.bumptech.glide.supportapp.utils.DelayTransformation;
import com.bumptech.glide.supportapp.utils.LoggingListener;
import com.bumptech.glide.supportapp.utils.LoggingTarget;

import androidx.core.content.ContextCompat;

public class TestFragment_Single extends GlideImageFragment {
	@Override protected void load(final Context context) throws Exception {
		String url = "http://www.kizoa.com/img/e8nZC.gif";
		Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.github_1261_nine_to_five);
		if (drawable instanceof Animatable) {
			((Animatable)drawable).stop();
			((Animatable)drawable).start();
		}
		Glide
				.with(this)
				.load(url)
				.placeholder(drawable)
				.crossFade(3000)
				.skipMemoryCache(true) // make sure the image is reloaded so the placeholder has a chance
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				.bitmapTransform(DelayTransformation.<Bitmap>create(2000)) // debug lengthen decode to see placeholder
				.listener(new LoggingListener<String, GlideDrawable>())
				.into(new LoggingTarget<>(new GlideDrawableImageViewTarget(imageView)))
		;
	}
}

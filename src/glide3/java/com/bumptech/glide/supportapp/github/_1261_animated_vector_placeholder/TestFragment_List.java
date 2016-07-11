package com.bumptech.glide.supportapp.github._1261_animated_vector_placeholder;

import java.util.Arrays;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.*;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.*;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.supportapp.*;
import com.bumptech.glide.supportapp.utils.*;

public class TestFragment_List extends GlideRecyclerFragment {
	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		listView.setLayoutManager(new GridLayoutManager(null, 2));
		String[] urls = new String[40];
		Arrays.fill(urls, "http://www.kizoa.com/img/e8nZC.gif");
		listView.setAdapter(new SimpleUrlAdapter(Glide.with(this), Arrays.asList(urls)) {
			@Override protected void load(Context context, RequestManager glide, String url, ImageView imageView)
					throws Exception {
				Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.github_1261_nine_to_five);
				if (drawable instanceof Animatable) {
					((Animatable)drawable).stop(); // required in case the drawable was reused
					((Animatable)drawable).start(); // required to start the animation
				}
				glide
						.load(url)
						.placeholder(drawable)
						.crossFade(3000)
						.skipMemoryCache(true) // debug: make sure the image is reloaded so the placeholder has a chance
						.diskCacheStrategy(DiskCacheStrategy.SOURCE)
						// debug: lengthen loading to see placeholder
						.bitmapTransform(DelayTransformation.<Bitmap>create(2000))
						.listener(new LoggingListener<String, GlideDrawable>())
						.into(new LoggingTarget<>(new GlideDrawableImageViewTarget(imageView)))
				;
			}
		});
	}
}

package com.bumptech.glide.supportapp.github._943_double_crossFade;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.supportapp.GlideImageFragment;

/**
 * This is proof that the #943 issue doesn't come up with Glide 4.x.
 * After the blue placeholder flashes, the thumbnail is loaded and crossfades into the full image,
 * this is observable over 5 seconds as the image gets clearer and clearer.
 * If it wasn't working the image would have red or blue tint.
 */
public class TestFragment extends GlideImageFragment {
	@Override protected void load(Context context) throws Exception {
		String url =
				"https://capfor.files.wordpress.com/2012/07/beautiful-forest-beautiful-day-forests-grass-green-light-nature-sunshine-trees.jpg";
		imageView.setBackgroundColor(Color.RED);
		Glide
				.with(getContext())
				.load(url)
				.skipMemoryCache(true)
				.centerCrop()
				.thumbnail(Glide
						.with(getContext())
						.load(url)
						.skipMemoryCache(true)
						.centerCrop()
						.sizeMultiplier(.05f)
						// .dontAnimate() doesn't work here, see RequestBuilder.buildRequestRecursive.
						.transition(DrawableTransitionOptions.withCrossFade(0))
				)
				.placeholder(new ColorDrawable(Color.BLUE))
				.transition(DrawableTransitionOptions.withCrossFade(5000))
				.into(imageView);
	}
}

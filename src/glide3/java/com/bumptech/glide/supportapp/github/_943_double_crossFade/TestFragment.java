package com.bumptech.glide.supportapp.github._943_double_crossFade;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.*;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.supportapp.GlideImageFragment;

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
						.sizeMultiplier(.1f)
						.crossFade(0) // dontAnimate doesn't work here, see GRB.buildRequestRecursive
				)
				.placeholder(new ColorDrawable(Color.BLUE))
				.crossFade(5000)
				.into(new GlideDrawableImageViewTarget(imageView) {
					@Override public void setDrawable(Drawable drawable) {
						if (drawable instanceof TransitionDrawable) {
							//((TransitionDrawable)drawable).setCrossFadeEnabled(false);
						}
						super.setDrawable(drawable);
					}
				});
	}
}

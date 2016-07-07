package com.bumptech.glide.supportapp.github._1140_always_animate;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.*;
import com.bumptech.glide.supportapp.*;

public class TestFragment extends GlideImageFragment {
	@Override protected void load(Context context) throws Exception {
		Glide
				.with(this)
				.load(R.drawable.glide)
				.animate(new ViewAnimationFactory<GlideDrawable>(context, android.R.anim.slide_in_left) {
					@Override public GlideAnimation<GlideDrawable> build(
							boolean isFromMemoryCache, boolean isFirstResource) {
						return super.build(false, isFirstResource);
					}
				})
				.into(imageView);
	}
}

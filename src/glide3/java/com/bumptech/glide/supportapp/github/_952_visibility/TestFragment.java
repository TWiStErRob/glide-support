package com.bumptech.glide.supportapp.github._952_visibility;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.supportapp.GlideImageFragment;

public class TestFragment extends GlideImageFragment {
	@Override protected void load(Context context) throws Exception {
		String url =
				"http://www.jqueryscript.net/images/Simplest-Responsive-jQuery-Image-Lightbox-Plugin-simple-lightbox.jpg";
		// hide the view (which already has a size, because it was VISIBLE)
		// it needs to be visible first (to measure and layout), otherwise the Glide load won't even start
		imageView.setVisibility(View.INVISIBLE);
		Glide
				.with(this)
				.load(url)
				.skipMemoryCache(true)
				// adding a placeholder also solves the issue,
				// because then `TransitionDrawable` is used instead of `AlphaAnimation`.
				//.placeholder(R.drawable.glide_placeholder)
				.crossFade(2000) // lengthen the default few hundred milliseconds for effect
				//.dontAnimate() // the proper fix, don't need to do expensive animations when the user doesn't see it
				.into(imageView);
	}
}

package com.bumptech.glide.supportapp.github._2000_apt_glide_app;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.supportapp.GlideApp;
import com.bumptech.glide.supportapp.GlideDualImageFragment;
import com.bumptech.glide.supportapp.R;

public class TestFragment extends GlideDualImageFragment {

	String url = "https://amazingslider.com/wp-content/uploads/2012/12/dandelion.jpg";

	@Override protected void load1(Context context, ImageView imageView) {
		Glide
				.with(context)
				.load(url)
				.apply(new RequestOptions()
						.placeholder(R.drawable.glide_placeholder)
				)
				.into(imageView);
	}

	/**
	 * {@link GlideApp} is available because of {@link com.bumptech.glide.supportapp.AppGlideModule}
	 * and the {@code annotationProcessor} declaration in {@code build.gradle}.
	 */
	@Override protected void load2(Context context, ImageView imageView) {
		GlideApp
				.with(context)
				.load(url)
				.placeholder(R.drawable.glide_placeholder)
				.into(imageView);
	}
}

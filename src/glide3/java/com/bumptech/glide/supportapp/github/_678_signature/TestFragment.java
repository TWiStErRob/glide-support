package com.bumptech.glide.supportapp.github._678_signature;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.signature.StringSignature;
import com.bumptech.glide.supportapp.GlideImageFragment;
import com.bumptech.glide.supportapp.utils.LoggingListener;

public class TestFragment extends GlideImageFragment {
	int toto = 0;
	@Override protected void load(final Context context) throws Exception {
		final ImageView imageView = this.imageView;
		toto = toto + 1;
		Glide
				.with(context.getApplicationContext())
				.load("http://lorempixel.com/400/200/sports")
				.signature(new StringSignature(Integer.toString(toto)))
				.centerCrop()
				.listener(new LoggingListener<String, GlideDrawable>())
				.into(imageView)
		;
	}
}

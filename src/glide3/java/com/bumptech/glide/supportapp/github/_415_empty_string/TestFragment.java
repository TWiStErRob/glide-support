package com.bumptech.glide.supportapp.github._415_empty_string;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.supportapp.*;
import com.bumptech.glide.supportapp.utils.LoggingListener;

public class TestFragment extends GlideImageFragment {
	@Override protected void load(Context context) {
		Glide.with(this)
		     .fromString()
		     //.asBitmap()
		     .error(R.drawable.glide_error)
		     .load("")
		     .listener(new LoggingListener<String, GlideDrawable>())
		     .into(imageView)
		;
	}
}

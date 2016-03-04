package com.bumptech.glide.supportapp.github._886_custom_memory_cache;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.supportapp.GlideImageFragment;

public class TestFragment extends GlideImageFragment {
	@Override protected void load(Context context) throws Exception {
		String url = "http://www.joomlaworks.net/images/demos/galleries/abstract/7.jpg";
		Glide.with(context).load(url).into(imageView);
	}
}

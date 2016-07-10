package com.bumptech.glide.supportapp;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class GlideBaseImageActivity extends GlideBaseActivity {
	protected void clear(ImageView imageView) {
		Glide.clear(imageView);
	}
}

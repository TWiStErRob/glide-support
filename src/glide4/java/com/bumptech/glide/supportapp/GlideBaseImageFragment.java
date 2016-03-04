package com.bumptech.glide.supportapp;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class GlideBaseImageFragment extends BaseFragment {
	protected void clear(ImageView imageView) {
		Glide.with(this).clear(imageView);
	}
}

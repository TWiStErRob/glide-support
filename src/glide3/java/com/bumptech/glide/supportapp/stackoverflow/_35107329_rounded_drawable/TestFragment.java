package com.bumptech.glide.supportapp.stackoverflow._35107329_rounded_drawable;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.supportapp.GlideImageFragment;
import com.bumptech.glide.supportapp.R;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;

public class TestFragment extends GlideImageFragment {
	@Override protected void load(final Context context) {
		String url = "";
		Glide
				.with(context)
				.load(url)
				.asBitmap()
				.transcode(new RoundedDrawableTranscoder(context), RoundedBitmapDrawable.class)
				.placeholder(R.drawable.glide_placeholder)
				.into(imageView);
	}
}

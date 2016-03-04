package com.bumptech.glide.supportapp.github._605_bitmap_crossfade;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.supportapp.*;

/** @see com.bumptech.glide.supportapp.github._840_bitmap_crossfade.TestFragment */
public class TestFragment extends GlideImageFragment {
	@Override protected void load(Context context) throws Exception {
		Glide
				.with(context)
				.load("https://media.giphy.com/media/4aBQ9oNjgEQ2k/giphy.gif")
				.asBitmap()
				.placeholder(R.drawable.glide_placeholder)
				.listener(new BitmapCrossfadeRequestListener<String>())
				.into(imageView)
		;
	}
}

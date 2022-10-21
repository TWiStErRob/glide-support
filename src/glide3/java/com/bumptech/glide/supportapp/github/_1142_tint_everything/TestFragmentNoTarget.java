package com.bumptech.glide.supportapp.github._1142_tint_everything;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.supportapp.R;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

/**
 * Same load, and different scenarios are controlled by the model passed to load.
 * Drawables are pre-loaded and pre-tinted, result cannot be tinted without using a listener or a custom target.
 */
public class TestFragmentNoTarget extends TestFragment {
	private Drawable placeholder;
	private Drawable error;
	private Drawable fallback;

	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		placeholder = getTinted(R.drawable.glide_placeholder, PLACEHOLDER_COLOR);
		error = getTinted(R.drawable.glide_error, ERROR_COLOR);
		fallback = getTinted(R.drawable.glide_fallback, FALLBACK_COLOR);
	}

	private @Nullable Drawable getTinted(@DrawableRes int res, @ColorInt int color) {
		// need to mutate otherwise all references to this drawable will be tinted
		// (try to remove the .mutate() call and observe the images on the buttons)
		Drawable drawable = ContextCompat.getDrawable(getContext(), res).mutate();
		return Tinter.tint(drawable, ColorStateList.valueOf(color));
	}

	@Override protected void load(Context context) throws Exception {
		Glide
				.with(this)
				.load(model)
				.placeholder(placeholder)
				.error(error)
				.fallback(fallback)
				.diskCacheStrategy(DiskCacheStrategy.NONE) // so it re-loads for demo
				.skipMemoryCache(true) // so it re-loads for demo
				.crossFade(5000) // slow transition for noticeable effect
				.into(imageView)
		;
	}
}

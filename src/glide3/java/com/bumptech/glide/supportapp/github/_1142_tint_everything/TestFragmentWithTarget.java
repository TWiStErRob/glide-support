package com.bumptech.glide.supportapp.github._1142_tint_everything;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.supportapp.R;

/**
 * Same load, and different scenarios are controlled by the model passed to load.
 * Target controls tinting. It works with cross-fade and result can be easily tinted.
 */
public class TestFragmentWithTarget extends TestFragment {
	@Override protected void load(Context context) throws Exception {
		Glide
				.with(this)
				.load(model)
				.placeholder(R.drawable.glide_placeholder)
				.error(R.drawable.glide_error)
				.fallback(R.drawable.glide_fallback)
				.diskCacheStrategy(DiskCacheStrategy.NONE) // so it re-loads for demo
				.skipMemoryCache(true) // so it re-loads for demo
				.crossFade(5000) // slow transition for noticeable effect
				.into(new TintTarget(imageView,
						RESULT_COLOR, PLACEHOLDER_COLOR, model != null? ERROR_COLOR : FALLBACK_COLOR)
				)
		;
	}
}

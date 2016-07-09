package com.bumptech.glide.supportapp.github._958_animated_placeholder;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.supportapp.*;
import com.bumptech.glide.supportapp.utils.*;

public class TestFragment extends GlideImageFragment {
	@Override protected void load(Context context) throws Exception {
		Glide
				.with(this)
				.load(R.drawable.glide_anim)
				.placeholder(R.drawable.github_958_progress)
				.placeholder(getProgressBarIndeterminate())
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				.transform(new DelayBitmapTransformation(5000)) // display placeholder for 5 seconds
				.listener(new LoggingListener<Integer, GlideDrawable>())
				.into(imageView)
		;
	}

	private Drawable getProgressBarIndeterminate() {
		final int[] attrs = {android.R.attr.indeterminateDrawable};
		final int attrs_indeterminateDrawable_index = 0;
		TypedArray a = getContext().obtainStyledAttributes(android.R.style.Widget_ProgressBar, attrs);
		try {
			return a.getDrawable(attrs_indeterminateDrawable_index);
		} finally {
			a.recycle();
		}
	}
}

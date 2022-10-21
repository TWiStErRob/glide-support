package com.bumptech.glide.supportapp.random.__quicky;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.supportapp.GlideImageFragment;
import com.bumptech.glide.supportapp.R;
import com.bumptech.glide.supportapp.utils.LoggingListener;
import com.bumptech.glide.supportapp.utils.LoggingTarget;

import androidx.annotation.Nullable;

public class QuickFragment extends GlideImageFragment {
	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
//		imageView.setScaleType(ScaleType.FIT_XY);
//		float fixed = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 400, getResources().getDisplayMetrics());
//		imageView.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}
	@Override protected void load(final Context context) throws Exception {
		String url = "http://i.imgur.com/1ALnB2s.gif";
		Glide
				.with(this)
				.load(url)
				.placeholder(R.drawable.glide_placeholder)
				.animate(android.R.anim.fade_in)
				.error(R.drawable.glide_error)
				.fallback(R.drawable.glide_fallback)
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				.skipMemoryCache(true)
				.listener(new LoggingListener<String, GlideDrawable>())
				.into(new LoggingTarget<>(new GlideDrawableImageViewTarget(imageView)))
		;
	}
}

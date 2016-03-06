package com.bumptech.glide.supportapp.random.__quicky;

import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.supportapp.*;
import com.bumptech.glide.supportapp.utils.LoggingListener;

public class QuickFragment extends GlideImageFragment {
	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
//		imageView.setScaleType(ScaleType.FIT_XY);
//		float fixed = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 400, getResources().getDisplayMetrics());
//		imageView.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}
	@Override protected void load(final Context context) throws ExecutionException, InterruptedException {
		String url = "";

		Glide
				.with(this)
				.load(url)
				.placeholder(R.drawable.glide_placeholder)
				.error(R.drawable.glide_error)
				.fallback(R.drawable.glide_fallback)
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				.skipMemoryCache(true)
				.listener(new LoggingListener<String, GlideDrawable>())
				.into(imageView)
		;
	}
}

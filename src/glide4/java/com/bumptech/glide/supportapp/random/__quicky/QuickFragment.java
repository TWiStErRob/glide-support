package com.bumptech.glide.supportapp.random.__quicky;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.supportapp.GlideImageFragment;
import com.bumptech.glide.supportapp.R;
import com.bumptech.glide.supportapp.utils.LoggingListener;

import androidx.annotation.Nullable;

public class QuickFragment extends GlideImageFragment {
	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		//imageView.setLayoutParams(new FrameLayout.LayoutParams(1443, 812));
		//imageView.setScaleType(ScaleType.FIT_CENTER);
	}
	@Override protected void load(Context context) {
		String url = "...";
		Glide
				.with(context)
				.load(url)
				.apply(new RequestOptions()
						.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
						.placeholder(R.drawable.glide_placeholder)
						.fallback(R.drawable.glide_fallback)
						.error(R.drawable.glide_error)
				)
				.transition(new DrawableTransitionOptions()
						.crossFade()
				)
				.listener(new LoggingListener<Drawable>())
				.into(imageView)
		;
	}
}

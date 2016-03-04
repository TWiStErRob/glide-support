package com.bumptech.glide.supportapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bumptech.glide.Glide;

public class QuickFragment extends GlideImageFragment {
	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		//imageView.setLayoutParams(new FrameLayout.LayoutParams(1443, 812));
		//imageView.setScaleType(ScaleType.FIT_CENTER);
	}
	@Override protected void load(Context context) {
		Glide
				.with(context)
				.load("http://7rf2lu.com2.z0.glb.qiniucdn.com/1450421326882.gif")
				.into(imageView);
	}
}

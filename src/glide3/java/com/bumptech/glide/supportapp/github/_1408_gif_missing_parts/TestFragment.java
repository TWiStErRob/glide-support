package com.bumptech.glide.supportapp.github._1408_gif_missing_parts;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.supportapp.GlideImageFragment;
import com.bumptech.glide.supportapp.R;

public class TestFragment extends GlideImageFragment {
	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.github_1408, container, false);
	}
	
	@Override protected void load(Context context) throws Exception {
		Glide
				.with(context)
				.load("http://www.aikf.com/ask/resources/images/facialExpression/qq/9.gif")
				.into(imageView);
	}
}

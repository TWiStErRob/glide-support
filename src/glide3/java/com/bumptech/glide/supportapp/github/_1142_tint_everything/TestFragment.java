package com.bumptech.glide.supportapp.github._1142_tint_everything;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.supportapp.GlideImageFragment;
import com.bumptech.glide.supportapp.R;
import com.bumptech.glide.supportapp.utils.Utils;

import androidx.annotation.Nullable;

/**
 * Same load, and different scenarios are controlled by the model passed to load.
 */
abstract class TestFragment extends GlideImageFragment {
	protected static final int RESULT_COLOR = Color.GREEN;
	protected static final int PLACEHOLDER_COLOR = Color.BLUE;
	protected static final int ERROR_COLOR = Color.RED;
	protected static final int FALLBACK_COLOR = Color.YELLOW;

	protected Uri model;
	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.github_1142, container, false);
	}
	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		view.findViewById(R.id.github_1142_normal).setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				model = Utils.toResourceUri(v.getContext(), R.drawable.glide);
				load();
			}
		});
		view.findViewById(R.id.github_1142_error).setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				model = Uri.parse("http://localhost/uri/that/will.error");
				load();
			}
		});
		view.findViewById(R.id.github_1142_fallback).setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				model = null;
				load();
			}
		});
		view.findViewById(R.id.github_1142_placeholder).setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				load();
				Glide.clear(imageView); // clearing after load shows the placeholder
			}
		});
	}
}

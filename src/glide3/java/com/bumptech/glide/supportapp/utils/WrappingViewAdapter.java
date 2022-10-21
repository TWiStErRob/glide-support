package com.bumptech.glide.supportapp.utils;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.bumptech.glide.request.animation.GlideAnimation.ViewAdapter;

import androidx.annotation.NonNull;

public class WrappingViewAdapter implements ViewAdapter {
	protected final ViewAdapter adapter;

	public WrappingViewAdapter(@NonNull ViewAdapter adapter) {
		this.adapter = adapter;
	}

	@Override public View getView() {
		return adapter.getView();
	}

	@Override public Drawable getCurrentDrawable() {
		return adapter.getCurrentDrawable();
	}

	@Override public void setDrawable(Drawable drawable) {
		adapter.setDrawable(drawable);
	}
}

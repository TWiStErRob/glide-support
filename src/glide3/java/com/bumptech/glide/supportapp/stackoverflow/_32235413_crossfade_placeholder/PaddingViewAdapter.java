package com.bumptech.glide.supportapp.stackoverflow._32235413_crossfade_placeholder;

import android.graphics.drawable.*;
import android.view.View;

import com.bumptech.glide.request.animation.GlideAnimation.ViewAdapter;

class PaddingViewAdapter implements ViewAdapter {
	private final ViewAdapter realAdapter;
	private final int targetWidth;
	private final int targetHeight;

	public PaddingViewAdapter(ViewAdapter adapter, int targetWidth, int targetHeight) {
		this.realAdapter = adapter;
		this.targetWidth = targetWidth;
		this.targetHeight = targetHeight;
	}

	@Override public View getView() {
		return realAdapter.getView();
	}

	@Override public Drawable getCurrentDrawable() {
		Drawable drawable = realAdapter.getCurrentDrawable();
		if (drawable != null) {
			int padX = Math.max(0, targetWidth - drawable.getIntrinsicWidth()) / 2;
			int padY = Math.max(0, targetHeight - drawable.getIntrinsicHeight()) / 2;
			if (padX != 0 || padY != 0) {
				drawable = new InsetDrawable(drawable, padX, padY, padX, padY);
			}
		}
		return drawable;
	}

	@Override public void setDrawable(Drawable drawable) {
		realAdapter.setDrawable(drawable);
	}
}

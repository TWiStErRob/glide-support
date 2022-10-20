package com.bumptech.glide.supportapp.github._938_background;

import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.View;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.ViewTarget;

/** @see ImageViewTarget */
public abstract class ViewBackgroundTarget<Z> extends ViewTarget<View, Z> implements GlideAnimation.ViewAdapter {
	public ViewBackgroundTarget(View view) {
		super(view);
	}
	@Override public void onLoadCleared(Drawable placeholder) {
		setBackground(placeholder);
	}
	@Override public void onLoadStarted(Drawable placeholder) {
		setBackground(placeholder);
	}
	@Override public void onLoadFailed(Exception e, Drawable errorDrawable) {
		setBackground(errorDrawable);
	}
	@Override public void onResourceReady(Z resource, GlideAnimation<? super Z> glideAnimation) {
		if (glideAnimation == null || !glideAnimation.animate(resource, this)) {
			setResource(resource);
		}
	}
	@Override public void setDrawable(Drawable drawable) {
		setBackground(drawable);
	}
	@Override public Drawable getCurrentDrawable() {
		return view.getBackground();
	}

	@SuppressWarnings("deprecation")
	protected void setBackground(Drawable drawable) {
		if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
			view.setBackground(drawable);
		} else {
			view.setBackgroundDrawable(drawable);
		}
	}

	protected abstract void setResource(Z resource);
}

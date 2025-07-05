package com.bumptech.glide.supportapp.github._938_background;

import android.graphics.drawable.Drawable;
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
		view.setBackground(placeholder);
	}
	@Override public void onLoadStarted(Drawable placeholder) {
		view.setBackground(placeholder);
	}
	@Override public void onLoadFailed(Exception e, Drawable errorDrawable) {
		view.setBackground(errorDrawable);
	}
	@Override public void onResourceReady(Z resource, GlideAnimation<? super Z> glideAnimation) {
		if (glideAnimation == null || !glideAnimation.animate(resource, this)) {
			setResource(resource);
		}
	}
	@Override public void setDrawable(Drawable drawable) {
		view.setBackground(drawable);
	}
	@Override public Drawable getCurrentDrawable() {
		return view.getBackground();
	}

	protected abstract void setResource(Z resource);
}

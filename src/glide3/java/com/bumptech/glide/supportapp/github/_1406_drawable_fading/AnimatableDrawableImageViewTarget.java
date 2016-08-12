package com.bumptech.glide.supportapp.github._1406_drawable_fading;

import android.graphics.drawable.*;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.DrawableImageViewTarget;

public class AnimatableDrawableImageViewTarget extends DrawableImageViewTarget {
	private Animatable animatable;

	public AnimatableDrawableImageViewTarget(@NonNull ImageView imageView) {
		super(imageView);
	}

	@Override public void onResourceReady(Drawable resource, GlideAnimation<? super Drawable> glideAnimation) {
		if (resource instanceof Animatable) {
			animatable = (Animatable)resource;
			animatable.stop();
			animatable.start();
		}
		super.onResourceReady(resource, glideAnimation);
	}

	@Override public void onStart() {
		if (animatable != null) {
			animatable.start();
		}
	}

	@Override public void onStop() {
		if (animatable != null) {
			animatable.stop();
		}
	}

	@Override public void onLoadCleared(Drawable placeholder) {
		if (animatable != null) {
			animatable.stop();
			animatable = null;
		}
		super.onLoadCleared(placeholder);
	}
}

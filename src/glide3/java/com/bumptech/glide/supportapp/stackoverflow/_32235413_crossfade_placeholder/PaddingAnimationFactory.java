package com.bumptech.glide.supportapp.stackoverflow._32235413_crossfade_placeholder;

import android.graphics.drawable.Drawable;

import com.bumptech.glide.request.animation.DrawableCrossFadeFactory;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.animation.GlideAnimationFactory;

class PaddingAnimationFactory<T extends Drawable> implements GlideAnimationFactory<T> {
	private final DrawableCrossFadeFactory<T> realFactory;

	public PaddingAnimationFactory(DrawableCrossFadeFactory<T> factory) {
		this.realFactory = factory;
	}

	@Override public GlideAnimation<T> build(boolean isFromMemoryCache, boolean isFirstResource) {
		return new PaddingAnimation<>(realFactory.build(isFromMemoryCache, isFirstResource));
	}
}

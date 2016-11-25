package com.bumptech.glide.supportapp.github._943_double_crossFade;

import android.graphics.drawable.*;

import com.bumptech.glide.request.animation.*;
import com.bumptech.glide.request.animation.GlideAnimation.ViewAdapter;
import com.bumptech.glide.supportapp.utils.WrappingViewAdapter;

class AlwaysCrossFade<D extends Drawable> extends DrawableCrossFadeFactory<D> {
	private final boolean transparentImagesPossible;

	/**
	 * @param transparentImagesPossible used to signal that are no transparent images possible with this load.
	 *                                  When cross-fading between opaque images a better-looking cross-fade is possible
	 *                                  via {@link TransitionDrawable#setCrossFadeEnabled(boolean)}.
	 * @see <a href="https://github.com/bumptech/glide/issues/943">#943</a>
	 */
	public AlwaysCrossFade(boolean transparentImagesPossible) {
		this.transparentImagesPossible = transparentImagesPossible;
	}

	@Override public GlideAnimation<D> build(boolean isFromMemoryCache, boolean isFirstResource) {
		// passing isFirstResource instead of isFromMemoryCache achieves the result we want
		GlideAnimation<D> animation = super.build(isFirstResource, isFirstResource);
		if (!transparentImagesPossible) {
			animation = new RealCrossFadeAnimation<>(animation);
		}
		return animation;
	}

	private static class RealCrossFadeAnimation<D extends Drawable> implements GlideAnimation<D> {
		private final GlideAnimation<D> animation;

		public RealCrossFadeAnimation(GlideAnimation<D> animation) {
			this.animation = animation;
		}

		@Override public boolean animate(D current, final ViewAdapter adapter) {
			return animation.animate(current, new CrossFadeDisablingViewAdapter(adapter));
		}
	}

	private static class CrossFadeDisablingViewAdapter extends WrappingViewAdapter {
		public CrossFadeDisablingViewAdapter(ViewAdapter adapter) {
			super(adapter);
		}

		@Override public void setDrawable(Drawable drawable) {
			if (drawable instanceof TransitionDrawable) {
				((TransitionDrawable)drawable).setCrossFadeEnabled(false);
			}
			super.setDrawable(drawable);
		}
	}
}

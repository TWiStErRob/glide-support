package com.bumptech.glide.supportapp.github._1406_drawable_fading;

import android.graphics.drawable.*;

import com.bumptech.glide.request.animation.*;
import com.bumptech.glide.request.animation.GlideAnimation.ViewAdapter;
import com.bumptech.glide.supportapp.utils.WrappingViewAdapter;

/**
 * Use it as:
 * <pre><code>
 * Glide.with(this)
 *     	.load(url)
 *     	.animate(new AlwaysCrossFade&lt;GlideDrawable&gt;(false))
 *     	.into(imageView);
 * </code></pre>
 * @since 3.8.0
 */
class AlwaysCrossFade<T extends Drawable> extends DrawableCrossFadeFactory<T> {
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

	@Override public GlideAnimation<T> build(boolean isFromMemoryCache, boolean isFirstResource) {
		// passing isFirstResource instead of isFromMemoryCache achieves the result we want
		GlideAnimation<T> animation = super.build(isFirstResource, isFirstResource);
		if (!transparentImagesPossible) {
			animation = new RealCrossFadeAnimation<>(animation);
		}
		return animation;
	}

	private static class RealCrossFadeAnimation<T extends Drawable> implements GlideAnimation<T> {
		private final GlideAnimation<T> animation;

		public RealCrossFadeAnimation(GlideAnimation<T> animation) {
			this.animation = animation;
		}

		@Override public boolean animate(T current, final ViewAdapter adapter) {
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

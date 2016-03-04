package com.bumptech.glide.supportapp.github._840_bitmap_crossfade;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.*;

import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.*;
import com.bumptech.glide.request.animation.GlideAnimation.ViewAdapter;
import com.bumptech.glide.request.target.Target;

public class BitmapCrossfadeListener<ModelType> implements RequestListener<ModelType, Bitmap> {
	public static final GlideAnimationFactory<Bitmap> CROSS_FADE_FACTORY = new GlideAnimationFactory<Bitmap>() {
		private final GlideAnimationFactory<Drawable> realFactory = new DrawableCrossFadeFactory<>();
		@Override public GlideAnimation<Bitmap> build(boolean isFromMemoryCache, boolean isFirstResource) {
			final GlideAnimation<Drawable> transition = realFactory.build(isFromMemoryCache, isFirstResource);
			return new GlideAnimation<Bitmap>() {
				@Override public boolean animate(Bitmap current, ViewAdapter adapter) {
					Resources resources = adapter.getView().getResources();
					Drawable currentBitmap = new BitmapDrawable(resources, current);
					return transition.animate(currentBitmap, adapter);
				}
			};
		}
	};
	@Override public boolean onException(Exception e, ModelType model, Target<Bitmap> target, boolean isFirstResource) {
		return false;
	}
	@Override public boolean onResourceReady(Bitmap resource, ModelType model, Target<Bitmap> target,
			boolean isFromMemoryCache, boolean isFirstResource) {
		return CROSS_FADE_FACTORY
				.build(isFromMemoryCache, isFirstResource)
				.animate(resource, (ViewAdapter)target)
				;
	}
}

package com.bumptech.glide.supportapp.github._605_bitmap_crossfade;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.DrawableCrossFadeFactory;
import com.bumptech.glide.request.target.*;

public class BitmapCrossfadeRequestListener<T> implements RequestListener<T, Bitmap> {
	@Override public boolean onException(Exception e, T model, Target<Bitmap> target, boolean isFirstResource) {
		return false;
	}
	@Override public boolean onResourceReady(
			Bitmap resource, T model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
		ImageViewTarget<?> imTarget = (ImageViewTarget<?>)target;
		return new DrawableCrossFadeFactory<>()
				.build(isFromMemoryCache, isFirstResource)
				.animate(new BitmapDrawable(imTarget.getView().getResources(), resource), imTarget);
	}
}

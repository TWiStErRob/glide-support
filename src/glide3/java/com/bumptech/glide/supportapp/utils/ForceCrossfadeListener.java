package com.bumptech.glide.supportapp.utils;

import android.graphics.drawable.Drawable;

import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.DrawableCrossFadeFactory;
import com.bumptech.glide.request.animation.GlideAnimation.ViewAdapter;
import com.bumptech.glide.request.target.Target;

public class ForceCrossfadeListener<TranscodeType extends Drawable> implements RequestListener<Object, TranscodeType> {
	private final int duration;
	public ForceCrossfadeListener() {
		this(500);
	}
	public ForceCrossfadeListener(int duration) {
		this.duration = duration;
	}
	@Override public boolean onException(Exception e, Object model, Target<TranscodeType> target,
			boolean isFirstResource) {
		return false;
	}
	@Override public boolean onResourceReady(TranscodeType resource, Object model, Target<TranscodeType> target,
			boolean isFromMemoryCache, boolean isFirstResource) {
		if (isFirstResource) {
			return false; // thumbnail was not shown, do as usual
		}
		return new DrawableCrossFadeFactory<>(duration)
				.build(false, false) // force crossFade() even if coming from memory cache
				.animate(resource, (ViewAdapter)target);
	}
}

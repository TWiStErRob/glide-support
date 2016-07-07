package com.bumptech.glide.supportapp.github._108_indicator;

import android.graphics.Color;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.*;

// Just a rough idea, not really functional
public class IndicatorListener<T, Z> implements RequestListener<T, Z> {
	public boolean onException(Exception e, T model, Target<Z> target, boolean isFirstResource) {
		return false;
	}

	public boolean onResourceReady(Z resource, T model, Target<Z> target, boolean isFromMemoryCache,
			boolean isFirstResource) {
		ImageView image = ((ImageViewTarget<Z>)target).getView();
		image.setPadding(4, 4, 4, 4);
		if (isFromMemoryCache) {
			if (isFirstResource) {
				image.setBackgroundColor(Color.argb(0xFF, 0x00, 0xFF, 0x00));
			} else {
				image.setBackgroundColor(Color.argb(0xFF, 0x00, 0xFF, 0xFF));
			}
		} else {
			if (isFirstResource) {
				image.setBackgroundColor(Color.argb(0xFF, 0xFF, 0xCC, 0x00));
			} else {
				image.setBackgroundColor(Color.argb(0xFF, 0xFF, 0x00, 0x00));
			}
		}
		return false;
	}
}

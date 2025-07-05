package com.bumptech.glide.supportapp.github._938_background;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

import com.bumptech.glide.request.target.BitmapImageViewTarget;

/** @see BitmapImageViewTarget */
public class BitmapViewBackgroundTarget extends ViewBackgroundTarget<Bitmap> {
	public BitmapViewBackgroundTarget(View view) {
		super(view);
	}
	@Override protected void setResource(Bitmap resource) {
		view.setBackground(new BitmapDrawable(view.getResources(), resource));
	}
}

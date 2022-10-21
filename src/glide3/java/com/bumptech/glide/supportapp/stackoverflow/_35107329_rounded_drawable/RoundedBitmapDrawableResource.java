package com.bumptech.glide.supportapp.stackoverflow._35107329_rounded_drawable;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.drawable.DrawableResource;
import com.bumptech.glide.util.Util;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;

public class RoundedBitmapDrawableResource extends DrawableResource<RoundedBitmapDrawable> {
	private final BitmapPool bitmapPool;
	public RoundedBitmapDrawableResource(RoundedBitmapDrawable drawable, BitmapPool bitmapPool) {
		super(drawable);
		this.bitmapPool = bitmapPool;
	}
	@Override public int getSize() {
		return Util.getBitmapByteSize(drawable.getBitmap());
	}
	@Override public void recycle() {
		bitmapPool.put(drawable.getBitmap());
	}
}

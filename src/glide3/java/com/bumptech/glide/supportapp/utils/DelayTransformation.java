package com.bumptech.glide.supportapp.utils;

import android.graphics.Bitmap;
import android.util.Log;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

public class DelayTransformation extends BitmapTransformation {
	private final int delay;
	public DelayTransformation(int delay) {
		super((BitmapPool)null);
		this.delay = delay;
	}
	@Override protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int w, int h) {
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			Log.i("DELAY", "Sleeping for " + delay + "ms was interrupted.", e);
		}
		return toTransform;
	}
	@Override public String getId() {
		return "";
	}
}

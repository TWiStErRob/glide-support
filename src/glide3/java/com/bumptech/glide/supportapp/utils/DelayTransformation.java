package com.bumptech.glide.supportapp.utils;

import android.util.Log;

import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;

public class DelayTransformation<T> implements Transformation<T> {
	private final int delay;
	public DelayTransformation(int delay) {
		this.delay = delay;
	}
	@Override public Resource<T> transform(Resource<T> resource, int outWidth, int outHeight) {
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			Log.i("DELAY", "Sleeping for " + delay + "ms was interrupted.", e);
		}
		return resource;
	}
	@Override public String getId() {
		return "";
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public static <T> Transformation<T>[] create(int delay) {
		return new Transformation[] {new DelayTransformation<Object>(delay)};
	}
}

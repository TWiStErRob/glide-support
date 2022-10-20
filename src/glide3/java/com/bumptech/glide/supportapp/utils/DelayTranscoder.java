package com.bumptech.glide.supportapp.utils;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gifbitmap.GifBitmapWrapper;
import com.bumptech.glide.load.resource.transcode.GifBitmapWrapperDrawableTranscoder;
import com.bumptech.glide.load.resource.transcode.GlideBitmapDrawableTranscoder;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;
import com.bumptech.glide.load.resource.transcode.UnitTranscoder;

public class DelayTranscoder<Z, R> implements ResourceTranscoder<Z, R> {
	private final int delay;
	private final ResourceTranscoder<Z, R> transcoder;
	public DelayTranscoder(int delay, ResourceTranscoder<Z, R> transcoder) {
		this.delay = delay;
		this.transcoder = transcoder;
	}
	@Override public Resource<R> transcode(Resource<Z> toTranscode) {
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			Log.i("DELAY", "Sleeping for " + delay + "ms was interrupted.", e);
		}
		return transcoder.transcode(toTranscode);
	}
	@Override public String getId() {
		return transcoder.getId();
	}
	public static ResourceTranscoder<GifBitmapWrapper, GlideDrawable> drawable(int delay, Context context) {
		return new DelayTranscoder<>(delay, new GifBitmapWrapperDrawableTranscoder(
				new GlideBitmapDrawableTranscoder(context.getResources(), Glide.get(context).getBitmapPool())));
	}
	public static <T> ResourceTranscoder<T, T> unit(int delay) {
		return new DelayTranscoder<>(delay, UnitTranscoder.<T>get());
	}
}

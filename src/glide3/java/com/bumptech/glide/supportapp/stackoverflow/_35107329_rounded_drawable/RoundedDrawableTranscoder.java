package com.bumptech.glide.supportapp.stackoverflow._35107329_rounded_drawable;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.*;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;

public class RoundedDrawableTranscoder implements ResourceTranscoder<Bitmap, RoundedBitmapDrawable> {
	private final Context context;
	private final BitmapPool bitmapPool;

	public RoundedDrawableTranscoder(Context context) {
		this.context = context;
		this.bitmapPool = Glide.get(context).getBitmapPool();
	}

	@Override public Resource<RoundedBitmapDrawable> transcode(Resource<Bitmap> toTranscode) {
		Bitmap resource = toTranscode.get();
		RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
		drawable.setCircular(true);
		return new RoundedBitmapDrawableResource(drawable, bitmapPool);
	}

	@Override public String getId() {
		return getClass().getName() + "." + getClass().getPackage().getName();
	}
}

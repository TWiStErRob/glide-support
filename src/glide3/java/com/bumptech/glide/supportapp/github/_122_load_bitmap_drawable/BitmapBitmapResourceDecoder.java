package com.bumptech.glide.supportapp.github._122_load_bitmap_drawable;

import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

public class BitmapBitmapResourceDecoder implements ResourceDecoder<Bitmap, Bitmap> {
	private final BitmapPool pool;
	public BitmapBitmapResourceDecoder(Context context) {
		this(Glide.get(context).getBitmapPool());
	}
	public BitmapBitmapResourceDecoder(BitmapPool pool) {
		this.pool = pool;
	}

	@Override public Resource<Bitmap> decode(Bitmap source, int width, int height) throws IOException {
		return BitmapResource.obtain(source, pool);
	}
	@Override public String getId() {
		return "";
	}
}

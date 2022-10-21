package com.bumptech.glide.supportapp.github._700_tall_image;

import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

abstract class RegionResourceDecoder<T> implements ResourceDecoder<T, Bitmap> {
	private final BitmapPool bitmapPool;
	private final Rect region;
	public RegionResourceDecoder(Context context, Rect region) {
		this(Glide.get(context).getBitmapPool(), region);
	}
	public RegionResourceDecoder(BitmapPool bitmapPool, Rect region) {
		this.bitmapPool = bitmapPool;
		this.region = region;
	}

	@Override public Resource<Bitmap> decode(T source, int width, int height) throws IOException {
		BitmapRegionDecoder decoder = createDecoder(source, width, height);
		Options opts = new Options();
		// Algorithm from Glide's Downsampler.getRoundedSampleSize
		int sampleSize = (int)Math.ceil((double)region.width() / (double)width);
		sampleSize = sampleSize == 0? 0 : Integer.highestOneBit(sampleSize);
		sampleSize = Math.max(1, sampleSize);
		opts.inSampleSize = sampleSize;

		// Although functionally equivalent to 0 for BitmapFactory, 1 is a safer default for our code than 0.
		Bitmap bitmap = decoder.decodeRegion(region, opts);
		return BitmapResource.obtain(bitmap, bitmapPool);
	}
	protected abstract BitmapRegionDecoder createDecoder(T source, int width, int height) throws IOException;

	@Override public String getId() {
		return getClass().getName() + region; // + region is important for RESULT caching
	}
}

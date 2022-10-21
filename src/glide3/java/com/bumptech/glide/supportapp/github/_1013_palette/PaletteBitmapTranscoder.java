package com.bumptech.glide.supportapp.github._1013_palette;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;

import androidx.palette.graphics.Palette;

/**
 * A {@link com.bumptech.glide.load.resource.transcode.ResourceTranscoder} for generating
 * {@link androidx.palette.graphics.Palette}s from {@link android.graphics.Bitmap}s in the background.
 */
public class PaletteBitmapTranscoder implements ResourceTranscoder<Bitmap, PaletteBitmap> {
	private final BitmapPool bitmapPool;

	public PaletteBitmapTranscoder(Context context) {
		this.bitmapPool = Glide.get(context).getBitmapPool();
	}

	@Override
	public Resource<PaletteBitmap> transcode(Resource<Bitmap> toTranscode) {
		Bitmap bitmap = toTranscode.get();
		Palette palette = new Palette.Builder(bitmap).generate();
		PaletteBitmap result = new PaletteBitmap(bitmap, palette);
		return new PaletteBitmapResource(result, bitmapPool);
	}

	@Override
	public String getId() {
		return PaletteBitmapTranscoder.class.getName();
	}
}

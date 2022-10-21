package com.bumptech.glide.supportapp.github._700_tall_image;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;

import com.bumptech.glide.supportapp.utils.BitmapRegionDecoderCompat;

class RegionFileDecoder extends RegionResourceDecoder<File> {
	public RegionFileDecoder(Context context, Rect region) {
		super(context, region);
	}

	@Override protected BitmapRegionDecoder createDecoder(File source, int width, int height) throws IOException {
		return BitmapRegionDecoderCompat.newInstance(source.getAbsolutePath());
	}
}

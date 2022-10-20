package com.bumptech.glide.supportapp.github._700_tall_image;

import java.io.IOException;

import android.content.Context;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;

import com.bumptech.glide.load.model.ImageVideoWrapper;
import com.bumptech.glide.supportapp.utils.BitmapRegionDecoderCompat;

class RegionImageVideoDecoder extends RegionResourceDecoder<ImageVideoWrapper> {
	public RegionImageVideoDecoder(Context context, Rect region) {
		super(context, region);
	}

	@Override protected BitmapRegionDecoder createDecoder(ImageVideoWrapper source, int width, int height)
			throws IOException {
		BitmapRegionDecoder decoder;
		try {
			decoder = BitmapRegionDecoderCompat.newInstance(source.getStream());
		} catch (Exception ex) {
			decoder = BitmapRegionDecoderCompat.newInstance(source.getFileDescriptor());
		}
		return decoder;
	}
}

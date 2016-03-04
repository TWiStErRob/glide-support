package com.bumptech.glide.supportapp.github._700_tall_image;

import java.io.*;

import android.content.Context;
import android.graphics.*;

class RegionFileDecoder extends RegionResourceDecoder<File> {
	public RegionFileDecoder(Context context, Rect region) {
		super(context, region);
	}

	@Override protected BitmapRegionDecoder createDecoder(File source, int width, int height) throws IOException {
		return BitmapRegionDecoder.newInstance(source.getAbsolutePath(), false);
	}
}

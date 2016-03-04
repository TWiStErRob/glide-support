package com.bumptech.glide.supportapp.github._781_get_size;

import android.graphics.Bitmap;

import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.SimpleResource;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;

class BitmapSizeTranscoder implements ResourceTranscoder<Bitmap, Size> {
	@Override public Resource<Size> transcode(Resource<Bitmap> toTranscode) {
		Bitmap bitmap = toTranscode.get();
		Size size = new Size();
		size.width = bitmap.getWidth();
		size.height = bitmap.getHeight();
		return new SimpleResource<>(size);
	}
	@Override public String getId() {
		return getClass().getName();
	}
}

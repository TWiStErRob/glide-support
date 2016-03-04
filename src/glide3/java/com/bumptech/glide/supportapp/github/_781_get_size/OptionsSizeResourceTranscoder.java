package com.bumptech.glide.supportapp.github._781_get_size;

import android.graphics.BitmapFactory.Options;

import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.SimpleResource;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;

class OptionsSizeResourceTranscoder implements ResourceTranscoder<Options, Size> {
	@Override public Resource<Size> transcode(Resource<Options> toTranscode) {
		Options options = toTranscode.get();
		Size size = new Size();
		size.width = options.outWidth;
		size.height = options.outHeight;
		return new SimpleResource<>(size);
	}
	@Override public String getId() {
		return getClass().getName();
	}
}

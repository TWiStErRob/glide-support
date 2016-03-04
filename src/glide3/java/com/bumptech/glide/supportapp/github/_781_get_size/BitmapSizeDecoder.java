package com.bumptech.glide.supportapp.github._781_get_size;

import java.io.*;

import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.SimpleResource;

class BitmapSizeDecoder implements ResourceDecoder<File, Options> {
	@Override public Resource<Options> decode(File source, int width, int height) throws IOException {
		Options options = new Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(source.getAbsolutePath(), options);
		return new SimpleResource<>(options);
	}
	@Override public String getId() {
		return getClass().getName();
	}
}

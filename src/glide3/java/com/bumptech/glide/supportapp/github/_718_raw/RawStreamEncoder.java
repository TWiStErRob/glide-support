package com.bumptech.glide.supportapp.github._718_raw;

import java.io.OutputStream;

import android.graphics.Bitmap;

import com.bumptech.glide.load.ResourceEncoder;
import com.bumptech.glide.load.engine.Resource;

public class RawStreamEncoder implements ResourceEncoder<Bitmap> {
	@Override public boolean encode(Resource<Bitmap> data, OutputStream os) {
		return false;
	}

	@Override public String getId() {
		return null;
	}
}

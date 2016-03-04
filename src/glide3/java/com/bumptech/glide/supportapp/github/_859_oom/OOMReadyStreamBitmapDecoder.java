package com.bumptech.glide.supportapp.github._859_oom;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.bitmap.StreamBitmapDecoder;

/**
 * Created by TWiStEr on 2016-01-04.
 */
class OOMReadyStreamBitmapDecoder extends StreamBitmapDecoder {
	public OOMReadyStreamBitmapDecoder(Context context) {
		super(context);
	} // or any other ctor you want from super
	@Override public Resource<Bitmap> decode(InputStream source, int width, int height) {
		try {
			return super.decode(source, width, height);
		} catch (OutOfMemoryError err) {
			throw new RuntimeException(err);
		}
	}
}

package com.bumptech.glide.supportapp.github._718_raw;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

public class RawStreamDecoder implements ResourceDecoder<InputStream, Bitmap> {
	private final BitmapPool bitmapPool;
	public RawStreamDecoder(Context context) {
		this(Glide.get(context).getBitmapPool());
	}

	public RawStreamDecoder(BitmapPool bitmapPool) {
		this.bitmapPool = bitmapPool;
	}

	@Override public Resource<Bitmap> decode(InputStream source, int width, int height) throws IOException {
		Bitmap bitmap = BitmapFactory.decodeStream(source);
		// read source stream into a Bitmap object (whatever the format)
		// make sure it's not using too much memory
		// the best is obviously if the loaded image matches the given width/height so downstream transformation will just pass it on
		return BitmapResource.obtain(bitmap, bitmapPool);
	}
	@Override public String getId() {
		return RawStreamDecoder.class.getName();
	}
}

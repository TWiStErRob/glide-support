package com.bumptech.glide.supportapp.github._832_raw;

import java.io.*;
import java.nio.ByteBuffer;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.support.annotation.Nullable;

import com.bumptech.glide.load.*;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

public class RawFileDecoder implements ResourceDecoder<File, Bitmap> {
	private final BitmapPool pool;
	public RawFileDecoder(BitmapPool pool) {
		this.pool = pool;
	}

	@Override public boolean handles(File data, Options options) throws IOException {
		// TODO adapt to your use case
		// TODO extract the regex Pattern into a pre-compiled one!
		return data.isFile() && data.getName().matches("orig_w\\d+_h\\d+.raw");
	}

	@Override public @Nullable Resource<Bitmap> decode(File file, int w, int h, Options options) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(w * h * 4);
		FileInputStream stream = new FileInputStream(file);
		try {
			stream.getChannel().read(buffer);
		} finally {
			stream.close();
		}
		Bitmap result = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		try {
			buffer.rewind();
			result.copyPixelsFromBuffer(buffer);
			return BitmapResource.obtain(result, pool);
		} catch (RuntimeException ex) {
			result.recycle();
			throw ex;
		}
	}
}

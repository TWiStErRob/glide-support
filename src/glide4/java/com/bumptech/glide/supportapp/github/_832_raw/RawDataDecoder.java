package com.bumptech.glide.supportapp.github._832_raw;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.support.annotation.Nullable;

import com.bumptech.glide.load.*;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

public class RawDataDecoder implements ResourceDecoder<HashMap<String, ?>, Bitmap> {
	private final BitmapPool pool;
	public RawDataDecoder(BitmapPool pool) {
		this.pool = pool;
	}

	@Override public boolean handles(HashMap<String, ?> bufferData, Options options) throws IOException {
		return bufferData.containsKey("path") && bufferData.containsKey("width") && bufferData.containsKey("height");
	}

	@Override public @Nullable Resource<Bitmap> decode(
			HashMap<String, ?> bufferData, int w, int h, Options options) throws IOException {
		int width = (Integer)bufferData.get("width");
		int height = (Integer)bufferData.get("height");
		String path = (String)bufferData.get("path");
		ByteBuffer buffer = ByteBuffer.allocate(width * height * 4);
		FileInputStream stream = new FileInputStream(path);
		stream.getChannel().read(buffer);
		stream.close();
		Bitmap result = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		buffer.position(0);
		result.copyPixelsFromBuffer(buffer);
		return BitmapResource.obtain(result, pool);
	}
}

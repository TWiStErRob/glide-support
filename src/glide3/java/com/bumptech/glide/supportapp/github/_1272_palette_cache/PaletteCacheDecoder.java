package com.bumptech.glide.supportapp.github._1272_palette_cache;

import java.io.*;

import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;
import android.util.Log;

import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.SimpleResource;

public class PaletteCacheDecoder implements ResourceDecoder<InputStream, Palette> {
	private final ResourceDecoder<InputStream, Palette> paletteDecoder;
	private final ResourceDecoder<InputStream, Bitmap> bitmapDecoder;
	public PaletteCacheDecoder(
			ResourceDecoder<InputStream, Palette> paletteDecoder,
			ResourceDecoder<InputStream, Bitmap> bitmapDecoder) {
		this.paletteDecoder = paletteDecoder;
		this.bitmapDecoder = bitmapDecoder;
	}

	@Override public Resource<Palette> decode(InputStream source, int width, int height) throws IOException {
		if (!source.markSupported()) {
			source = new BufferedInputStream(source);
		}
		Log.d("PALETTE", "Decoding from cache");
		if (isBitmap(source)) {
			Log.d("PALETTE", "It's a cached bitmap");
			Resource<Bitmap> bitmap = bitmapDecoder.decode(source, width, height);
			try {
				Palette palette = new Palette.Builder(bitmap.get())
						.resizeBitmapArea(-1)
						.generate();
				Log.d("PALETTE", "Palette generated");
				return new SimpleResource<>(palette);
			} finally {
				bitmap.recycle();
			}
		} else {
			Log.d("PALETTE", "It's a cached palette");
			if (PaletteCacheEncoder.PALETTE_MAGIC_BYTE != source.read()) {
				throw new IOException("Cannot read palette magic.");
			}
			return paletteDecoder.decode(source, width, height);
		}
	}

	private boolean isBitmap(InputStream source) throws IOException {
		if (!source.markSupported()) {
			throw new IllegalArgumentException("Cannot peek");
		}
		source.mark(1);
		boolean isBitmap = true;
		if (source.read() == PaletteCacheEncoder.PALETTE_MAGIC_BYTE) {
			isBitmap = false;
		}
		source.reset();
		return isBitmap;
	}

	@Override public String getId() {
		return PaletteCacheDecoder.class.getSimpleName();
	}
}

package com.bumptech.glide.supportapp.github._1272_palette_cache;

import java.io.IOException;
import java.io.OutputStream;

import android.util.Log;

import com.bumptech.glide.load.ResourceEncoder;
import com.bumptech.glide.load.engine.Resource;

import androidx.palette.graphics.Palette;

public class PaletteCacheEncoder implements ResourceEncoder<Palette> {
	/**
	 * Flag that it's not a bitmap, but a palette.
	 * Bitmaps start with '‰' for PNG, 0xFF for JPEG, 'R' for WEBP, 'G' for GIF.
	 */
	static final int PALETTE_MAGIC_BYTE = 0;

	private final ResourceEncoder<Palette> paletteEncoder;
	public PaletteCacheEncoder(ResourceEncoder<Palette> paletteEncoder) {
		this.paletteEncoder = paletteEncoder;
	}

	@Override public boolean encode(Resource<Palette> data, OutputStream os) {
		Log.d("PALETTE", "Encoding a palette to cache");
		try {
			os.write(PALETTE_MAGIC_BYTE);
			return paletteEncoder.encode(data, os);
		} catch (IOException e) {
			return false;
		}
	}

	@Override public String getId() {
		return PaletteCacheEncoder.class.getSimpleName();
	}
}

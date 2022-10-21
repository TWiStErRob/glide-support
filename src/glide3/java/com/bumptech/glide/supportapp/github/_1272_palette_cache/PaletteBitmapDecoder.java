package com.bumptech.glide.supportapp.github._1272_palette_cache;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.util.Log;

import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.supportapp.github._1013_palette.PaletteBitmap;
import com.bumptech.glide.supportapp.github._1013_palette.PaletteBitmapResource;

import androidx.palette.graphics.Palette;

public class PaletteBitmapDecoder implements ResourceDecoder<InputStream, PaletteBitmap> {
	private final ResourceDecoder<InputStream, Bitmap> bitmapDecoder;
	private final BitmapPool bitmapPool;
	private final ResourceDecoder<InputStream, Palette> paletteDecoder;
	public PaletteBitmapDecoder(
			BitmapPool bitmapPool,
			ResourceDecoder<InputStream, Bitmap> bitmapDecoder,
			ResourceDecoder<InputStream, Palette> paletteDecoder) {
		this.bitmapPool = bitmapPool;
		this.paletteDecoder = paletteDecoder;
		this.bitmapDecoder = bitmapDecoder;
	}

	@Override public Resource<PaletteBitmap> decode(InputStream source, int width, int height) throws IOException {
		if (!source.markSupported()) {
			source = new BufferedInputStream(source);
		}
		source.mark(1024); // 1k allowance for palette to figure out if it works or not
		// in practice it's most likely just 2 shorts (see ObjectInputStream.readStreamHeader)
		Palette palette = null;
		try {
			palette = paletteDecoder.decode(source, width, height).get();
		} catch (Exception ex) {
			// go back to the beginning as the palette was invalid, let's hope it's a bitmap
			source.reset();
		}
		Bitmap bitmap = bitmapDecoder.decode(source, width, height).get();
		if (palette == null) {
			Log.i("PALETTE", "Palette was not included in cache, calculate from Bitmap");
			palette = new Palette.Builder(bitmap).generate();
		}
		return new PaletteBitmapResource(new PaletteBitmap(bitmap, palette), bitmapPool);
	}

	@Override public String getId() {
		return PaletteBitmapDecoder.class.getSimpleName();
	}
}

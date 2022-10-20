package com.bumptech.glide.supportapp.github._1272_palette_cache;

import java.io.OutputStream;

import android.graphics.Bitmap;

import com.bumptech.glide.load.ResourceEncoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPoolAdapter;
import com.bumptech.glide.load.resource.SimpleResource;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.supportapp.github._1013_palette.PaletteBitmap;

import androidx.palette.graphics.Palette;

public class PaletteBitmapEncoder implements ResourceEncoder<PaletteBitmap> {
	/** BitmapEncoder doesn't need the real pool, because the resource won't ever be recycled. */
	private static final BitmapPoolAdapter FAKE_POOL = new BitmapPoolAdapter();

	private final ResourceEncoder<Bitmap> bitmapEncoder;
	private final ResourceEncoder<Palette> paletteEncoder;
	public PaletteBitmapEncoder(ResourceEncoder<Bitmap> bitmapEncoder, ResourceEncoder<Palette> paletteEncoder) {
		this.paletteEncoder = paletteEncoder;
		this.bitmapEncoder = bitmapEncoder;
	}

	@Override public boolean encode(Resource<PaletteBitmap> data, OutputStream os) {
		PaletteBitmap bitmap = data.get();
		// Resource objects are only created to satisfy the contract, they don't need to be recycled.
		boolean paletteOK = paletteEncoder.encode(new SimpleResource<>(bitmap.palette), os);
		boolean bitmapOK = bitmapEncoder.encode(new BitmapResource(bitmap.bitmap, FAKE_POOL), os);
		return bitmapOK && paletteOK;
	}

	@Override public String getId() {
		return PaletteBitmapEncoder.class.getSimpleName();
	}
}

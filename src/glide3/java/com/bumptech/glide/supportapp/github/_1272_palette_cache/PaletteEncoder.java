package com.bumptech.glide.supportapp.github._1272_palette_cache;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import android.util.Log;

import com.bumptech.glide.load.ResourceEncoder;
import com.bumptech.glide.load.engine.Resource;

import androidx.palette.graphics.Palette;

public class PaletteEncoder implements ResourceEncoder<Palette> {
	@Override public boolean encode(Resource<Palette> data, OutputStream os) {
		Log.d("PALETTE", "Encoding a palette to cache");
		PaletteSerializer palette = new PaletteSerializer(data.get());
		try {
			ObjectOutputStream stream = new ObjectOutputStream(os);
			stream.writeObject(palette);
			stream.flush();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	@Override public String getId() {
		return PaletteEncoder.class.getSimpleName();
	}
}

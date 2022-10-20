package com.bumptech.glide.supportapp.github._1272_palette_cache;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import android.util.Log;

import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.SimpleResource;

import androidx.palette.graphics.Palette;

public class PaletteDecoder implements ResourceDecoder<InputStream, Palette> {
	@Override public Resource<Palette> decode(InputStream source, int width, int height) throws IOException {
		try {
			ObjectInputStream stream = new ObjectInputStream(source);
			PaletteSerializer palette = (PaletteSerializer)stream.readObject();
			Log.d("PALETTE", "Palette loaded");
			return new SimpleResource<>(palette.getPalette());
		} catch (Exception e) {
			Log.w("PALETTE", "Cannot deserialize", e);
			throw new IOException("Cannot read palette", e);
		}
	}
	@Override public String getId() {
		return PaletteDecoder.class.getSimpleName();
	}
}

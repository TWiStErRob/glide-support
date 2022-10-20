package com.bumptech.glide.supportapp.github._1013_palette;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.palette.graphics.Palette;

/**
 * A simple wrapper for a {@link androidx.palette.graphics.Palette} and a {@link android.graphics.Bitmap}.
 */
public class PaletteBitmap {
	public final Palette palette;
	public final Bitmap bitmap;

	public PaletteBitmap(@NonNull Bitmap bitmap, Palette palette) {
		this.bitmap = bitmap;
		this.palette = palette;
	}
}

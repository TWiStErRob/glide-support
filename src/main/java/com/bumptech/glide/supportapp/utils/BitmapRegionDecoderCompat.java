package com.bumptech.glide.supportapp.utils;

import java.io.IOException;
import java.io.InputStream;

import android.graphics.BitmapRegionDecoder;
import android.os.Build;
import android.os.ParcelFileDescriptor;

import androidx.annotation.NonNull;

public class BitmapRegionDecoderCompat {

	/**
	 * @see BitmapRegionDecoder#newInstance(String)
	 */
	@SuppressWarnings("deprecation")
	public static @NonNull BitmapRegionDecoder newInstance(String source) throws IOException {
		if (Build.VERSION_CODES.S <= Build.VERSION.SDK_INT) {
			return BitmapRegionDecoder.newInstance(source);
		} else {
			return BitmapRegionDecoder.newInstance(source, false);
		}
	}

	/**
	 * @see BitmapRegionDecoder#newInstance(InputStream)
	 */
	@SuppressWarnings("deprecation")
	public static @NonNull BitmapRegionDecoder newInstance(InputStream source) throws IOException {
		if (Build.VERSION_CODES.S <= Build.VERSION.SDK_INT) {
			return BitmapRegionDecoder.newInstance(source);
		} else {
			return BitmapRegionDecoder.newInstance(source, false);
		}
	}

	/**
	 * @see BitmapRegionDecoder#newInstance(ParcelFileDescriptor)
	 */
	@SuppressWarnings("deprecation")
	public static @NonNull BitmapRegionDecoder newInstance(ParcelFileDescriptor source) throws IOException {
		if (Build.VERSION_CODES.S <= Build.VERSION.SDK_INT) {
			return BitmapRegionDecoder.newInstance(source);
		} else {
			return BitmapRegionDecoder.newInstance(source.getFileDescriptor(), false);
		}
	}
}

package com.bumptech.glide.supportapp.github._122_load_bitmap_drawable;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

public class DrawableBitmapResourceDecoder implements ResourceDecoder<Drawable, Bitmap> {
	private final BitmapPool pool;

	public DrawableBitmapResourceDecoder(Context context) {
		this(Glide.get(context).getBitmapPool());
	}

	public DrawableBitmapResourceDecoder(BitmapPool pool) {
		this.pool = pool;
	}

	@Override public Resource<Bitmap> decode(Drawable drawable, int width, int height) throws IOException {
		@SuppressLint("WrongConstant") // formatHasAlpha supports any PixelFormat value.
		Config config = PixelFormat.formatHasAlpha(drawable.getOpacity())? Config.ARGB_8888 : Config.RGB_565;
		Bitmap bitmap = pool.get(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), config);
		if (bitmap == null) {
			bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), config);
		}
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);
		return new BitmapResource(bitmap, pool);
	}

	@Override public String getId() {
		return "";
	}
}

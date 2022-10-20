package com.bumptech.glide.supportapp.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

public class TextDrawable extends Drawable {

	private final String text;
	private final Paint paint;

	public TextDrawable(String text) {
		this.text = text;

		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(220f);
		paint.setAntiAlias(true);
		paint.setFakeBoldText(true);
		paint.setShadowLayer(6f, 0, 0, Color.BLACK);
		paint.setStyle(Paint.Style.FILL);
		paint.setTextAlign(Paint.Align.CENTER);
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawText(text, getBounds().exactCenterX(), getBounds().exactCenterY(), paint);
	}

	@Override
	public void setAlpha(int alpha) {
		paint.setAlpha(alpha);
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		paint.setColorFilter(cf);
	}

	@Override
	@Deprecated
	public int getOpacity() {
		return PixelFormat.TRANSLUCENT;
	}
}

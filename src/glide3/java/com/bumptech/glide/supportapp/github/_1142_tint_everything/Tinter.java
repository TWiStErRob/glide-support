package com.bumptech.glide.supportapp.github._1142_tint_everything;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;

class Tinter {
	public static Drawable tint(Drawable input, ColorStateList tint) {
		if (input == null) {
			return null;
		}
		Drawable wrappedDrawable = DrawableCompat.wrap(input);
		DrawableCompat.setTintList(wrappedDrawable, tint);
		DrawableCompat.setTintMode(wrappedDrawable, Mode.MULTIPLY);
		return wrappedDrawable;
	}
}

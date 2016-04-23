package com.bumptech.glide.supportapp.github._1142_tint_everything;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.widget.ImageView;

import static android.content.res.ColorStateList.*;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ImageViewTarget;

import static com.bumptech.glide.supportapp.github._1142_tint_everything.Tinter.*;

public class TintTarget extends ImageViewTarget<GlideDrawable> {
	private final ColorStateList placeholderColor;
	private final ColorStateList resultColor;
	private final ColorStateList errorColor;

	public TintTarget(ImageView view, ColorStateList tintColor) {
		this(view, tintColor, tintColor, tintColor);
	}
	public TintTarget(ImageView view, @ColorInt int tintColor) {
		this(view, tintColor, tintColor, tintColor);
	}
	public TintTarget(ImageView view,
			@ColorInt int resultColor, @ColorInt int placeholderColor, @ColorInt int errorColor) {
		this(view, valueOf(placeholderColor), valueOf(resultColor), valueOf(errorColor));
	}
	public TintTarget(ImageView view,
			ColorStateList placeholderColor, ColorStateList resultColor, ColorStateList errorColor) {
		super(view);
		this.placeholderColor = placeholderColor;
		this.resultColor = resultColor;
		this.errorColor = errorColor;
	}

	@Override public void setDrawable(Drawable drawable) {
		// don't tint, this is called with a cross-fade drawable,
		// and we need the inner drawables tinted, but not this
		super.setDrawable(drawable);
	}

	@Override public void onLoadStarted(Drawable placeholder) {
		super.onLoadStarted(tint(placeholder, placeholderColor));
	}
	@Override public void onLoadFailed(Exception e, Drawable errorDrawable) {
		super.onLoadFailed(e, tint(errorDrawable, errorColor));
	}
	@Override public void onLoadCleared(Drawable placeholder) {
		super.onLoadStarted(tint(placeholder, placeholderColor));
	}
	@SuppressWarnings("unchecked")
	@Override public void onResourceReady(GlideDrawable resource, GlideAnimation glideAnimation) {
		Drawable tinted = tint(resource, resultColor);
		// animate works with drawable likely because it's accepting Drawables, but declaring GlideDrawable as generics
		if (glideAnimation == null || !glideAnimation.animate(tinted, this)) {
			view.setImageDrawable(tinted);
		}
	}

	@Override protected void setResource(GlideDrawable resource) {
		throw new UnsupportedOperationException("onResourceReady is overridden, this shouldn't be called");
	}
}

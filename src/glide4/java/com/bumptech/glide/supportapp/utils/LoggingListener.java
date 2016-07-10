package com.bumptech.glide.supportapp.utils;

import java.util.Locale;

import android.graphics.Bitmap;
import android.graphics.drawable.*;
import android.support.annotation.*;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.*;

public class LoggingListener<R> implements RequestListener<R> {
	private final int level;
	private final String name;
	private final RequestListener<R> delegate;

	public LoggingListener() {
		this("");
	}

	public LoggingListener(@NonNull String name) {
		this(Log.VERBOSE, name);
	}

	public LoggingListener(int level, @NonNull String name) {
		this(level, name, null);
	}

	public LoggingListener(RequestListener<R> delegate) {
		this(Log.VERBOSE, "", delegate);
	}

	public LoggingListener(int level, @NonNull String name, RequestListener<R> delegate) {
		this.level = level;
		this.name = name;
		this.delegate = delegate == null? NoOpRequestListener.<R>get() : delegate;
	}

	@Override public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<R> target,
			boolean isFirstResource) {
		android.util.Log.println(level, "GLIDE", String.format(Locale.ROOT,
				"%s.onLoadFailed(%s, %s, %s, %s)\n%s",
				name, e, model, strip(target), isFirst(isFirstResource), android.util.Log.getStackTraceString(e)));
		return delegate.onLoadFailed(e, model, target, isFirstResource);
	}
	@Override public boolean onResourceReady(R resource, Object model, Target<R> target, DataSource dataSource,
			boolean isFirstResource) {
		String resourceString = strip(getResourceDescription(resource));
		String targetString = strip(getTargetDescription(target));
		android.util.Log.println(level, "GLIDE", String.format(Locale.ROOT,
				"%s.onResourceReady(%s, %s, %s, %s, %s)",
				name, resourceString, model, targetString, dataSource, isFirst(isFirstResource)));
		return delegate.onResourceReady(resource, model, target, dataSource, isFirstResource);
	}

	private String isFirst(boolean isFirstResource) {
		return isFirstResource? "first" : "not first";
	}

	private String getTargetDescription(Target<R> target) {
		String result;
		if (target instanceof ViewTarget) {
			View v = ((ViewTarget)target).getView();
			LayoutParams p = v.getLayoutParams();
			result = String.format(Locale.ROOT,
					"%s(params=%dx%d->size=%dx%d)", target, p.width, p.height, v.getWidth(), v.getHeight());
		} else {
			result = String.valueOf(target);
		}
		return result;
	}

	private String getResourceDescription(R resource) {
		String result;
		if (resource instanceof Bitmap) {
			Bitmap bm = (Bitmap)resource;
			result = String.format(Locale.ROOT,
					"%s(%dx%d@%s)", resource, bm.getWidth(), bm.getHeight(), bm.getConfig());
		} else if (resource instanceof BitmapDrawable) {
			Bitmap bm = ((BitmapDrawable)resource).getBitmap();
			result = String.format(Locale.ROOT,
					"%s(%dx%d@%s)", resource, bm.getWidth(), bm.getHeight(), bm.getConfig());
		} else if (resource instanceof Drawable) {
			Drawable d = (Drawable)resource;
			result = String.format(Locale.ROOT,
					"%s(%dx%d)", resource, d.getIntrinsicWidth(), d.getIntrinsicHeight());
		} else {
			result = String.valueOf(resource);
		}
		return result;
	}

	private static String strip(Object text) {
		return String.valueOf(text).replaceAll("(com|android|net|org)(\\.[a-z]+)+\\.", "");
	}
}

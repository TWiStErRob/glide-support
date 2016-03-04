package com.bumptech.glide.supportapp.utils;

import java.util.Locale;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.*;

public class LoggingListener<R> implements RequestListener<R> {
	private final String name;
	public LoggingListener() {
		this("");
	}
	public LoggingListener(String name) {
		this.name = name;
	}
	@Override public boolean onLoadFailed(
			@Nullable GlideException e, Object model, Target<R> target, boolean isFirstResource) {
		android.util.Log.d("GLIDE", String.format(Locale.ROOT,
				"%s.onLoadFailed(%s, %s, %s, %s)",
				name, e, model, strip(target), isFirstResource), e);
		return false;
	}
	@Override public boolean onResourceReady(
			R resource, Object model, Target<R> target, DataSource dataSource, boolean isFirstResource) {
		String resourceString;
		if (resource instanceof Bitmap) {
			Bitmap bm = (Bitmap)resource;
			resourceString = String.format("%s(%dx%d@%s)", resource, bm.getWidth(), bm.getHeight(), bm.getConfig());
		} else {
			resourceString = String.valueOf(resource);
		}

		String targetString;
		if (target instanceof ViewTarget) {
			View v = ((ViewTarget)target).getView();
			LayoutParams p = v.getLayoutParams();
			targetString = String.format("%s(%dx%d->%dx%d)", target, p.width, p.height, v.getWidth(), v.getHeight());
		} else {
			targetString = String.valueOf(target);
		}
		android.util.Log.d("GLIDE", String.format(Locale.ROOT,
				"%s.onResourceReady(%s, %s, %s, %s, %s)",
				name, strip(resourceString), model, strip(targetString), dataSource, isFirstResource));
		return false;
	}
	private static String strip(Object text) {
		return String.valueOf(text).replaceAll("(com|android|net|org)(\\.[a-z]+)+\\.", "");
	}
}

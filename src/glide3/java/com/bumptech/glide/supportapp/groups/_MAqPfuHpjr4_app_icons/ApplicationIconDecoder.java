package com.bumptech.glide.supportapp.groups._MAqPfuHpjr4_app_icons;

import java.io.IOException;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.drawable.DrawableResource;
import com.bumptech.glide.util.Util;

class ApplicationIconDecoder implements ResourceDecoder<ApplicationInfo, Drawable> {
	private final Context context;
	public ApplicationIconDecoder(Context context) {
		this.context = context;
	}
	@Override public Resource<Drawable> decode(ApplicationInfo source, int width, int height) throws IOException {
		Drawable icon = context.getPackageManager().getApplicationIcon(source);
		return new DrawableResource<Drawable>(icon) {
			@Override public int getSize() { // best effort
				if (drawable instanceof BitmapDrawable) {
					return Util.getBitmapByteSize(((BitmapDrawable)drawable).getBitmap());
				} else {
					return 1;
				}
			}
			@Override public void recycle() { /* not from our pool */ }
		};
	}
	@Override public String getId() {
		return "ApplicationInfoToDrawable";
	}
}

package com.bumptech.glide.supportapp.github._122_load_bitmap_drawable;

import java.io.IOException;

import android.graphics.drawable.Drawable;

import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.drawable.DrawableResource;

class DrawableResourceDecoder implements ResourceDecoder<Drawable, Drawable> {
	@Override public Resource<Drawable> decode(Drawable source, int width, int height) throws IOException {
		return new DrawableResource<Drawable>(source) {
			@Override public int getSize() {
				return 1;
			}
			@Override public void recycle() {
			}
		};
	}
	@Override public String getId() {
		return "";
	}
}

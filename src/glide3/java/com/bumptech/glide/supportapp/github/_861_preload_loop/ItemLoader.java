package com.bumptech.glide.supportapp.github._861_preload_loop;

import java.io.InputStream;

import android.content.Context;

import com.bumptech.glide.load.model.*;
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;

class ItemLoader extends BaseGlideUrlLoader<Item> {
	public ItemLoader(Context context) {
		super(context);
	}
	@Override protected String getUrl(Item model, int width, int height) {
		return model.getImageUrl();
	}

	public static class Factory implements ModelLoaderFactory<Item, InputStream> {
		@Override public ModelLoader<Item, InputStream> build(Context context, GenericLoaderFactory factories) {
			return new ItemLoader(context);
		}
		@Override public void teardown() {
		}
	}
}

package com.bumptech.glide.supportapp.github._556_data_uri_via_POST;

import java.io.InputStream;

import android.content.Context;

import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.stream.StreamModelLoader;

// TODO read https://github.com/bumptech/glide/wiki/Downloading-custom-sizes-with-Glide
class ImageModelLoader implements StreamModelLoader<Image> {
	@Override public DataFetcher<InputStream> getResourceFetcher(final Image model, int width, int height) {
		return new JSONImageFetcher(model);
	}

	public static class Factory implements ModelLoaderFactory<Image, InputStream> {
		public Factory() {
		}
		@Override public ModelLoader<Image, InputStream> build(Context context, GenericLoaderFactory factories) {
			return new ImageModelLoader();
		}
		@Override public void teardown() {
		}
	}
}

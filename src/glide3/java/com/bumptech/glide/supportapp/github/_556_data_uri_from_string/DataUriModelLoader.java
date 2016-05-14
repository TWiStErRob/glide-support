package com.bumptech.glide.supportapp.github._556_data_uri_from_string;

import java.io.InputStream;

import android.content.Context;

import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.*;
import com.bumptech.glide.load.model.stream.*;

// TODO read https://github.com/bumptech/glide/wiki/Downloading-custom-sizes-with-Glide
class DataUriModelLoader implements StreamModelLoader<String> {
	private final ModelLoader<String, InputStream> defaultLoader;
	public DataUriModelLoader(ModelLoader<String, InputStream> defaultLoader) {
		this.defaultLoader = defaultLoader;
	}

	private static final String PREFIX = "data:image/png;base64,";
	@Override public DataFetcher<InputStream> getResourceFetcher(final String model, int width, int height) {
		if (model.startsWith(PREFIX)) { // TODO better matching based on your needs
			return new Base64StreamDataFetcher(model.substring(PREFIX.length()));
		} else {
			return defaultLoader.getResourceFetcher(model, width, height);
		}
	}

	public static class Factory implements ModelLoaderFactory<String, InputStream> {
		public Factory() {
		}
		@Override public ModelLoader<String, InputStream> build(Context context, GenericLoaderFactory factories) {
			// StreamStringLoader is hardcoded, because we've already overwritten String -> InputStream loader
			// This will be better handled in Glide v4.
			return new DataUriModelLoader(new StreamStringLoader(context));
		}
		@Override public void teardown() {
		}
	}
}

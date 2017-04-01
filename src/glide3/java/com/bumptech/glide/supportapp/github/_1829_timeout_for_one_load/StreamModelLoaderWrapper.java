package com.bumptech.glide.supportapp.github._1829_timeout_for_one_load;

import java.io.InputStream;

import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.stream.StreamModelLoader;

class StreamModelLoaderWrapper<T> implements StreamModelLoader<T> {
	private final ModelLoader<T, InputStream> wrapped;

	StreamModelLoaderWrapper(ModelLoader<T, InputStream> wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public DataFetcher<InputStream> getResourceFetcher(T model, int width, int height) {
		return wrapped.getResourceFetcher(model, width, height);
	}
}

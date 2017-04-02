package com.bumptech.glide.supportapp.github._1829_timeout_for_one_load;

import java.io.InputStream;

import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.stream.StreamModelLoader;

/**
 * Workaround for <a href="https://github.com/bumptech/glide/issues/1832">bumptech/glide#1832</a>.
 *
 * @deprecated not needed after 3.8.0
 */
@Deprecated
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

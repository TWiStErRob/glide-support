package com.bumptech.glide.supportapp.utils;

import java.io.InputStream;

import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.stream.StreamModelLoader;

public class NetworkDisablingLoader<T> implements StreamModelLoader<T> {
	@Override public DataFetcher<InputStream> getResourceFetcher(final T model, int width, int height) {
		return new NetworkDisablingFetcher(model);
	}
}

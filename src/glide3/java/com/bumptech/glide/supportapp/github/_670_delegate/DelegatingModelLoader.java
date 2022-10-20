package com.bumptech.glide.supportapp.github._670_delegate;

import java.io.InputStream;

import android.content.Context;

import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.stream.StreamModelLoader;

abstract class DelegatingModelLoader<From, To> implements StreamModelLoader<From> {
	public DelegatingModelLoader(Context context, ModelLoader<To, InputStream> loader) {
		this.loader = loader;
	}
	private final ModelLoader<To, InputStream> loader;

	@Override public DataFetcher<InputStream> getResourceFetcher(From model, int width, int height) {
		return loader.getResourceFetcher(convertModel(model), width, height);
	}
	protected abstract To convertModel(From model);

	public static abstract class Factory<From, To> implements ModelLoaderFactory<From, InputStream> {
		private final Class<To> toModelType;
		public Factory(Class<To> toModelType) {
			this.toModelType = toModelType;
		}

		@Override public ModelLoader<From, InputStream> build(Context context, GenericLoaderFactory factories) {
			ModelLoader<To, InputStream> delegate = factories.buildModelLoader(toModelType, InputStream.class);
			return new DelegatingModelLoader<From, To>(context, delegate) {
				@Override protected To convertModel(From model) {
					return null; // FIXME
				}
			};
		}

		@Override public void teardown() {
		}
	}
}

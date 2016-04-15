package com.bumptech.glide.supportapp.stackoverflow._36642052_stetho_integration;

import java.io.InputStream;

import android.content.Context;

import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.*;

public class StethoHttpUrlGlideUrlLoader implements ModelLoader<GlideUrl, InputStream> {
	private final ModelCache<GlideUrl, GlideUrl> modelCache;

	public static class Factory implements ModelLoaderFactory<GlideUrl, InputStream> {
		private final ModelCache<GlideUrl, GlideUrl> modelCache = new ModelCache<>(500);

		@Override public ModelLoader<GlideUrl, InputStream> build(Context context, GenericLoaderFactory factories) {
			return new StethoHttpUrlGlideUrlLoader(modelCache);
		}

		@Override public void teardown() {
			// Do nothing.
		}
	}

	private StethoHttpUrlGlideUrlLoader(ModelCache<GlideUrl, GlideUrl> modelCache) {
		this.modelCache = modelCache;
	}

	@Override public DataFetcher<InputStream> getResourceFetcher(GlideUrl model, int width, int height) {
		// GlideUrls memoize parsed URLs so caching them saves a few object instantiations and time spent parsing urls.
		GlideUrl url = model;
		if (modelCache != null) {
			url = modelCache.get(model, 0, 0);
			if (url == null) {
				modelCache.put(model, 0, 0, model);
				url = model;
			}
		}
		return new StethoHttpUrlFetcher(url);
	}
}

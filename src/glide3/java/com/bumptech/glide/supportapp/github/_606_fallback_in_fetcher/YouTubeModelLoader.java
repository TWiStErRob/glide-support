package com.bumptech.glide.supportapp.github._606_fallback_in_fetcher;

import java.io.InputStream;

import android.content.Context;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.*;
import com.bumptech.glide.load.model.stream.StreamModelLoader;

class YouTubeModelLoader implements StreamModelLoader<YouTubeVideo> {
	private final ModelLoader<String, InputStream> loader;
	public YouTubeModelLoader(ModelLoader<String, InputStream> loader) {
		this.loader = loader;
	}
	@Override public DataFetcher<InputStream> getResourceFetcher(final YouTubeVideo model, final int width,
			final int height) {
		return new DataFetcher<InputStream>() {
			private DataFetcher<InputStream> fetcher;

			@Override public String getId() {
				return model.id; // assuming that if SDUrl failed once, it'll fail the next time
				// so it's better to cache MDUrl in that case for this video, forever
			}

			@Override public InputStream loadData(Priority priority) throws Exception {
				try {
					fetcher = loader.getResourceFetcher(model.getSDUrl(), width, height);
					return fetcher.loadData(priority);
				} catch (Exception ex) {
					if (fetcher != null) {
						fetcher.cleanup();
					}
					fetcher = loader.getResourceFetcher(model.getMDUrl(), width, height);
					return fetcher.loadData(priority);
				}
			}

			@Override public void cleanup() {
				if (fetcher != null) {
					fetcher.cleanup();
				}
			}
			@Override public void cancel() {
				if (fetcher != null) {
					fetcher.cancel();
				}
			}
		};
	}

	static class Factory implements ModelLoaderFactory<YouTubeVideo, InputStream> {
		@Override public ModelLoader<YouTubeVideo, InputStream> build(Context context,
				GenericLoaderFactory factories) {
			return new YouTubeModelLoader(factories.buildModelLoader(String.class, InputStream.class));
		}
		@Override public void teardown() {
		}
	}
}

package com.bumptech.glide.supportapp.github._699_mp3_cover;

import java.io.InputStream;

import android.content.Context;

import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.stream.StreamModelLoader;

class AudioCoverLoader implements StreamModelLoader<AudioCover> {
	@Override public DataFetcher<InputStream> getResourceFetcher(AudioCover model, int width, int height) {
		return new AudioCoverFetcher(model);
	}

	static class Factory implements ModelLoaderFactory<AudioCover, InputStream> {
		@Override public ModelLoader<AudioCover, InputStream> build(Context context, GenericLoaderFactory factories) {
			return new AudioCoverLoader();
		}
		@Override public void teardown() {
		}
	}
}

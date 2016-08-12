package com.bumptech.glide.supportapp.github._1406_drawable_fading;

import java.io.IOException;

import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.SimpleResource;

public class SimpleResourceDecoder<T> implements ResourceDecoder<T, T> {
	@Override public Resource<T> decode(T source, int width, int height) throws IOException {
		return new SimpleResource<>(source);
	}

	@Override public String getId() {
		return SimpleResourceDecoder.class.getSimpleName();
	}
}

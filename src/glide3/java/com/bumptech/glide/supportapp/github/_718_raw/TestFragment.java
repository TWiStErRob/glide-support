package com.bumptech.glide.supportapp.github._718_raw;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.bumptech.glide.supportapp.GlideImageFragment;

public class TestFragment extends GlideImageFragment {
	@Override protected void load(Context context) throws Exception {
		String model = "http://.../....dng";
		//File model = ...;
		Glide
				.with(context)
				.load(model)
				.asBitmap()
				.imageDecoder(new RawStreamDecoder(context))
				.encoder(new RawStreamEncoder())
				.cacheDecoder(new FileToStreamDecoder<>(new RawStreamDecoder(context)))
				.into(imageView)
		;
	}
}

package com.bumptech.glide.supportapp.github._829_bitmapfactory_rotate;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.supportapp.GlideImageFragment;

public class TestFragment extends GlideImageFragment {
	@Override protected void load(Context context) throws Exception {
		String url = "https://s3.amazonaws.com/timeset-photos/RackMultipart20151217-4907-vnn7er.jpeg";
		url = "https://cloud.githubusercontent.com/assets/2364583/11941136/528db8a0-a7e2-11e5-9327-d6cc11548c91.JPG";
		Glide
				.with(context)
				.load(url)
				.asBitmap()
				.format(DecodeFormat.PREFER_ARGB_8888)
				.skipMemoryCache(true)
				.diskCacheStrategy(DiskCacheStrategy.RESULT)
				.into(imageView);
	}
}

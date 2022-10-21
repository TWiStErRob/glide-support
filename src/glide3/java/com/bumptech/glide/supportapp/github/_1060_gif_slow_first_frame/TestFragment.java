package com.bumptech.glide.supportapp.github._1060_gif_slow_first_frame;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.supportapp.GlideImageFragment;
import com.bumptech.glide.supportapp.utils.LoggingListener;

public class TestFragment extends GlideImageFragment {
	@Override protected void load(Context context) throws Exception {
		String url =
//				"https://cloud.githubusercontent.com/assets/3822339/14021484/b8814c80-f201-11e5-93b0-b50076381286.gif";
				"https://cloud.githubusercontent.com/assets/2906988/14031433/1bfc4106-f20d-11e5-94c6-861bbf3f4991.gif";
		Glide
				.with(context)
				.load(url)
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				.listener(new LoggingListener<String, GlideDrawable>())
				.into(imageView)
		;
	}
}

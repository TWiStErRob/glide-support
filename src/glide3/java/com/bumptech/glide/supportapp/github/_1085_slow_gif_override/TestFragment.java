package com.bumptech.glide.supportapp.github._1085_slow_gif_override;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.*;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gifbitmap.GifBitmapWrapper;
import com.bumptech.glide.load.resource.transcode.*;
import com.bumptech.glide.supportapp.*;
import com.bumptech.glide.supportapp.utils.LoggingListener;

public class TestFragment extends GlideDualImageFragment {
	// https://cloud.githubusercontent.com/assets/2437726/14014172/3372df2c-f1b1-11e5-814a-90650a752a62.gif
	private final String url = "/sdcard/3372df2c-f1b1-11e5-814a-90650a752a62.gif";
	@Override protected void load1(Context context, ImageView imageView) throws Exception {
		Glide
				.with(this)
				.load(url)
				.diskCacheStrategy(DiskCacheStrategy.RESULT)
				.listener(new LoggingListener<String, GlideDrawable>("first"))
				.dontAnimate()
				.dontTransform()
				.override(360, 360)
				.into(imageView)
		;
	}
	@Override protected void load2(Context context, ImageView imageView) throws Exception {
		Glide
				.with(this)
				.load(url)
				.diskCacheStrategy(DiskCacheStrategy.RESULT)
				.listener(new LoggingListener<String, GlideDrawable>("second"))
				.dontAnimate()
				.dontTransform()
//				.transcoder(new GifBitmapWrapperDrawableTranscoder(new GlideBitmapDrawableTranscoder(context)) {
//					@Override public Resource<GlideDrawable> transcode(Resource<GifBitmapWrapper> toTranscode) {
//						try {
//							Thread.sleep(3000);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//						return super.transcode(toTranscode);
//					}
//				})
				.override(360, 360)
				.thumbnail(Glide
						.with(this)
						.load(url)
						.diskCacheStrategy(DiskCacheStrategy.RESULT)
						.listener(new LoggingListener<String, GlideDrawable>("second-thumb"))
						.animate(R.anim.abc_fade_in)
						.override(72, 72)
				)
				.into(imageView)
		;
	}
}

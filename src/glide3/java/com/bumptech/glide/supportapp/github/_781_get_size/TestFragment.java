package com.bumptech.glide.supportapp.github._781_get_size;

import java.io.InputStream;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.util.Log;

import com.bumptech.glide.*;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.model.stream.StreamUriLoader;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.supportapp.GlideImageFragment;
import com.bumptech.glide.supportapp.utils.LoggingListener;

public class TestFragment extends GlideImageFragment {
	private GenericRequestBuilder<Uri, InputStream, Options, Size> SIZE_REQUEST;
	@Override public void onAttach(Activity activity) {
		super.onAttach(activity);
		SIZE_REQUEST = Glide
				.with(this)
				.using(new StreamUriLoader(activity), InputStream.class)
				.from(Uri.class)
				.as(Options.class)
				.transcode(new OptionsSizeResourceTranscoder(), Size.class)
				.sourceEncoder(new StreamEncoder())
				.cacheDecoder(new BitmapSizeDecoder())
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				.listener(new LoggingListener<Uri, Size>());
	}
	@Override protected void load(Context context) throws Exception {
		Uri uri = Uri.parse("http://i.imgur.com/etgBAG4.jpg");
		// normal load to display
		Glide
				.with(this)
				.load(uri)
				.diskCacheStrategy(DiskCacheStrategy.ALL)
				.listener(new LoggingListener<Uri, GlideDrawable>())
				.into(imageView)
		;
		// get original size
		SIZE_REQUEST
				.load(uri)
				.into(new SimpleTarget<Size>() {
					@Override public void onResourceReady(Size resource, GlideAnimation glideAnimation) {
						Log.wtf("SIZE", String.format(Locale.ROOT, "%dx%d", resource.width, resource.height));
					}
				})
		;

		Glide
				.with(this)
				.load(uri)
				.asBitmap()
				.transcode(new BitmapSizeTranscoder(), Size.class)
				.into(new SimpleTarget<Size>() {
					@Override public void onResourceReady(Size resource, GlideAnimation<? super Size> glideAnimation) {
						Log.wtf("SIZE", String.format(Locale.ROOT, "%dx%d", resource.width, resource.height));
					}
				})
		;
	}
}

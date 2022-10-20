package com.bumptech.glide.supportapp.github._1829_timeout_for_one_load;

import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.signature.StringSignature;
import com.bumptech.glide.supportapp.GlideDualImageFragment;
import com.bumptech.glide.supportapp.R;
import com.bumptech.glide.supportapp.utils.LoggingListener;
import com.bumptech.glide.supportapp.utils.LoggingTarget;

import okhttp3.OkHttpClient;

public class TestFragment extends GlideDualImageFragment {
	private OkHttpClient longTimeoutClient;

	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		longTimeoutClient = new OkHttpClient.Builder() // default timeouts are 10 seconds
		                                               .connectTimeout(15, TimeUnit.SECONDS)
		                                               .readTimeout(15, TimeUnit.SECONDS)
		                                               .build();
	}

	@Override protected void load1(Context context, ImageView imageView) throws Exception {
		Glide
				.with(this)
				// default timeout is 2.5 seconds (com.bumptech.glide.load.data.HttpUrlFetcher)
				.load("https://httpbin.org/delay/12") // force a timeout: 2.5 < 12
				.signature(new StringSignature("load1")) // distinguish from other load to make sure loader is picked up
				.placeholder(R.drawable.glide_placeholder)
				.error(R.drawable.glide_error)
				.listener(new LoggingListener<String, GlideDrawable>("load1"))
				.into(new LoggingTarget<>("load1", Log.VERBOSE, new GlideDrawableImageViewTarget(imageView)))
		;
	}

	@Override protected void load2(Context context, ImageView imageView) throws Exception {
		Glide
				.with(this)
				.using(new StreamModelLoaderWrapper<>(new OkHttpUrlLoader(longTimeoutClient)))
				.load(new GlideUrl("https://httpbin.org/delay/12")) // timeout increased: 15 > 10, so it'll pass
				.signature(new StringSignature("load2")) // distinguish from other load to make sure loader is picked up
				.placeholder(R.drawable.glide_placeholder)
				// since the test URL returns a JSON stream, the load will fail,
				// let's still add an error to see that the load fails slower than the other,
				// meaning the image was actually tried to be decoded
				.error(R.drawable.glide_error)
				.listener(new LoggingListener<GlideUrl, GlideDrawable>("load2"))
				.into(new LoggingTarget<>("load2", Log.VERBOSE, new GlideDrawableImageViewTarget(imageView)))
		;
	}
}

package com.bumptech.glide.supportapp.random;

import android.content.Context;
import android.os.AsyncTask;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.supportapp.*;
import com.bumptech.glide.supportapp.utils.LoggingListener;

public class Test_GlideSingleImage extends GlideImageFragment {
	@Override protected void load(final Context context) {
		//noinspection unchecked
		new AsyncTask<Object, Object, Object>() {
			@Override protected Object doInBackground(Object[] params) {
				Glide.get(context).clearDiskCache();
				return null;
			}
			@Override protected void onPostExecute(Object result) {
				loadImage(context);
			}
		}.execute();
	}

	private void loadImage(Context context) {
		//Glide.setup(new GlideBuilder(context).setBitmapPool(new BitmapPoolAdapter()));
		Glide
				.with(this)
				.load("https://cloud.githubusercontent.com/assets/784063/8439742/bbe02ae8-1f22-11e5-8676-c7a1d525782d.jpg")
				.placeholder(R.drawable.glide_placeholder)
				.error(R.drawable.glide_error)
				//.skipMemoryCache(true)
				//.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				.listener(new LoggingListener<String, GlideDrawable>())
				.into(imageView);
	}
}

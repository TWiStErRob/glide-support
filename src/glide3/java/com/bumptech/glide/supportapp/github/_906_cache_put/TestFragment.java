package com.bumptech.glide.supportapp.github._906_cache_put;

import java.io.*;

import android.annotation.*;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION_CODES;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache.Writer;
import com.bumptech.glide.load.resource.bitmap.*;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.StringSignature;
import com.bumptech.glide.supportapp.*;

public class TestFragment extends GlideImageFragment {
	@SuppressLint("SdCardPath")
	@Override protected void load(Context context) throws Exception {
		Glide
				.with(this)
				.load(Uri.parse("file:///sdcard/001.jpg")) // doesn't matter what model you load
				.asBitmap()
				.diskCacheStrategy(DiskCacheStrategy.NONE)
				.override(2000, 2000) // size of the image to upload
				.dontTransform()
				.listener(new RequestListener<Uri, Bitmap>() {
					@Override public boolean onException(Exception e, Uri model, Target<Bitmap> target,
							boolean isFirstResource) {
						return false;
					}
					@Override public boolean onResourceReady(Bitmap resource, Uri model, Target<Bitmap> target,
							boolean isFromMemoryCache, boolean isFirstResource) {
						upload(resource);
						return false;
					}
				})
				.into(imageView)
		;
	}
	private void upload(Bitmap resource) {
		new AsyncTask<Bitmap, Void, String>() {
			@Override protected String doInBackground(Bitmap... params) {
				Bitmap bitmap = params[0];
				String url = "https://aws.amazon.com/........png";
				// TODO upload bitmap and get a resulting url,
				// placeholder is there to show that it works even with a malformed non-existent url
				cache(url, bitmap);
				return url;
			}
			@Override protected void onPostExecute(String url) {
				Glide
						.with(TestFragment.this)
						.load(url)
						.diskCacheStrategy(DiskCacheStrategy.SOURCE) // must use this to hit the just-cached image
						.into(imageView)
				;
			}
		}.execute(resource);
	}

	private void cache(String url, final Bitmap bitmap) {
		Key key = new StringSignature(url);
		// the key here is that Engine uses fetcher.getId() for constructing OriginalKey from EngineKey
		// see Engine.load and also signature can be ignored because it is an EmptySignature instance for most
		App.getInstance().getDiskCache().put(key, new Writer() {
			@TargetApi(VERSION_CODES.KITKAT) // for try-with-resources
			@Override public boolean write(File file) {
				try (OutputStream out = new FileOutputStream(file)) {
					// mimic default behavior you can also use Bitmap.compress
					BitmapPool pool = Glide.get(getContext()).getBitmapPool();
					BitmapResource resource = BitmapResource.obtain(bitmap, pool);
					new BitmapEncoder().encode(resource, out);
					return true;
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
		});
	}
}

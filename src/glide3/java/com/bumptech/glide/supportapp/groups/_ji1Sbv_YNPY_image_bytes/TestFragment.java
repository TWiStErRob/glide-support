package com.bumptech.glide.supportapp.groups._ji1Sbv_YNPY_image_bytes;

import android.content.Context;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.supportapp.GlideImageFragment;

public class TestFragment extends GlideImageFragment {
	private static final int MAX_IMAGE_SIZE = 2000;
	@Override protected void load(Context context) throws Exception {
		Uri uri = getActivity().getIntent().getData();
		Glide
				.with(this)
				.load(uri)
				.asBitmap()
				.toBytes(CompressFormat.JPEG, 80)
				.atMost()
				.override(MAX_IMAGE_SIZE, MAX_IMAGE_SIZE)
				.diskCacheStrategy(DiskCacheStrategy.NONE)
				.skipMemoryCache(true)
				.into(new SimpleTarget<byte[]>() {
					@Override public void onResourceReady(byte[] resource, GlideAnimation<? super byte[]> ignore) {
						// save resource to a File
						// send it somewhere in a Stream (e.g. Google Drive)
						// save it in a Database as a Blob
					}
					@Override public void onLoadFailed(Exception ex, Drawable ignore) {
						Log.e("GLIDE", "Loading image failed", ex);
					}
				})
		;
	}
}

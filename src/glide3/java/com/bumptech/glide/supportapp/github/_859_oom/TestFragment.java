package com.bumptech.glide.supportapp.github._859_oom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.*;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.*;
import com.bumptech.glide.supportapp.GlideImageFragment;

public class TestFragment extends GlideImageFragment {
	@Override protected void load(Context context) throws Exception {
		String url = "https://upload.wikimedia.org/wikipedia/commons/c/c6/BlankMap-World-large3.png";
		BitmapRequestBuilder<String, Bitmap> oomRequest = Glide
				.with(context)
				.fromString()
				.asBitmap()
				.imageDecoder(new OOMReadyStreamBitmapDecoder(context))
				//.cacheDecoder(new FileToStreamDecoder<>(new OOMReadyStreamBitmapDecoder(context))) // needed for .diskCacheStrategy(SOURCE|ALL)
				//.error(R.drawable.ic_launcher) // displayed when OOM (because throw RuntimeException)
				//.override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) // to make sure it OOMs (url is huge)
				;

		oomRequest
				.load(url)
				.listener(new RequestListener<String, Bitmap>() {
					@Override public boolean onException(Exception e, String model, Target<Bitmap> target,
							boolean isFirstResource) {
						if (e.getCause() instanceof OutOfMemoryError) {
							// do something
							// you also have model available and if you return true, .error() won't be displayed
						}
						return false;
					}
					@Override public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target,
							boolean isFromMemoryCache, boolean isFirstResource) {
						return false;
					}
				})
		;

		oomRequest
				.load(url)
				.into(new BitmapImageViewTarget(imageView) {
					@Override public void onLoadFailed(Exception e, Drawable errorDrawable) {
						if (e.getCause() instanceof OutOfMemoryError) {
							// do something
						}
						super.onLoadFailed(e, errorDrawable);
					}
				})
		;
	}
}

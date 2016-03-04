package com.bumptech.glide.supportapp.github._840_bitmap_crossfade;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.*;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.supportapp.GlideImageFragment;

import static com.bumptech.glide.supportapp.github._840_bitmap_crossfade.BitmapCrossfadeListener.*;

/** @see com.bumptech.glide.supportapp.github._605_bitmap_crossfade.TestFragment */
public class TestFragment extends GlideImageFragment {
	@Override protected void load(Context context) throws Exception {
		String url = "http://www.online-image-editor.com//styles/2014/images/example_image.png";
		String url2 =
				"http://a5.mzstatic.com/us/r30/Purple5/v4/5a/2e/e9/5a2ee9b3-8f0e-4f8b-4043-dd3e3ea29766/icon128-2x.png";
		BitmapRequestBuilder<String, Bitmap> request = Glide
				.with(context)
				.load(url)
				.asBitmap()
				.format(DecodeFormat.PREFER_ARGB_8888)
				.thumbnail(Glide
						.with(context)
						.load(url2)
						.asBitmap()
				);
		loadProper(request);
		loadHacky(request);
		loadHackyAlt(request);
	}

	private void loadProper(BitmapRequestBuilder<String, Bitmap> request) {
		request
				.dontAnimate() // turn off defaults since we're handling it
				.listener(new BitmapCrossfadeListener<>())
				.into(imageView);
	}

	private void loadHacky(BitmapRequestBuilder<String, Bitmap> request) {
		Github840_GlideRequestBuilderAccessor.animate(request, CROSS_FADE_FACTORY);
		request.into(imageView);
	}

	private void loadHackyAlt(BitmapRequestBuilder<String, Bitmap> request) {
		Github840_GlideRequestBuilderAccessor.animateAlt(request, CROSS_FADE_FACTORY).into(imageView);
	}
}

package com.bumptech.glide.supportapp.github._122_load_bitmap_drawable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapEncoder;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.StreamBitmapDecoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.bumptech.glide.signature.StringSignature;
import com.bumptech.glide.supportapp.GlideImageFragment;
import com.bumptech.glide.supportapp.utils.PassthroughModelLoader;

import androidx.core.content.ContextCompat;

public class TestFragment extends GlideImageFragment {
	@SuppressWarnings("unchecked")
	@Override protected void load(Context context) {
		GenericRequestBuilder<Bitmap, Bitmap, Bitmap, Bitmap> glide = Glide
				.with(context)
				.using(new PassthroughModelLoader<Bitmap, Bitmap>(), Bitmap.class)
				.from(Bitmap.class)
				.as(Bitmap.class)
				.decoder(new BitmapBitmapResourceDecoder(context))
				.cacheDecoder(new FileToStreamDecoder<Bitmap>(new StreamBitmapDecoder(context)))
				.encoder(new BitmapEncoder())
				// or .diskCacheStrategy(DiskCacheStrategy.NONE) instead of last 2
				;

		// simulate a bitmap input
		Drawable drawable = ContextCompat.getDrawable(context, android.R.drawable.sym_def_app_icon);
		Bitmap bitmap = Bitmap.createBitmap(
				drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);

		glide
				.clone()
				.load(bitmap)
				.signature(new StringSignature("android.R.drawable.sym_def_app_icon")) // required for caching
				.diskCacheStrategy(DiskCacheStrategy.NONE) // but can't really cache it, see #122 comments
				.transform(new CenterCrop(context))
				.into(imageView)
		;
	}
}

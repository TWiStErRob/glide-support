package com.bumptech.glide.supportapp.stackoverflow._31867322_generate_bitmap;

import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

/** Handles pooling to reduce/prevent GC lagging from too many{@link Bitmap#createBitmap}s */
class GenerateBitmapResourceDecoder implements ResourceDecoder<GenerateParams, Bitmap> {
	private final Context context;
	public GenerateBitmapResourceDecoder(Context context) {
		this.context = context;
	}
	@Override public Resource<Bitmap> decode(GenerateParams source, int width, int height) throws IOException {
		BitmapPool pool = Glide.get(context).getBitmapPool();
		Bitmap bitmap = pool.getDirty(width, height, Bitmap.Config.ARGB_8888);
		if (bitmap == null) {
			bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		}
		Generators.imageWithTextNoLayout(context, bitmap, source);
		return BitmapResource.obtain(bitmap, pool);
	}
	@Override public String getId() {
		// be careful if you change the Generator implementation you have to change this
		// otherwise the users may see a cached image; or clear cache on app update
		return "com.example.MyImageGenerator";
	}
}

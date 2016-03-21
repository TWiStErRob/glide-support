package com.bumptech.glide.supportapp.github._1077_rounded_placeholder;

import android.content.Context;
import android.graphics.*;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.*;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.supportapp.*;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TestFragment extends GlideImageFragment {
	private Drawable placeholder;

	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		placeholder = transformDrawable(
				// has white background because it's not transparent, so rounding will be visible
				ContextCompat.getDrawable(getContext(), R.drawable.glide_jpeg),
				// transformation to be applied
				new RoundedCornersTransformation(getContext(), 100, 0),
				// size of the target in pixels
				256
		);
	}

	@Override protected void load(Context context) throws Exception {
		Glide
				.with(this)
				.load((String)null) // force fallback
				.fallback(placeholder) // works the same as error/placeholder
				.into(imageView)
		;
	}

	private BitmapDrawable transformDrawable(Drawable drawable, Transformation<Bitmap> transform, int size) {
		// render original
		Bitmap bitmap = Bitmap.createBitmap(size, size, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, size, size);
		drawable.draw(canvas);
		// make rounded
		Resource<Bitmap> original = BitmapResource.obtain(bitmap, Glide.get(getContext()).getBitmapPool());
		Resource<Bitmap> rounded = transform.transform(original, size, size);
		if (!original.equals(rounded)) {
			original.recycle();
		}
		return new BitmapDrawable(getResources(), rounded.get());
	}
}

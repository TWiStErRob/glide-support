package com.bumptech.glide.supportapp.github._832_raw;

import java.io.File;

import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.supportapp.GlideImageFragment;

public class TestFragment extends GlideImageFragment {
	@Override protected void load(Context context) throws Exception {
		// TODO get from Adapter data?
		File path = new File(Environment.getExternalStorageDirectory(), "orig_w678_h427.raw");
		int width = 678; // TODO parse from file name
		int height = 427; // TODO parse from file name
		// == Glide.with(context).load(path).apply(RequestOptions.overrideOf(width, height)).into(imageView);
		Glide
				.with(context)
				//.asBitmap() // optional
				.load(path)
				.apply(new RequestOptions()
								.override(width, height) // tell Glide what is the expected size (from file name)
						//.diskCacheStrategy(DiskCacheStrategy.ALL) // optional, verified works (caches resized PNG files)
						//.decode(Bitmap.class) // optional, superflous if asBitmap()
						//.dontAnimate() // optional
						//.dontTransform() // optional
				).into(imageView)
		;
	}
}

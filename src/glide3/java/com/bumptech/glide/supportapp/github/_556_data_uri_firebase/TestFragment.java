package com.bumptech.glide.supportapp.github._556_data_uri_firebase;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.supportapp.GlideImageFragment;
import com.bumptech.glide.supportapp.R;

public class TestFragment extends GlideImageFragment {
	@Override protected void load(Context context) {
		//Glide.setup(new GlideBuilder(context).setBitmapPool(new BitmapPoolAdapter()));
		//Glide.get(context).register(FirebaseImage.class, InputStream.class, new FirebaseModelLoader.Factory(null));
		Glide
				.with(context)
				.load(new FirebaseImage("data:image/jpeg;base64," + getString(R.string.glide_base64)))
				.placeholder(R.drawable.glide_placeholder)
				.error(R.drawable.glide_error)
				.diskCacheStrategy(DiskCacheStrategy.NONE)
				.skipMemoryCache(true)
				.into(imageView);
	}
}

package com.bumptech.glide.supportapp.github._1022_crop_thumb;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView.ScaleType;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.supportapp.GlideImageFragment;
import com.bumptech.glide.supportapp.utils.*;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.*;

public class TestFragment extends GlideImageFragment {

	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		imageView.setScaleType(ScaleType.CENTER_CROP);
	}
	@Override protected void load(Context context) throws Exception {
		String url = "http://placehold.it/3000x2000/AAFF00/000000&text=IMAGE";
		int[] requestedSize = new int[] {540, 540};
		Glide
				.with(this)
				.load(url)
				.override(requestedSize[0], requestedSize[1])
				.transform(new DelayBitmapTransformation(3000), new CenterCrop(context))
				.skipMemoryCache(true)
				.diskCacheStrategy(NONE)
				.listener(new LoggingListener<String, GlideDrawable>())
				.thumbnail(Glide
						.with(this)
						.load(url)
						.override(512, 384)
						.transform(new DelayBitmapTransformation(1000), new CenterCrop(context))
						.skipMemoryCache(true)
						.diskCacheStrategy(NONE)
						.listener(new LoggingListener<String, GlideDrawable>())
				)
				.into(imageView)
		;
	}
}

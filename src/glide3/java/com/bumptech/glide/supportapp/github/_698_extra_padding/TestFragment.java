package com.bumptech.glide.supportapp.github._698_extra_padding;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION_CODES;
import android.util.TypedValue;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView.ScaleType;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.supportapp.*;
import com.bumptech.glide.supportapp.utils.LoggingListener;

@TargetApi(VERSION_CODES.M)
public class TestFragment extends GlideImageFragment {
	@Override protected void load(final Context context) {
		String url =
				"https://upload.wikimedia.org/wikipedia/commons/0/0b/8K,_4K,_2K,_UHD,_HD,_SD.png";
		imageView.setScaleType(ScaleType.FIT_XY);
		float fixed = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 400, getResources().getDisplayMetrics());
		imageView.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, (int)fixed));
		Glide
				.with(context)
				.load(url)
				.fitCenter()
				.placeholder(R.drawable.glide_placeholder)
				.listener(new LoggingListener<String, GlideDrawable>())
				.diskCacheStrategy(DiskCacheStrategy.ALL)
				.into(imageView);
	}
}

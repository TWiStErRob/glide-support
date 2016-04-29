package com.bumptech.glide.supportapp.stackoverflow._32235413_crossfade_placeholder;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView.ScaleType;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.DrawableCrossFadeFactory;
import com.bumptech.glide.supportapp.*;

public class TestFragment extends GlideImageFragment {
	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		imageView.setScaleType(ScaleType.CENTER);
	}

	@Override protected void load(Context context) throws Exception {
		Glide
				.with(context)
				.load(R.drawable.glide)
				.diskCacheStrategy(DiskCacheStrategy.NONE)
				.fitCenter()
				.placeholder(R.drawable.glide_placeholder)
				.animate(new PaddingAnimationFactory<>(new DrawableCrossFadeFactory<GlideDrawable>(2000)))
				.into(imageView)
		;
	}
}

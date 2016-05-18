package com.bumptech.glide.supportapp.github._1214_abstractmethoderror;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.*;

import com.bumptech.glide.*;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.supportapp.GlideImageFragment;

public class TestFragment extends GlideImageFragment {
	@Override protected void load(Context context) throws Exception {
		GlideUrl url = new GlideUrl("https://avatars1.githubusercontent.com/u/5476227?v=3&s=460");
		RequestManager requestManager = Glide.with(this);
		RequestBuilder<Drawable> mFullRequest = requestManager
				.asDrawable()
				.apply(RequestOptions
						.centerCropTransform(context)
						.placeholder(new ColorDrawable(Color.GRAY))
				);
		RequestBuilder<Drawable> mThumbnailRequest = requestManager
				.asDrawable()
				.apply(RequestOptions
						.centerCropTransform(context)
						.override(100, 100)
				);
		mFullRequest
				.load(url)
				.thumbnail(mThumbnailRequest.load(url))
				.into(imageView);
	}
}

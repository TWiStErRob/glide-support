package com.bumptech.glide.supportapp.github._921_different_headers;

import android.app.Activity;
import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.*;
import com.bumptech.glide.supportapp.GlideImageFragment;

public class TestFragment extends GlideImageFragment {
	private LazyHeaders.Builder authHeaders;
	private LazyHeaders.Builder otherHeaders;

	@Override public void onAttach(Activity activity) {
		super.onAttach(activity);
		authHeaders = AuthLoader.createHeaders(activity);
		otherHeaders = OtherLoader.createHeaders(activity);
	}
	@Override protected void load(Context context) throws Exception {
		String url = "http://blah";

		Glide.with(this).load(url).into(imageView);
		Glide.with(this).load(new AuthModel(url)).into(imageView);
		Glide.with(this).load(new OtherModel(url)).into(imageView);

		Glide.with(this).load(url).into(imageView);
		Glide.with(this).load(new GlideUrl(url, authHeaders.build())).into(imageView);
		Glide.with(this).load(new GlideUrl(url, otherHeaders.build())).into(imageView);
	}
}

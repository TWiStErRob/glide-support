package com.bumptech.glide.supportapp.github._876_wifi_only;

import android.content.Context;

import com.bumptech.glide.*;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.supportapp.GlideImageFragment;
import com.bumptech.glide.supportapp.utils.NetworkDisablingLoader;

public class TestFragment extends GlideImageFragment {
	@Override protected void load(Context context) throws Exception {
		boolean wifiOnly = false;
		String urlStr = "http://";
		wifiRequest(context, wifiOnly).load(urlStr).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
	}

	public static DrawableTypeRequest<String> wifiRequest(Context context, boolean isOnlyOnWiFi) {
		DrawableTypeRequest<String> request;
		if (isOnlyOnWiFi) {
			request = Glide.with(context).using(new NetworkDisablingLoader<String>()).from(String.class);
		} else {
			request = Glide.with(context).fromString();
		}
		return request;
	}
}

package com.bumptech.glide.supportapp.github._1257_cache_304;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;
import com.bumptech.glide.load.model.LazyHeaders;

public class ForceLoadGlideUrl extends GlideUrl {
	private static final Headers FORCE_ETAG_CHECK = new LazyHeaders.Builder()
			// I need to set the Cache-Control in order to force server side validation for the ETAG
			.addHeader("Cache-Control", "max-age=0")
			.build();

	public ForceLoadGlideUrl(String url) {
		super(url, FORCE_ETAG_CHECK);
	}
}

package com.bumptech.glide.supportapp.github._1032_restart_app_gif;

import java.io.InputStream;

import android.content.Context;
import android.net.Uri;

import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.stream.StreamModelLoader;

/**
 * A base ModelLoader for {@link android.net.Uri}s that handles assets {@link android.net.Uri}s directly.
 */
class AssetUriLoader implements StreamModelLoader<Uri> {
	private final Context context;

	public AssetUriLoader(Context context) {
		this.context = context;
	}

	@Override
	public final DataFetcher<InputStream> getResourceFetcher(Uri model, int width, int height) {
		AssetUriFetcher result = new AssetUriFetcher(context, model);
		return result;
	}
}

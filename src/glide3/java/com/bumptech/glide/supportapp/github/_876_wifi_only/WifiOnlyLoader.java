package com.bumptech.glide.supportapp.github._876_wifi_only;

import java.io.InputStream;

import android.content.*;

import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.*;
import com.bumptech.glide.load.model.stream.HttpUrlGlideUrlLoader;
import com.bumptech.glide.supportapp.App;
import com.bumptech.glide.supportapp.utils.NetworkDisablingFetcher;

public class WifiOnlyLoader implements ModelLoader<GlideUrl, InputStream> {
	private final ModelLoader<GlideUrl, InputStream> defaultLoader;
	public WifiOnlyLoader(ModelLoader<GlideUrl, InputStream> loader) {
		defaultLoader = loader;
	}
	@Override public DataFetcher<InputStream> getResourceFetcher(GlideUrl model, int width, int height) {
		SharedPreferences prefs = App.getPrefs();
		if (prefs.getBoolean("wifi_only", false)) {
			return new NetworkDisablingFetcher(model);
		} else {
			return defaultLoader.getResourceFetcher(model, width, height);
		}
	}
	public static class Factory implements ModelLoaderFactory<GlideUrl, InputStream> {
		@Override public ModelLoader<GlideUrl, InputStream> build(Context context, GenericLoaderFactory factories) {
			//ModelLoader<GlideUrl, InputStream> loader = factories.buildModelLoader(GlideUrl.class, InputStream.class);
			// the above could be used when you have a custom model, this version already replaced the default loader,
			// so it needs to be created explicitly. If you use OkHttp or Volley create that.
			return new WifiOnlyLoader(new HttpUrlGlideUrlLoader(new ModelCache<GlideUrl, GlideUrl>(500)));
		}
		@Override public void teardown() {
		}
	}
}


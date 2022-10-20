package com.bumptech.glide.supportapp.github._724_check_and_fallback;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.StringSignature;
import com.bumptech.glide.supportapp.GlideImageFragment;
import com.bumptech.glide.supportapp.utils.NetworkDisablingLoader;

public class TestFragment extends GlideImageFragment {
	@Override protected void load(final Context context) throws Exception {
		String profileUrl = "...";
		long lastProfileCache = context.getSharedPreferences("profile", 0)
		                               .getLong("lastCacheTime", System.currentTimeMillis());
		Glide // display a fresh version
		      .with(context)
		      .load(profileUrl)
		      .thumbnail(Glide // display a cached version
		                       .with(context)
		                       .using(new NetworkDisablingLoader<>()) // only if exists in disk cache
		                       .load(profileUrl)
		                       .signature(new StringSignature(String.valueOf(lastProfileCache)))
		                       .diskCacheStrategy(DiskCacheStrategy.SOURCE)
		      )
		      .diskCacheStrategy(DiskCacheStrategy.NONE) // downloaded right now
		      .into(imageView)
		;
	}

	// update the cache (for example on app startup)
	private void updateProfileCache(final Context context, String profileUrl) {
		final long lastProfileCache = System.currentTimeMillis();
		Glide
				.with(context)
				.load(profileUrl)
				.signature(new StringSignature(String.valueOf(lastProfileCache)))
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				.listener(new SaveLastProfileCacheTime(context, lastProfileCache))
				.preload()
		;
	}
	private static class SaveLastProfileCacheTime implements RequestListener<String, GlideDrawable> {
		private final Context context;
		private final long lastProfileCache;
		public SaveLastProfileCacheTime(Context context, long lastProfileCache) {
			this.context = context;
			this.lastProfileCache = lastProfileCache;
		}
		@Override public boolean onException(Exception e, String model, Target<GlideDrawable> target,
				boolean isFirstResource) {
			return false;
		}
		@Override public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target,
				boolean isFromMemoryCache, boolean isFirstResource) {
			context.getSharedPreferences("profile", 0).edit().putLong("lastCacheTime", lastProfileCache).apply();
			return false;
		}
	}
}

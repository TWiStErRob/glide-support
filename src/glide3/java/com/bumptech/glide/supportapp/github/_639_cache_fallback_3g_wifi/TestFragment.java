package com.bumptech.glide.supportapp.github._639_cache_fallback_3g_wifi;

import java.io.File;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.supportapp.GlideImageFragment;
import com.bumptech.glide.supportapp.utils.NetworkDisablingLoader;
import com.bumptech.glide.supportapp.utils.TextDrawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;

public class TestFragment extends GlideImageFragment {

	private final MenuProvider testMenuProvider = new MenuProvider() {
		@Override public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
			menu.add(0, 10, 0, "Load");
			menu.add(0, 11, 0, "Cache 3G").setIcon(android.R.drawable.presence_audio_online);
			menu.add(0, 12, 0, "Cache Wifi").setIcon(android.R.drawable.presence_video_online);
			menu.add(0, 13, 0, "On 3G");
			menu.add(0, 14, 0, "On Wifi");
		}

		private boolean net3g = false;
		private boolean netWifi = true;

		@Override public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
			String fast = "http://placehold.it/1920x1080?text=wifi";
			String slow = "http://placehold.it/640x480?text=3g";
			switch (menuItem.getItemId()) {
				case 11:
					cache(slow, "3g preloaded", "3g preload failed");
					return true;
				case 12:
					cache(fast, "wifi preloaded", "wifi preload failed");
					return true;
				case 13:
					net3g = true;
					netWifi = false;
					Toast.makeText(getActivity(), "On 3G now", Toast.LENGTH_SHORT).show();
					return true;
				case 14:
					net3g = false;
					netWifi = true;
					Toast.makeText(getActivity(), "On Wifi now", Toast.LENGTH_SHORT).show();
					return true;
				case 10:
					simulateLoading(slow, fast, net3g);
					return true;
				default:
					return false;
			}
		}
	};

	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		getActivity().addMenuProvider(testMenuProvider, getViewLifecycleOwner());
	}

	private void cache(String slow, String successMessage, String failMessage) {
		Glide
				.with(this)
				.load(slow)
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				.skipMemoryCache(true)
				.listener(new RequestListener<>() {
					@Override public boolean onException(Exception e, String model,
							Target<GlideDrawable> target,
							boolean isFirstResource) {
						Log.i("GLIDE", failMessage, e);
						return false;
					}
					@Override public boolean onResourceReady(GlideDrawable resource, String model,
							Target<GlideDrawable> target,
							boolean isFromMemoryCache, boolean isFirstResource) {
						Log.i("GLIDE", successMessage);
						return false;
					}
				})
				.preload()
		;
	}

	private void simulateLoading(String slowUrl, String fastUrl, boolean isNetworkSlow) {
		DrawableRequestBuilder<String> slowLoad;
		DrawableRequestBuilder<String> fastLoad;

		if (isNetworkSlow) {
			slowLoad = Glide.with(this).load(slowUrl);
			fastLoad = Glide.with(this).using(new NetworkDisablingLoader<String>()).load(fastUrl);
		} else {
			slowLoad = Glide.with(this).load(slowUrl);
			fastLoad = Glide.with(this).load(fastUrl);
		}

		slowLoad
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				.skipMemoryCache(true)
				.error(new TextDrawable("slow failed"))
				.listener(new SlowListener());
		fastLoad
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				.skipMemoryCache(true)
				.error(new TextDrawable("fast failed"))
				.listener(new FastListener());

		fastLoad.thumbnail(slowLoad).into(imageView);
	}

	@Override protected void load(final Context context) throws Exception {
	}

	boolean syncTry1(final String url) {
		boolean cached = false;
		try {
			final AtomicReference<File> file = new AtomicReference<>();
			final CountDownLatch latch = new CountDownLatch(1);
			new Thread() {
				@Override public void run() {
					File cached;
					try {
						cached = Glide
								.with(getActivity())
								.using(new NetworkDisablingLoader<String>())
								.load(url)
								.downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
								.get();
						file.set(cached);
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					} finally {
						latch.countDown();
					}
				}
			}.start();
			latch.await();
			Log.wtf("GLIDE", String.valueOf(file));
			cached = file.get() != null;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return cached;
	}

	boolean syncTry2(String url) {
		Log.i("GLIDE", "Checking isCached()");
		final AtomicBoolean cached = new AtomicBoolean();
		final CountDownLatch latch = new CountDownLatch(1);
		Glide
				.with(getActivity())
				.using(new NetworkDisablingLoader<String>())
				.load(url)
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				.skipMemoryCache(true)
				//.imageDecoder(new NullDecoder<InputStream, Bitmap>())
				.into(new SimpleTarget<GlideDrawable>() {
					@Override public void onLoadFailed(Exception e, Drawable errorDrawable) {
						Log.i("GLIDE", String.format(Locale.ROOT, "onLoadFailed(%s, %s)", e, errorDrawable), e);
						cached.set(false);
						latch.countDown();
					}
					@Override public void onResourceReady(
							GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
						Log.i("GLIDE", String.format(Locale.ROOT, "onResourceReady(%s, %s)", resource, glideAnimation));
						cached.set(true);
						latch.countDown();
					}
				});
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return cached.get();
	}

	private static class SlowListener implements RequestListener<String, GlideDrawable> {
		@Override public boolean onException(Exception e, String model,
				Target<GlideDrawable> target,
				boolean isFirstResource) {
			Log.i("GLIDE", "slow failed", e);
			return false;
		}
		@Override public boolean onResourceReady(GlideDrawable resource, String model,
				Target<GlideDrawable> target,
				boolean isFromMemoryCache, boolean isFirstResource) {
			Log.i("GLIDE", "slow loaded");
			return false;
		}
	}

	private static class FastListener implements RequestListener<String, GlideDrawable> {
		@Override public boolean onException(Exception e, String model,
				Target<GlideDrawable> target,
				boolean isFirstResource) {
			Log.i("GLIDE", "fast failed", e);
			return false;
		}
		@Override public boolean onResourceReady(GlideDrawable resource, String model,
				Target<GlideDrawable> target,
				boolean isFromMemoryCache, boolean isFirstResource) {
			Log.i("GLIDE", "fast loaded");
			return false;
		}
	}
}

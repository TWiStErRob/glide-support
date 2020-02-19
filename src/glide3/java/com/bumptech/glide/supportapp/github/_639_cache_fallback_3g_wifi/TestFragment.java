package com.bumptech.glide.supportapp.github._639_cache_fallback_3g_wifi;

import java.io.File;
import java.util.Locale;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.*;
import android.widget.Toast;

import com.bumptech.glide.*;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.*;
import com.bumptech.glide.supportapp.GlideImageFragment;
import com.bumptech.glide.supportapp.utils.*;

public class TestFragment extends GlideImageFragment {
	@Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		menu.add(0, 10, 0, "Load");
		menu.add(0, 11, 0, "Cache 3G").setIcon(android.R.drawable.presence_audio_online);
		menu.add(0, 12, 0, "Cache Wifi").setIcon(android.R.drawable.presence_video_online);
		menu.add(0, 13, 0, "On 3G");
		menu.add(0, 14, 0, "On Wifi");
	}
	private boolean net3g = false;
	private boolean netWifi = true;

	@Override public boolean onOptionsItemSelected(MenuItem item) {
		String fast = "http://placehold.it/1920x1080?text=wifi";
		String slow = "http://placehold.it/640x480?text=3g";
		switch (item.getItemId()) {
			case 11:
				Glide.with(this)
				     .load(slow)
				     .diskCacheStrategy(DiskCacheStrategy.SOURCE)
				     .skipMemoryCache(true)
				     .listener(new RequestListener<String, GlideDrawable>() {
					     @Override public boolean onException(Exception e, String model,
							     Target<GlideDrawable> target,
							     boolean isFirstResource) {
						     Log.i("GLIDE", "3g preload failed", e);
						     return false;
					     }
					     @Override public boolean onResourceReady(GlideDrawable resource, String model,
							     Target<GlideDrawable> target,
							     boolean isFromMemoryCache, boolean isFirstResource) {
						     Log.i("GLIDE", "3g preloaded");
						     return false;
					     }
				     })
				     .preload()
				;
				return true;
			case 12:
				Glide.with(this)
				     .load(fast)
				     .diskCacheStrategy(DiskCacheStrategy.SOURCE)
				     .skipMemoryCache(true)
				     .listener(new RequestListener<String, GlideDrawable>() {
					     @Override public boolean onException(Exception e, String model,
							     Target<GlideDrawable> target,
							     boolean isFirstResource) {
						     Log.i("GLIDE", "wifi preload failed", e);
						     return false;
					     }
					     @Override public boolean onResourceReady(GlideDrawable resource, String model,
							     Target<GlideDrawable> target,
							     boolean isFromMemoryCache, boolean isFirstResource) {
						     Log.i("GLIDE", "wifi preloaded");
						     return false;
					     }
				     })
				     .preload()
				;
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
				DrawableRequestBuilder<String> slowLoad;
				DrawableRequestBuilder<String> fastLoad;

				if (net3g) {
					slowLoad = Glide.with(this).load(slow);
					fastLoad = Glide.with(this).using(new NetworkDisablingLoader<String>()).load(fast);
				} else {
					slowLoad = Glide.with(this).load(slow);
					fastLoad = Glide.with(this).load(fast);
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
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
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
							GlideDrawable resource, GlideAnimation<?super GlideDrawable> glideAnimation) {
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


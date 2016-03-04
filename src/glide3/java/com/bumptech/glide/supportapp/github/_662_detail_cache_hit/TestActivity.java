package com.bumptech.glide.supportapp.github._662_detail_cache_hit;

import java.io.InputStream;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.*;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.data.*;
import com.bumptech.glide.load.model.*;
import com.bumptech.glide.load.model.stream.StreamModelLoader;
import com.bumptech.glide.supportapp.utils.NetworkDisablingFetcher;

public class TestActivity extends FragmentActivity implements ListFragment.Callback {
	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		FrameLayout layout = new FrameLayout(this);
		layout.setId(android.R.id.content);
		layout.setLayoutParams(
				new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		setContentView(layout);

		if (savedInstanceState == null) {
			clearCacheSyncAndHackNetwork();
			getSupportFragmentManager()
					.beginTransaction()
					.add(android.R.id.content, new ListFragment())
					.commit();
		}
	}

	@Override public void selected(int position, ListItem model) {
		Bundle args = new Bundle();
		args.putSerializable("model", model);
		Fragment fragment = new DetailFragment();
		fragment.setArguments(args);
		getSupportFragmentManager()
				.beginTransaction()
				.replace(android.R.id.content, fragment)
				.addToBackStack("details")
				.commit();
	}

	private void clearCacheSyncAndHackNetwork() {
		// TODO only for testing: clear all caches before anything is loaded to always have a clean slate
		Glide.get(TestActivity.this).clearMemory();
		final CountDownLatch latch = new CountDownLatch(1);
		new Thread() {
			@Override public void run() {
				Glide.get(TestActivity.this).clearDiskCache();
				latch.countDown();
			}
		}.start();
		try {
			latch.await(); // never do this in production
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// TODO only for debug: override default Url handler to fail sometimes (50%)
		Glide.get(this).register(GlideUrl.class, InputStream.class, new ModelLoaderFactory<GlideUrl, InputStream>() {
			Random random = new Random(0);
			@Override public ModelLoader<GlideUrl, InputStream> build(Context context, GenericLoaderFactory factories) {
				return new StreamModelLoader<GlideUrl>() {
					@Override public DataFetcher<InputStream> getResourceFetcher(GlideUrl url, int width, int height) {
						return random.nextBoolean()? new HttpUrlFetcher(url) : new NetworkDisablingFetcher(url);
					}
				};
			}
			@Override public void teardown() {
			}
		});
	}
}

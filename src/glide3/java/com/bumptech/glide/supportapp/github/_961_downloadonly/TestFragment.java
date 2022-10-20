package com.bumptech.glide.supportapp.github._961_downloadonly;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.supportapp.BaseFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TestFragment extends BaseFragment {
	private Downloader downloader;
	@Override public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		downloader = download();
	}

	@SuppressWarnings("deprecation") // Historical code.
	private @NonNull Downloader download() {
		Downloader downloader = new Downloader(Glide.with(getContext().getApplicationContext()));
		downloader.execute(
				"http://placehold.it/100?text=1",
				"http://placehold.it/100?text=2",
				"http://placehold.it/100?text=3",
				"http://placehold.it/100?text=4",
				"xttp://placehold.it/100?text=5", // will fail
				"http://placehold.it/100?text=6",
				"http://placehold.it/100?text=7",
				"http://placehold.it/100?text=8",
				"http://placehold.it/100?text=9"
		);
		return downloader;
	}

	@SuppressWarnings("deprecation") // Historical code.
	@Override public void onDestroy() {
		downloader.cancel(true);
		super.onDestroy();
	}
}

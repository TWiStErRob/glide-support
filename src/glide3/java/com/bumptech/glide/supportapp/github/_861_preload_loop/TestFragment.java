package com.bumptech.glide.supportapp.github._861_preload_loop;

import java.util.Arrays;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.bumptech.glide.Glide;
import com.bumptech.glide.supportapp.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TestFragment extends Fragment {
	private ImageView imageView;
	private LoadCycler<Item, ?> cycler;

	@Override public @Nullable View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		imageView = new ImageView(container.getContext());
		imageView.setLayoutParams(new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		imageView.setScaleType(ScaleType.CENTER_CROP);
		imageView.setId(R.id.image);
		imageView.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				cycler.startNext();
			}
		});
		return imageView;
	}

	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		imageView = (ImageView)view.findViewById(R.id.image);
		setupCycler();
	}

	@Override public void onStart() {
		super.onStart();
		loadDataAsync();
	}

	private void setupCycler() {
		cycler = new LoadCycler<>(Glide
				.with(this)
				.from(Item.class)
				.centerCrop()
				, imageView);
		//cycler.setAnimation(new DrawableCrossFadeFactory<>(1000));
	}

	private void loadDataAsync() {
		new AsyncTask<Void, Void, List<Item>>() {
			@Override protected List<Item> doInBackground(Void... params) {
				return Arrays.asList(
						new Item("http://placehold.it/200x200.gif?text=0"),
						new Item("http://placehold.it/200x200.gif?text=1"),
						new Item("http://placehold.it/200x200.gif?text=2"),
						new Item("http://placehold.it/200x200.gif?text=3"),
						new Item("http://placehold.it/200x200.gif?text=4"),
						new Item("http://placehold.it/200x200.gif?text=5")
				);
			}
			@Override protected void onPostExecute(List<Item> data) {
				cycler.setData(data);
				next();
			}
		}.execute();
	}

	private void next() {
		if (!isResumed()) {
			return;
		}
		cycler.startNext();
		//autoAdvance();
	}

	private void autoAdvance() {
		new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
			@Override public void run() {
				next();
			}
		}, 2000);
	}
}

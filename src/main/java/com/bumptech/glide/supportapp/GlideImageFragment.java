package com.bumptech.glide.supportapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

public abstract class GlideImageFragment extends GlideBaseImageFragment {
	protected ImageView imageView;
	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override public @Nullable View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.base_image, container, false);
	}

	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		imageView = (ImageView)view.findViewById(android.R.id.icon);
		imageView.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				load();
			}
		});
	}

	@Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		MenuItem clearImage = menu.add(0, 9, 0, "Clear image").setIcon(android.R.drawable.ic_menu_close_clear_cancel);
		MenuItemCompat.setShowAsAction(clearImage, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
	}

	@Override public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case 9:
				Log.i("GLIDE", "Clearing target " + imageView);
				clear(imageView);
				Log.i("GLIDE", "Clearing target " + imageView + " finished");
				Toast.makeText(getContext(), "Target cleared", Toast.LENGTH_SHORT).show();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public void load() {
		Log.i("GLIDE", "Loading");
		try {
			load(getContext());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.i("GLIDE", "Loaded");
	}

	@Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		load();
	}

	protected abstract void load(Context context) throws Exception;
}

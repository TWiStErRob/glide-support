package com.bumptech.glide.supportapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;

public abstract class GlideImageFragment extends GlideBaseImageFragment {
	protected ImageView imageView;

	private final MenuProvider baseMenuProvider = new MenuProvider() {

		@Override public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
			MenuItem clearImage = menu.add(0, 9, 0, "Clear image")
			                          .setIcon(android.R.drawable.ic_menu_close_clear_cancel);
			clearImage.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		}

		@Override public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
			switch (menuItem.getItemId()) {
				case 9:
					Log.i("GLIDE", "Clearing target " + imageView);
					clear(imageView);
					Log.i("GLIDE", "Clearing target " + imageView + " finished");
					Toast.makeText(getContext(), "Target cleared", Toast.LENGTH_SHORT).show();
					return true;
				default:
					return false;
			}
		}
	};

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.base_image, container, false);
	}

	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		requireActivity().addMenuProvider(baseMenuProvider, getViewLifecycleOwner());
		imageView = (ImageView)view.findViewById(android.R.id.icon);
		((View)view.getParent()).setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				load();
			}
		});
		load();
	}

	public void load() {
		Log.i("GLIDE", "Starting load");
		try {
			load(getContext());
		} catch (Exception e) {
			Log.e("GLIDE", "Failed to start load", e);
			Toast.makeText(getContext(), "Load: " + e, Toast.LENGTH_SHORT).show();
		}
		Log.i("GLIDE", "Load started");
	}

	protected abstract void load(Context context) throws Exception;
}

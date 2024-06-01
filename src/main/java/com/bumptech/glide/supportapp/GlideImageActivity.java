package com.bumptech.glide.supportapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;

public abstract class GlideImageActivity extends GlideBaseImageActivity {
	protected ImageView imageView;
	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addMenuProvider(new ImageMenuProvider());
		onCreateView();
		imageView = (ImageView)findViewById(android.R.id.icon);
		imageView.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				load();
			}
		});
		load();
	}
	protected void onCreateView() {
		setContentView(R.layout.base_image);
	}

	public void load() {
		Log.i("GLIDE", "Loading");
		try {
			load(this);
		} catch (Exception e) {
			Log.e("GLIDE", "Failed to start load", e);
			Toast.makeText(this, "Load: " + e, Toast.LENGTH_SHORT).show();
		}
		Log.i("GLIDE", "Loaded");
	}

	protected abstract void load(Context context) throws Exception;
	
	private class ImageMenuProvider implements MenuProvider {

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
					Toast.makeText(GlideImageActivity.this, "Target cleared", Toast.LENGTH_SHORT).show();
					return true;
				default:
					return false;
			}
		}
	}
}

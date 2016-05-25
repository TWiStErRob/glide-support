package com.bumptech.glide.supportapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

import static com.bumptech.glide.Glide.*;

public abstract class GlideImageActivity extends GlideSupportActivity {
	protected ImageView imageView;
	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
	@Override public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuItem clearImage = menu.add(0, 9, 0, "Clear image").setIcon(android.R.drawable.ic_menu_close_clear_cancel);
		MenuItemCompat.setShowAsAction(clearImage, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
		return true;
	}

	@Override public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case 9:
				Log.i("GLIDE", "Clearing target " + imageView);
				clear(imageView);
				Log.i("GLIDE", "Clearing target " + imageView + " finished");
				Toast.makeText(this, "Target cleared", Toast.LENGTH_SHORT).show();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public void load() {
		Log.i("GLIDE", "Loading");
		try {
			load(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.i("GLIDE", "Loaded");
	}

	protected abstract void load(Context context) throws Exception;
}

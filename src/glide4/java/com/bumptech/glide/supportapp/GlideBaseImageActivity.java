package com.bumptech.glide.supportapp;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public abstract class GlideBaseImageActivity extends GlideBaseActivity {
	protected void clear(ImageView imageView) {
		Glide.with(this).clear(imageView);
	}

	@Override public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		item(menu, 5, "Glide.arrayPool.clearMemory", "#8800ff", false);
		return true;
	}

	@Override public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case 5:
				Glide.get(this).getArrayPool().clearMemory();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}

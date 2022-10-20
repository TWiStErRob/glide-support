package com.bumptech.glide.supportapp;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.bumptech.glide.supportapp.utils.ClearCachesTask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.BlendModeColorFilterCompat;
import androidx.core.graphics.BlendModeCompat;

public abstract class GlideBaseActivity extends AppCompatActivity {
	@Override public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		item(menu, 1, "Glide.clear*", "#888888", false);
		item(menu, 2, "Glide.clearMemory", "#888800", true);
		item(menu, 3, "Glide.clearDiskCache", "#ff8800", true);
		item(menu, 4, "Glide.bitmapPool.clearMemory", "#0088ff", false);
		return true;
	}

	protected void item(Menu menu, int id, String title, String color, boolean always) {
		Drawable icon = ContextCompat.getDrawable(this, android.R.drawable.ic_menu_delete).mutate();
		icon.setColorFilter(BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
				Color.parseColor(color),
				BlendModeCompat.SCREEN
		));
		MenuItem item = menu.add(0, id, 0, title).setIcon(icon);
		if (always) {
			item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		} else {
			item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		}
	}
	
	@Override public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case 1:
				new ClearCachesTask(this, true, true).execute();
				return true;
			case 2:
				new ClearCachesTask(this, true, false).execute();
				return true;
			case 3:
				new ClearCachesTask(this, false, true).execute();
				return true;
			case 4:
				Glide.get(this).getBitmapPool().clearMemory();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}

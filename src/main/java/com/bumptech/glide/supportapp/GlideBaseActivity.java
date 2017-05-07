package com.bumptech.glide.supportapp;

import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.*;

import com.bumptech.glide.Glide;
import com.bumptech.glide.supportapp.utils.ClearCachesTask;

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
		icon.setColorFilter(Color.parseColor(color), Mode.SCREEN);
		MenuItem item = menu.add(0, id, 0, title).setIcon(icon);
		if (always) {
			MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
		} else {
			MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
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
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}

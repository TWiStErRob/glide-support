package com.bumptech.glide.supportapp;

import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.*;

import com.bumptech.glide.supportapp.utils.ClearCachesTask;

public class GlideSupportActivity extends AppCompatActivity {
	@Override public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		item(menu, 1, "Clear cache", "#888888");
		item(menu, 2, "Clear memory cache", "#888800");
		item(menu, 3, "Click disk cache", "#ff8800");
		return true;
	}

	private void item(Menu menu, int id, String title, String color) {
		Drawable icon = ContextCompat.getDrawable(this, android.R.drawable.ic_menu_delete).mutate();
		icon.setColorFilter(Color.parseColor(color), Mode.SCREEN);
		MenuItem item = menu.add(0, id, 0, title).setIcon(icon);
		MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
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
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}

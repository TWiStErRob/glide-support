package com.bumptech.glide.supportapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.bumptech.glide.supportapp.utils.ClearCachesTask;

import static com.bumptech.glide.supportapp.GlideBaseActivity.createMenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.BlendModeCompat;
import androidx.core.view.MenuProvider;

import static androidx.core.graphics.BlendModeColorFilterCompat.createBlendModeColorFilterCompat;

public abstract class GlideBaseActivity extends AppCompatActivity {

	@Override protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addMenuProvider(new BaseActivityMenuProvider(this));
	}
	
	@SuppressWarnings("StaticMethodOnlyUsedInOneClass") // Used in GlideBaseImageActivity@v4
	protected static void createMenuItem(
			@NonNull Context context, @NonNull Menu menu, int id, @NonNull CharSequence title, @NonNull String color, boolean always
	) {
		Drawable icon = ContextCompat.getDrawable(context, android.R.drawable.ic_menu_delete).mutate();
		icon.setColorFilter(createBlendModeColorFilterCompat(Color.parseColor(color), BlendModeCompat.SCREEN));
		MenuItem item = menu.add(0, id, 0, title).setIcon(icon);
		if (always) {
			item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		} else {
			item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		}
	}
}

class BaseActivityMenuProvider implements MenuProvider {

	private final @NonNull Context context;

	BaseActivityMenuProvider(@NonNull Context context) {
		this.context = context;
	}

	@Override public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
		createMenuItem(context, menu, 1, "Glide.clear*", "#888888", false);
		createMenuItem(context, menu, 2, "Glide.clearMemory", "#888800", true);
		createMenuItem(context, menu, 3, "Glide.clearDiskCache", "#ff8800", true);
		createMenuItem(context, menu, 4, "Glide.bitmapPool.clearMemory", "#0088ff", false);
	}

	@Override public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
		switch (menuItem.getItemId()) {
			case 1:
				new ClearCachesTask(context, true, true).execute();
				return true;
			case 2:
				new ClearCachesTask(context, true, false).execute();
				return true;
			case 3:
				new ClearCachesTask(context, false, true).execute();
				return true;
			case 4:
				Glide.get(context).getBitmapPool().clearMemory();
				return true;
			default:
				return false;
		}
	}
}

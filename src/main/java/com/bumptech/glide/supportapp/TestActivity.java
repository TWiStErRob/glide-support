package com.bumptech.glide.supportapp;

import android.content.pm.*;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.*;
import android.view.ViewGroup.*;
import android.widget.FrameLayout;

import com.bumptech.glide.supportapp.utils.ClearCachesTask;

public class TestActivity extends AppCompatActivity {
	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		FrameLayout root = new FrameLayout(this);
		root.setId(android.R.id.content);
		root.setLayoutParams(new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		setContentView(root);

		getSupportFragmentManager().addOnBackStackChangedListener(new OnBackStackChangedListener() {
			@Override public void onBackStackChanged() {
				Fragment fragment = null;
				FragmentManager fm = getSupportFragmentManager();
				if (fm.getBackStackEntryCount() > 0) {
					fragment = fm.findFragmentById(android.R.id.content);
				}
				if (fragment != null) {
					setTitle(fragment.getClass().getSimpleName());
				} else {
					setTitle("<no fragment>");
				}
			}
		});

		if (savedInstanceState == null) {
			getSupportFragmentManager()
					.beginTransaction()
					.add(android.R.id.content, Fragment.instantiate(this, getFragmentClass()))
					.addToBackStack("content")
					.commit()
			;
		}
	}
	private String getFragmentClass() {
		String fragmentClass = QuickFragment.class.getName();
		try {
			ActivityInfo ai = getPackageManager().getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
			if (ai.metaData != null) {
				String clazz = ai.metaData.getString("contentFragment");
				if (clazz != null) {
					fragmentClass = clazz;
				} else {
					Log.w("SYS", TestActivity.class.getName() + " doesn't have a contentFragment metadata.");
				}
			} else {
				Log.w("SYS", TestActivity.class.getName() + " doesn't have metadata.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.d("SYS", "Using test fragment: " + fragmentClass);
		return fragmentClass;
	}

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

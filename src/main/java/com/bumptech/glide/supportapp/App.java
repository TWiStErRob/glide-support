package com.bumptech.glide.supportapp;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.bumptech.glide.load.engine.cache.DiskCache;

import androidx.appcompat.app.AppCompatDelegate;

public class App extends android.app.Application {
	private static App instance;
	private DiskCache diskCache;
	public App() {
		instance = this;
	}

	public static SharedPreferences getPrefs() {
		return PreferenceManager.getDefaultSharedPreferences(instance);
	}
	public static App getInstance() {
		return instance;
	}
	public void setDiskCache(DiskCache diskCache) {
		this.diskCache = diskCache;
	}
	public DiskCache getDiskCache() {
		return diskCache;
	}

	@Override public void onCreate() {
		super.onCreate();
		AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
	}
}

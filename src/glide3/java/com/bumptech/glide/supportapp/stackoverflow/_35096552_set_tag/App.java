package com.bumptech.glide.supportapp.stackoverflow._35096552_set_tag;

import android.annotation.SuppressLint;
import android.app.Application;

import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.supportapp.R;

@SuppressLint("Registered") // TODO in manifest of your app: <application android:name="....App" ...>
public class App extends Application {
	@Override public void onCreate() {
		super.onCreate();
		ViewTarget.setTagId(R.id._35096552_tag);
	}
}

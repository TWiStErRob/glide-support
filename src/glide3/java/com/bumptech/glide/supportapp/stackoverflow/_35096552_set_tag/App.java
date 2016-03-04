package com.bumptech.glide.supportapp.stackoverflow._35096552_set_tag;

import android.app.Application;

import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.supportapp.R;

// TODO in manifest: <application android:name="....App" ...>
public class App extends Application {
	@Override public void onCreate() {
		super.onCreate();
		ViewTarget.setTagId(R.id._35096552_tag);
	}
}

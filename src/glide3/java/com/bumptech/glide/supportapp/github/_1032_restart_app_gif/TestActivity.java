package com.bumptech.glide.supportapp.github._1032_restart_app_gif;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.supportapp.utils.LoggingListener;

import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {
	@Override public void onCreate(Bundle savedInstanceState) {
		Log.i("ACTIVITY", "Created: " + savedInstanceState);
		super.onCreate(savedInstanceState);

		RelativeLayout rl = new RelativeLayout(this);
		setContentView(rl);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(600, 400);
		ImageView imageView = new ImageView(this);
		params.leftMargin = 100;
		params.topMargin = 100;
		rl.addView(imageView, params);

		Glide
				.with(getApplicationContext())
				.using(new AssetUriLoader(getApplicationContext()))
				.load(Uri.parse("file:///android_asset/glide_anim.gif"))
				.diskCacheStrategy(DiskCacheStrategy.ALL)
				.listener(new LoggingListener<Uri, GlideDrawable>())
				.into(imageView)
		;
	}
	@Override protected void onStart() {
		Log.i("ACTIVITY", "Started");
		super.onStart();
	}
	@Override protected void onStop() {
		Log.i("ACTIVITY", "Stopped");
		super.onStop();
	}
	@Override protected void onDestroy() {
		Log.i("ACTIVITY", "Destroyed");
		super.onDestroy();
	}
}

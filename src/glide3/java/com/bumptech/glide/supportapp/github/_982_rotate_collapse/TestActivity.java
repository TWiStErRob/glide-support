package com.bumptech.glide.supportapp.github._982_rotate_collapse;

import android.content.Context;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.supportapp.GlideImageActivity;
import com.bumptech.glide.supportapp.R;
import com.bumptech.glide.supportapp.utils.LoggingListener;

import androidx.appcompat.widget.Toolbar;

public class TestActivity extends GlideImageActivity {
	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
	}
	@Override protected void onCreateView() {
		setContentView(R.layout.github_982);
	}
	@Override protected void load(Context context) throws Exception {
		String url =
				"http://www.visitcentrodeportugal.com.pt/wp-content/uploads/2013/01/museu-francisco-tavares-por.jpg";
		Glide
				.with(context)
				.load(url)
				.centerCrop()
				.listener(new LoggingListener<String, GlideDrawable>())
				.into(imageView)
		;
	}
}

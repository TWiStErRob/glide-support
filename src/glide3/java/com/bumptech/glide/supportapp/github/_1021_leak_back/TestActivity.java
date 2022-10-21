package com.bumptech.glide.supportapp.github._1021_leak_back;

import java.util.Locale;

import android.os.Bundle;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.supportapp.utils.LoggingListener;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {
	private static final String URL_TEMPLATE = "http://placehold.it/300x200/FFFFFF/000000&text=%d";
	@Override protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ImageView imageView = new ImageView(this);
		imageView.setLayoutParams(new MarginLayoutParams(MATCH_PARENT, MATCH_PARENT));
		setContentView(imageView);
		String url = String.format(Locale.ROOT, URL_TEMPLATE, -1);
		Glide
				.with(this)
				.load(url)
				.listener(new LoggingListener<String, GlideDrawable>())
				.into(imageView);
	}
	// http://stackoverflow.com/a/10154357/253468
//	@Override public void finish() {
//		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//		imm.windowDismissed(getWindow().getDecorView().getWindowToken());
//		super.finish();
//	}
}

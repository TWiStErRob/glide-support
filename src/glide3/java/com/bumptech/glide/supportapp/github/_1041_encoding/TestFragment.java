package com.bumptech.glide.supportapp.github._1041_encoding;

import java.util.Arrays;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.supportapp.GlideRecyclerFragment;
import com.bumptech.glide.supportapp.utils.LoggingListener;

import androidx.annotation.Nullable;

public class TestFragment extends GlideRecyclerFragment {
	private static final String URL_BASE =
			"http://2.bp.blogspot.com/_wOtGwObrPyI/SgbEunh78vI/AAAAAAAAAx4/UAk7SuQkUu8/s320/";
	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		listView.setAdapter(new SimpleUrlAdapter(Glide.with(this), Arrays.asList(
				// original url, already url-encoded
				URL_BASE + "s%C3%BCt%C3%A9s+1146.jpg", 
				// parse url-encoded url and toString encodes it again
				Uri.parse(URL_BASE + "s%C3%BCt%C3%A9s+1146.jpg").toString(),
				// raw url, nothing is escaped "sütés"
				Uri.decode(URL_BASE + "s%C3%BCt%C3%A9s+1146.jpg"),
				// illegally decoded url, utf-8 is not parsed properly
				URL_BASE + "sĂĽtĂ©s+1146.jpg"
		)) {
			@Override protected void load(Context context, RequestManager glide, String url, ImageView imageView) {
				glide.load(url).listener(new LoggingListener<String, GlideDrawable>()).into(imageView);
			}
		});
	}
}

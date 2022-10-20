package com.bumptech.glide.supportapp.github._978_preload_list;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.supportapp.utils.LoggingListener;

import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

public class TestFragment extends ListFragment {
	private static final String URL_TEMPLATE = "http://placehold.it/300x200/%06x/000000&text=%d";
	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		List<String> images = new ArrayList<>();
		for (int i = 1; i < 100; ++i) {
			images.add(String.format(Locale.ROOT, URL_TEMPLATE, i * 0x5f3759df % 0x1000000, i));
		}
		DrawableRequestBuilder<String> request = Glide
				.with(this)
				.fromString()
				.fitCenter() // must be explicit, otherwise there's a conflict between into(ImageView) and into(Target)
				.listener(new LoggingListener<String, GlideDrawable>());

		PreloadingAdapter adapter = new PreloadingAdapter(request, images);
		setListAdapter(adapter);
		getListView().setOnScrollListener(adapter.preload(3));
	}
}

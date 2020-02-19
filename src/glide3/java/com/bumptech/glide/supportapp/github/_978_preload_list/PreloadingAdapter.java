package com.bumptech.glide.supportapp.github._978_preload_list;

import java.util.*;

import android.view.*;
import android.view.ViewGroup.LayoutParams;
import android.widget.*;
import android.widget.AbsListView.OnScrollListener;

import com.bumptech.glide.*;
import com.bumptech.glide.ListPreloader.PreloadModelProvider;
import com.bumptech.glide.util.ViewPreloadSizeProvider;

class PreloadingAdapter extends BaseAdapter implements PreloadModelProvider<String> {
	private final GenericRequestBuilder<String, ?, ?, ?> request;
	private final ViewPreloadSizeProvider<String> sizeProvider = new ViewPreloadSizeProvider<>();
	private final List<String> images;

	public PreloadingAdapter(GenericRequestBuilder<String, ?, ?, ?> request, List<String> images) {
		this.request = request;
		this.images = images;
	}

	/// adapter methods
	@Override public int getCount() {
		return images.size();
	}
	@Override public String getItem(int position) {
		return images.get(position);
	}
	@Override public long getItemId(int position) {
		return images.get(position).hashCode();
	}
	@Override public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = (ImageView)convertView;
		if (imageView == null) {
			imageView = new ImageView(parent.getContext());
			imageView.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, 400));
			sizeProvider.setView(imageView); // safe to call multiple times
		}

		request
				.load(getItem(position))
				.into(imageView);

		return imageView;
	}

	/// preload methods
	@Override public List<String> getPreloadItems(int position) {
		return Collections.singletonList(getItem(position));
	}
	@Override public GenericRequestBuilder<?, ?, ?, ?> getPreloadRequestBuilder(String item) {
		return request.load(item);
	}
	public OnScrollListener preload(int maxPreload) {
		return new ListPreloader<>(this, sizeProvider, maxPreload);
	}
}

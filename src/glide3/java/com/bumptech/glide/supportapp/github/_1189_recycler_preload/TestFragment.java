package com.bumptech.glide.supportapp.github._1189_recycler_preload;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.ListPreloader.PreloadModelProvider;
import com.bumptech.glide.ListPreloader.PreloadSizeProvider;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.supportapp.GlideRecyclerFragment;
import com.bumptech.glide.supportapp.R;
import com.bumptech.glide.supportapp.utils.LoggingListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class TestFragment extends GlideRecyclerFragment {
	private static final int LIST_LENGTH = 1000;

	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		PreloadingAdapter adapter = new PreloadingAdapter(Glide.with(this), generateImages());
		listView.setAdapter(adapter);
		listView.addOnScrollListener(new RecyclerViewPreloader<>(adapter, adapter, 5));
	}

	private @NonNull List<String> generateImages() {
		List<String> urls = new ArrayList<>(LIST_LENGTH);
		for (int i = 1; i < LIST_LENGTH; ++i) {
			urls.add("http://placehold.it/300x700/?text=" + i);
		}
		return urls;
	}

	private static class PreloadingAdapter extends RecyclerView.Adapter<SimpleViewHolder>
			implements PreloadModelProvider<String>, PreloadSizeProvider<String> {
		private final List<String> urls;
		private final BitmapRequestBuilder<String, Bitmap> builder;
		private int[] stolenSize;

		public PreloadingAdapter(RequestManager glide, List<String> urls) {
			this.urls = urls;
			this.builder = glide
					.fromString()
					.asBitmap()
					.centerCrop()
					.listener(new LoggingListener<String, Bitmap>())
			;
		}

		// ------------------------ RecyclerView.Adapter -----------------------
		@Override public int getItemCount() {
			return urls.size();
		}

		public String getItem(int position) {
			return urls.get(position);
		}

		@Override public @NonNull SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			View view = inflater.inflate(R.layout.base_item, parent, false);

			DisplayMetrics metrics = parent.getContext().getResources().getDisplayMetrics();
			view.findViewById(android.R.id.icon).getLayoutParams().height = (int)
					TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, metrics);
			return new SimpleViewHolder(view);
		}

		@Override public void onBindViewHolder(SimpleViewHolder holder, int position) {
			String url = getItem(position);
			holder.titleText.setText(url);
			Target<?> target = builder.load(url).into(holder.imageView);
			if (stolenSize == null) {
				// assuming uniform sizing among items (fixed size or match works, wrap doesn't)
				target.getSize(new SizeReadyCallback() {
					@Override public void onSizeReady(int width, int height) {
						if (0 < width && 0 < height) {
							stolenSize = new int[] {width, height};
						}
					}
				});
			}
		}

		// ------------------------ PreloadModelProvider -----------------------
		@Override public List<String> getPreloadItems(int position) {
			return Collections.singletonList(getItem(position));
		}

		@Override public GenericRequestBuilder<?, ?, ?, ?> getPreloadRequestBuilder(String item) {
			return builder.load(item);
		}

		// ------------------------ PreloadSizeProvider -----------------------
		@Override public int[] getPreloadSize(String item, int adapterPosition, int perItemPosition) {
			return stolenSize;
		}
	}
}

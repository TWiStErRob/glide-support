package com.bumptech.glide.supportapp.github._1189_recycler_preload;

import java.util.*;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.*;
import android.support.v7.widget.RecyclerView;
import android.util.*;
import android.view.*;

import com.bumptech.glide.*;
import com.bumptech.glide.ListPreloader.*;
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import com.bumptech.glide.request.target.*;
import com.bumptech.glide.supportapp.*;
import com.bumptech.glide.supportapp.utils.LoggingListener;

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

		@Override public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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

		@Override public GenericRequestBuilder getPreloadRequestBuilder(String item) {
			return builder.load(item);
		}

		// ------------------------ PreloadSizeProvider -----------------------
		@Override public int[] getPreloadSize(String item, int adapterPosition, int perItemPosition) {
			return stolenSize;
		}
	}
}

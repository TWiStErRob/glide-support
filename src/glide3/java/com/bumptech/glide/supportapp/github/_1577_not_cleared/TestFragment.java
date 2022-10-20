package com.bumptech.glide.supportapp.github._1577_not_cleared;

import java.util.Locale;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.supportapp.GlideRecyclerFragment;
import com.bumptech.glide.supportapp.R;
import com.bumptech.glide.supportapp.utils.LoggingListener;
import com.bumptech.glide.supportapp.utils.LoggingTarget;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class TestFragment extends GlideRecyclerFragment {
	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		listView.setAdapter(new MyAdapter(Glide.with(getContext()), generate(1000)));
	}
	private String[] generate(int n) {
		String[] urls = new String[n];
		for (int i = 0; i < urls.length; i++) {
			urls[i] = String.format(Locale.ROOT, "http://placehold.it/300x200/%06x/000000&text=%d",
					(int)(Math.random() * 0xffffff), i);
		}
		return urls;
	}

	private static class MyAdapter extends RecyclerView.Adapter<MyHolder> {
		private final RequestManager glide;
		private final String[] images;
		public MyAdapter(RequestManager glide, String... urls) {
			this.glide = glide;
			this.images = urls;
		}
		@Override public int getItemCount() {
			return images.length;
		}
		public String getItem(int position) {
			return images[position];
		}

		@Override public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			View view = inflater.inflate(R.layout.github_1577_item, parent, false);
			return new MyHolder(view);
		}

		@Override public void onBindViewHolder(MyHolder holder, int position) {
			glide
					.load(getItem(position))
					.dontAnimate()
					.dontTransform()
					.diskCacheStrategy(DiskCacheStrategy.NONE) // FIXME Just for debug.
					.skipMemoryCache(true) // FIXME Just for debug.
					.placeholder(R.drawable.glide_placeholder)
					.listener(new LoggingListener<String, GlideDrawable>())
					.into(new LoggingTarget<>(new GlideDrawableImageViewTarget(holder.image) {
						@Override public void onLoadStarted(Drawable placeholder) {
							//setImageScaleType(ScaleType.CENTER_INSIDE);
							super.onLoadStarted(placeholder);
						}
					}).addToString());
		}
	}

	static class MyHolder extends RecyclerView.ViewHolder {
		ImageView image;
		public MyHolder(View itemView) {
			super(itemView);
			image = (ImageView)itemView.findViewById(R.id.github_1577_image);
		}
	}
}

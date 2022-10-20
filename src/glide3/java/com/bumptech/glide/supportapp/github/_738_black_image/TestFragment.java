package com.bumptech.glide.supportapp.github._738_black_image;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.resource.bitmap.StreamBitmapDecoder;
import com.bumptech.glide.supportapp.GlideListFragment;
import com.bumptech.glide.supportapp.R;

import androidx.annotation.Nullable;

public class TestFragment extends GlideListFragment {
	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		listView.setAdapter(new BaseAdapter() {
			String[] images = {
					"http://placehold.it/400x300/fe6f5e/000000&text=1",
					"http://placehold.it/400x300/fe6f5e/000000&text=2",
					"http://placehold.it/400x300/fe6f5e/000000&text=3",
					"http://placehold.it/400x300/fe6f5e/000000&text=4",
					"http://placehold.it/400x300/fe6f5e/000000&text=5",
					"http://placehold.it/400x300/fe6f5e/000000&text=6",
					"http://placehold.it/400x300/fe6f5e/000000&text=7",
					"http://placehold.it/400x300/fe6f5e/000000&text=8",
					"http://placehold.it/400x300/fe6f5e/000000&text=9"
			};
			@Override public int getCount() {
				return images.length;
			}
			@Override public String getItem(int position) {
				return images[position];
			}
			@Override public long getItemId(int position) {
				return images[position].hashCode();
			}
			@Override public View getView(int position, View convertView, ViewGroup parent) {
				ImageView imageView = recycle(convertView, parent);
				Glide
						.with(imageView.getContext())
						.load(getItem(position))
						.asBitmap()
						.imageDecoder(new StreamBitmapDecoder(imageView.getContext(), DecodeFormat.PREFER_ARGB_8888))
						.override(400, 300)
						.fitCenter()
						.placeholder(R.drawable.glide_placeholder)
						.into(imageView)
				;

				return imageView;
			}
		});
	}
}

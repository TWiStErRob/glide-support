package com.bumptech.glide.supportapp.random.__download;

import java.io.File;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.supportapp.GlideListFragment;
import com.bumptech.glide.supportapp.utils.Downloader;

import androidx.annotation.Nullable;

public class TestFragment extends GlideListFragment {
	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		listView.setAdapter(new BaseAdapter() {
			String[] images = {
					"http://placehold.it/300x200/fe6f5e/000000&text=1",
					"http://placehold.it/300x200/fe6f5e/000000&text=2",
					"http://placehold.it/300x200/fe6f5e/000000&text=3",
					"http://placehold.it/300x200/fe6f5e/000000&text=4",
					"http://placehold.it/300x200/fe6f5e/000000&text=5",
					"http://placehold.it/300x200/fe6f5e/000000&text=6",
					"http://placehold.it/300x200/fe6f5e/000000&text=7",
					"http://placehold.it/300x200/fe6f5e/000000&text=8",
					"http://placehold.it/300x200/fe6f5e/000000&text=9"
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
						.diskCacheStrategy(DiskCacheStrategy.ALL)
						.into(imageView)
				;

				return imageView;
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String url = (String)parent.getAdapter().getItem(position);
				FutureTarget<File> download = Glide
						.with(view.getContext())
						.load(url)
						.downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
				execute(download);
			}

			@SuppressWarnings({"unchecked", "deprecation"}) // Historical code.
			private void execute(FutureTarget<File> download) {
				new Downloader("downloaded.jpg").execute(download);
			}
		});
	}
}

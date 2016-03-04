package com.bumptech.glide.supportapp.random;

import java.io.File;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.supportapp.GlideListFragment;
import com.bumptech.glide.supportapp.utils.Downloader;

public class Test_GlideDownloadFromList extends GlideListFragment {
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
			@SuppressWarnings("unchecked")
			@Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String url = (String)parent.getAdapter().getItem(position);
				FutureTarget<File> download = Glide
						.with(view.getContext())
						.load(url)
						.downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
				new Downloader("downloaded.jpg").execute(download);
			}
		});
	}
}

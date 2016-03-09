package com.bumptech.glide.supportapp;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.*;
import android.view.ViewGroup.LayoutParams;
import android.widget.*;
import android.widget.ImageView.ScaleType;

import com.bumptech.glide.RequestManager;

public class GlideListFragment extends BaseFragment {
	protected ListView listView;

	@Override
	public @Nullable View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.base_list, container, false);
	}

	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		listView = (ListView)view.findViewById(android.R.id.list);
	}

	protected static ImageView recycle(View convertView, ViewGroup parent) {
		if (convertView == null) {
			ImageView view = new ImageView(parent.getContext());
			view.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.WRAP_CONTENT, 400));
			view.setAdjustViewBounds(true);
			view.setScaleType(ScaleType.FIT_CENTER);
			convertView = view;
		}
		return (ImageView)convertView;
	}

	public static class SimpleViewHolder {
		public final View itemView;
		public final TextView titleText;
		public final ImageView imageView;

		public SimpleViewHolder(View itemView) {
			this.itemView = itemView;
			this.titleText = (TextView)itemView.findViewById(android.R.id.title);
			this.imageView = (ImageView)itemView.findViewById(android.R.id.icon);
		}
	}

	public static abstract class SimpleUrlAdapter extends BaseAdapter {
		private final RequestManager glide;
		private final List<String> urls;

		public SimpleUrlAdapter(RequestManager glide, List<String> urls) {
			this.glide = glide;
			this.urls = urls;
		}

		@Override public int getCount() {
			return urls.size();
		}

		@Override public String getItem(int position) {
			return urls.get(position);
		}

		@Override public long getItemId(int position) {
			return urls.get(position).hashCode();
		}

		@Override public boolean hasStableIds() {
			return true;
		}

		@Override public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(parent.getContext());
				convertView = inflater.inflate(R.layout.base_item, parent, false);
				convertView.setTag(new SimpleViewHolder(convertView));
			}
			SimpleViewHolder holder = (SimpleViewHolder)convertView.getTag();
			onBindViewHolder(holder, position);
			return convertView;
		}

		public void onBindViewHolder(SimpleViewHolder holder, int position) {
			String url = urls.get(position);
			holder.titleText.setText(url);
			try {
				load(holder.itemView.getContext(), glide, url, holder.imageView);
			} catch (Exception ex) {
				Log.w("GLIDE", "Cannot bind url at position " + position + ": " + url, ex);
			}
		}

		protected abstract void load(Context context, RequestManager glide, String url, ImageView imageView)
				throws Exception;
	}
}

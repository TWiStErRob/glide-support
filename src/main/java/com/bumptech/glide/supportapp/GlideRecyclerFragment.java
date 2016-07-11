package com.bumptech.glide.supportapp;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.*;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

import com.bumptech.glide.RequestManager;

public class GlideRecyclerFragment extends BaseFragment {
	protected RecyclerView listView;

	@Override
	public @Nullable View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.base_recycler, container, false);
	}

	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		listView = (RecyclerView)view.findViewById(android.R.id.list);
		listView.setLayoutManager(new LinearLayoutManager(getContext()));
	}

	public static class SimpleViewHolder extends RecyclerView.ViewHolder {
		public final TextView titleText;
		public final ImageView imageView;

		public SimpleViewHolder(View itemView) {
			super(itemView);
			this.titleText = (TextView)itemView.findViewById(android.R.id.title);
			this.imageView = (ImageView)itemView.findViewById(android.R.id.icon);
		}
	}

	public static abstract class SimpleUrlAdapter extends RecyclerView.Adapter<SimpleViewHolder> {
		private final RequestManager glide;
		private final List<String> urls;

		public SimpleUrlAdapter(RequestManager glide, List<String> urls) {
			this.glide = glide;
			this.urls = urls;
		}

		@Override public int getItemCount() {
			return urls.size();
		}

		public String getItem(int position) {
			return urls.get(position);
		}

		@Override public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			View view = inflater.inflate(R.layout.base_item, parent, false);
			final SimpleViewHolder holder = new SimpleViewHolder(view);
			holder.itemView.setOnClickListener(new OnClickListener() {
				@Override public void onClick(View v) {
					onBindViewHolder(holder, holder.getAdapterPosition());
				}
			});
			return holder;
		}

		@Override public void onBindViewHolder(SimpleViewHolder holder, int position) {
			String url = getItem(position);
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

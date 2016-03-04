package com.bumptech.glide.supportapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.*;
import android.view.ViewGroup.*;
import android.widget.*;
import android.widget.ImageView.ScaleType;

public class GlideListFragment extends BaseFragment {
	protected ListView listView;
	@Override public @Nullable View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ListView view = new ListView(container.getContext());
		view.setId(android.R.id.list);
		view.setLayoutParams(new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		return view;
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
}

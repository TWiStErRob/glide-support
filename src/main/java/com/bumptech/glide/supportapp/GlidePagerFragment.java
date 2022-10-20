package com.bumptech.glide.supportapp;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AbsListView;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.PagerTitleStrip;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.LayoutParams;

public class GlidePagerFragment extends BaseFragment {
	protected ViewPager list;

	@Override public @Nullable View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewPager view = new ViewPager(getActivity()); // container.getContext() results in white on white text
		view.setId(android.R.id.list);
		view.setLayoutParams(new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		PagerTitleStrip
				title = new PagerTabStrip(view.getContext());
		ViewPager.LayoutParams params = new ViewPager.LayoutParams();
		params.width = LayoutParams.MATCH_PARENT;
		params.height = LayoutParams.WRAP_CONTENT;
		params.gravity = Gravity.TOP;
		title.setLayoutParams(params);

		view.addView(title);
		return view;
	}

	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		list = (ViewPager)view.findViewById(android.R.id.list);
	}

	protected static ImageView createView(ViewGroup parent) {
		ImageView view = new ImageView(parent.getContext());
		view.setId(R.id.image);
		view.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		return view;
	}
}

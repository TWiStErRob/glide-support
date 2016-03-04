package com.bumptech.glide.supportapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.*;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.*;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.*;

public class GlidePagerFragment extends BaseFragment {
	protected ViewPager list;

	@Override public @Nullable View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewPager view = new ViewPager(getActivity()); // container.getContext() results in white on white text
		view.setId(android.R.id.list);
		view.setLayoutParams(new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		PagerTitleStrip title = new PagerTabStrip(view.getContext());
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

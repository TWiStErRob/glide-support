package com.bumptech.glide.supportapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.*;
import android.view.*;
import android.view.ViewGroup.*;

public class GlideRecyclerFragment extends BaseFragment {
	protected RecyclerView listView;

	@Override public @Nullable View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		RecyclerView view = new RecyclerView(container.getContext());
		view.setId(android.R.id.list);
		view.setLayoutParams(new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		view.setLayoutManager(new LinearLayoutManager(container.getContext()));
		return view;
	}

	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		listView = (RecyclerView)view.findViewById(android.R.id.list);
	}
}

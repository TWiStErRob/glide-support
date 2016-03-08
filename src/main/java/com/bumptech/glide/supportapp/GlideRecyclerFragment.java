package com.bumptech.glide.supportapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.*;
import android.view.*;

public class GlideRecyclerFragment extends BaseFragment {
	protected RecyclerView listView;

	@Override
	public @Nullable View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.base_recycler, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		listView = (RecyclerView)view.findViewById(android.R.id.list);
		listView.setLayoutManager(new LinearLayoutManager(getContext()));
	}
}

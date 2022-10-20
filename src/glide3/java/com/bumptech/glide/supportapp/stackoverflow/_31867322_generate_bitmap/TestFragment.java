package com.bumptech.glide.supportapp.stackoverflow._31867322_generate_bitmap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TestFragment extends Fragment {
	@Override public @Nullable View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		RecyclerView view = new RecyclerView(container.getContext());
		view.setLayoutParams(new MarginLayoutParams(MATCH_PARENT, MATCH_PARENT));
		view.setLayoutManager(new GridLayoutManager(container.getContext(), 2 /*columns*/));
		view.setAdapter(new GeneratingAdapter(view.getContext()));
		return view;
	}
}

// Here are the imports in case you need it;
// didn't want to put it in the beginning to keep the relevant code first.

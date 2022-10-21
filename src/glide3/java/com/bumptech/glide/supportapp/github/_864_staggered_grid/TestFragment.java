package com.bumptech.glide.supportapp.github._864_staggered_grid;

import org.json.JSONArray;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.supportapp.GlideRecyclerFragment;
import com.bumptech.glide.supportapp.utils.SpacingItemDecoration;

import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class TestFragment extends GlideRecyclerFragment {
	private RequestManager glide;

	@Override public void onAttach(Context context) {
		super.onAttach(context);
		this.glide = Glide.with(this);
	}

	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		float spacing = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
		listView.addItemDecoration(new SpacingItemDecoration((int)spacing));
		listView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
		listView.addOnScrollListener(new PauseOnFling(glide));
	}

	@Override public void onStart() {
		super.onStart();
		LoaderManager.getInstance(this).initLoader(0, null, new LoaderManager.LoaderCallbacks<JSONArray>() {
			@Override public Loader<JSONArray> onCreateLoader(int id, Bundle args) {
				return new PicasaFeedLoader(getContext());
			}
			@Override public void onLoadFinished(Loader<JSONArray> loader, JSONArray data) {
				listView.setAdapter(new FeedAdapter(glide, data));
			}
			@Override public void onLoaderReset(Loader<JSONArray> loader) {
				listView.setAdapter(null);
			}
		});
	}
}

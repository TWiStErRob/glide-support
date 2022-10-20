package com.bumptech.glide.supportapp.github._864_staggered_grid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.supportapp.R;

import androidx.recyclerview.widget.RecyclerView;

class FeedAdapter extends RecyclerView.Adapter<FeedEntryViewHolder> {
	private final RequestManager glide;
	private final JSONArray data;

	public FeedAdapter(RequestManager glide, JSONArray data) {
		super.setHasStableIds(true);
		this.glide = glide;
		this.data = data;
	}

	@Override public int getItemCount() {
		return data.length();
	}

	@Override public long getItemId(int position) {
		try {
			return data.getJSONObject(position).getJSONObject("id").getString("$t").hashCode();
		} catch (JSONException e) {
			return RecyclerView.NO_ID;
		}
	}

	@Override public FeedEntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.github_864_item, parent, false);
		return new FeedEntryViewHolder(view, glide);
	}

	@Override public void onBindViewHolder(FeedEntryViewHolder holder, int position) {
		try {
			JSONObject entry = data.getJSONObject(position);
			holder.bind(entry);
		} catch (JSONException ex) {
			Log.e("FeedAdapter", "Cannot bind object at position " + position, ex);
		}
	}
}

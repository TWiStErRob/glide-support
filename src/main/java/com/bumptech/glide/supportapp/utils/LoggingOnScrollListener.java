package com.bumptech.glide.supportapp.utils;

import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

public class LoggingOnScrollListener extends RecyclerView.OnScrollListener {
	@Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
		String name;
		switch (newState) {
			case RecyclerView.SCROLL_STATE_DRAGGING:
				name = "SCROLL_STATE_DRAGGING";
				break;
			case RecyclerView.SCROLL_STATE_IDLE:
				name = "SCROLL_STATE_IDLE";
				break;
			case RecyclerView.SCROLL_STATE_SETTLING:
				name = "SCROLL_STATE_SETTLING";
				break;
			default:
				name = "" + newState;
		}
		Log.wtf("SCROLL", name);
	}
	@Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
		Log.wtf("SCROLL", "dx=" + dx + ", dy=" + dy);
	}
}

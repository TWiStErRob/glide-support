package com.bumptech.glide.supportapp.github._864_staggered_grid;

import com.bumptech.glide.RequestManager;

import androidx.recyclerview.widget.RecyclerView;

import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;

class PauseOnFling extends RecyclerView.OnScrollListener {
	private static final int FLING_JUMP_LOW_THRESHOLD = 80;
	private static final int FLING_JUMP_HIGH_THRESHOLD = 120;

	private final RequestManager glide;
	private boolean dragging = false;

	public PauseOnFling(RequestManager glide) {
		this.glide = glide;
	}

	@Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
		dragging = newState == SCROLL_STATE_DRAGGING;
		if (glide.isPaused()) {
			if (newState == SCROLL_STATE_DRAGGING || newState == SCROLL_STATE_IDLE) {
				// user is touchy or the scroll finished, show images
				glide.resumeRequests();
			} // settling means the user let the screen go, but it can still be flinging
		}
	}

	@Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
		if (!dragging) {
			// TODO can be made better by a rolling average of last N calls to smooth out patterns like a,b,a
			int currentSpeed = Math.abs(dy);
			boolean paused = glide.isPaused();
			if (paused && currentSpeed < FLING_JUMP_LOW_THRESHOLD) {
				glide.resumeRequests();
			} else if (!paused && FLING_JUMP_HIGH_THRESHOLD < currentSpeed) {
				glide.pauseRequests();
			}
		}
	}
}

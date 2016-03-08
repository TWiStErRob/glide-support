package com.bumptech.glide.supportapp.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/** @see http://stackoverflow.com/a/32190325/253468 */
public class SpacingItemDecoration extends RecyclerView.ItemDecoration {
	private final int halfSpace;

	public SpacingItemDecoration(int space) {
		this.halfSpace = space / 2;
	}

	@Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		if (parent.getPaddingLeft() != halfSpace) {
			parent.setPadding(halfSpace, halfSpace, halfSpace, halfSpace);
			parent.setClipToPadding(false);
		}

		outRect.top = halfSpace;
		outRect.bottom = halfSpace;
		outRect.left = halfSpace;
		outRect.right = halfSpace;
	}
}

package com.bumptech.glide.supportapp.github._1530_weird_scrolling;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.supportapp.GlideBaseImageFragment;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Repro: Scroll around to fill the disk cache and then fling up and down.
 * Some items will stick to top/bottom and/or there will be some flickering.
 */
public class TestFragment extends GlideBaseImageFragment {
	private RecyclerView list;
	@Override public @Nullable View onCreateView(
			LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		list = new RecyclerView(getContext());
		list.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		list.setLayoutManager(new GridLayoutManager(null, 3));
		return list;
	}

	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		list.setAdapter(new Adapter(getContext()));
	}

	static class Adapter extends RecyclerView.Adapter<Adapter.ImageHolder> {
		private final Context context;
		private final List<Integer> colors = new ArrayList<>();
		private final Random rnd = new Random();

		Adapter(Context context) {
			this.context = context;
			for (int i = 0; i < 150; i++) {
				colors.add(Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
			}
		}

		@Override public int getItemViewType(int position) {
			return 1;
		}
		@Override public int getItemCount() {
			return colors.size();
		}

		@Override public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			ImageView image = new ImageView(context);
			int fixed = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120,
					context.getResources().getDisplayMetrics());
			image.setLayoutParams(new GridLayoutManager.LayoutParams(LayoutParams.MATCH_PARENT, fixed));
			return new ImageHolder(image);
		}

		@Override public void onBindViewHolder(final ImageHolder holder, int position) {
			int color = colors.get(position);
			// for easier identification in debug (breakpoint)
			holder.image.setContentDescription(position + ": " + color);

			String url = "https://dummyimage.com/250x250/" + String.valueOf(color) + "/fff.png&text=" + (position + 1);
			Glide
					.with(context)
					.load(url)
					.skipMemoryCache(true) // makes the bug happen more often
					//.dontAnimate() // fixes the bug, but disables fade in
					.into(holder.image);

			// non-Glide repro:
//			holder.image.setImageDrawable(new ColorDrawable(color));
//			holder.image.clearAnimation();
//			AlphaAnimation animation = new AlphaAnimation(0f, 1f);
//			animation.setDuration(300);
//			holder.image.startAnimation(animation);
		}

		@Override public void onViewDetachedFromWindow(ImageHolder holder) {
			// holder.image.clearAnimation(); // proper workaround without known side-effects
		}

		static class ImageHolder extends RecyclerView.ViewHolder {
			final ImageView image;
			ImageHolder(View v) {
				super(v);
				image = (ImageView)v;
			}
		}
	}
}

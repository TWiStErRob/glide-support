package com.bumptech.glide.supportapp.github._847_shared_transition;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.supportapp.R;
import com.bumptech.glide.supportapp.utils.LoggingListener;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class DetailFragmentWithPager extends DetailFragment {
	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// ImageView is inside pager, it needs to be present at all times so leave transition works nicely
		// for this reason it's added as a child in xml with R.id.image as super expects
		return inflater.inflate(R.layout.github_847_detail_pager, container, false);
	}
	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		ViewPager pager = (ViewPager)view.findViewById(R.id.pager);
		pager.setAdapter(new ImageAdapter());
	}

	private class ImageAdapter extends PagerAdapter {
		@Override public Object instantiateItem(ViewGroup container, int position) {
			ImageView image;
			if (position == 0) { // main image
				// already added, no need for container.addView
				image = DetailFragmentWithPager.this.imageView;
				if (image.getDrawable() == null) {
					// nothing is loaded, that is loadThumb() wasn't called
					// this happens when the ViewPager destroys the main page, but scrolls back to it
					// if a transition happens loadThumb() already set the image and there's no need to do anything
					// the transition listeners will finish the full load
					loadFull(false);
				}
			} else { // rest of the images
				image = new ImageView(container.getContext());
				image.setLayoutParams(new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
				container.addView(image);
				Glide
						.with(DetailFragmentWithPager.this)
						.load(item.images.get(position - 1))
						.centerCrop()
						.listener(new LoggingListener<String, GlideDrawable>())
						.into(image);
			}
			return image;
		}
		@Override public void destroyItem(ViewGroup container, int position, Object object) {
			ImageView imageView = (ImageView)object;
			Glide.clear(imageView);
			if (position != 0) { // keep main image as child so on leave transition it can be animated
				container.removeView(imageView);
			}
		}
		@Override public int getCount() {
			return 1 + item.images.size();
		}
		@Override public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
	}
}

package com.bumptech.glide.supportapp.github._662_detail_cache_hit;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.*;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.supportapp.R;
import com.bumptech.glide.supportapp.utils.*;

public class DetailFragment extends Fragment {
	protected ImageView imageView;

	@Override public @Nullable View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ImageView view = new ImageView(container.getContext());
		view.setId(android.R.id.icon);
		view.setLayoutParams(
				new MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		view.setScaleType(ScaleType.FIT_CENTER);
		return view;
	}

	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		imageView = (ImageView)view.findViewById(android.R.id.icon);
	}

	@Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		final ListItem model = (ListItem)getArguments().getSerializable("model");
		Glide
				.with(this)
				.using(new NetworkDisablingLoader<String>()) // TODO disables network for debug
				.load(model.getStandardUrl())
				.listener(new LoggingListener<String, GlideDrawable>())
				.into(new GlideDrawableImageViewTarget(imageView) {
					@Override public void onLoadFailed(Exception e, Drawable errorDrawable) {
						Glide
								.with(DetailFragment.this)
								.using(new NetworkDisablingLoader<String>()) // TODO disables network for debug
								.load(model.getLowUrl())
								.diskCacheStrategy(DiskCacheStrategy.SOURCE)
								.error(R.drawable.glide_error)
								.listener(new LoggingListener<String, GlideDrawable>())
								.into(imageView);
					}
				});
	}
}

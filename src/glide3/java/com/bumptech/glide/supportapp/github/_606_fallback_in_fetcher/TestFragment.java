package com.bumptech.glide.supportapp.github._606_fallback_in_fetcher;

import android.content.Context;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.supportapp.GlideImageFragment;

public class TestFragment extends GlideImageFragment {
	@Override protected void load(final Context context) throws Exception {
		final ImageView imageView = this.imageView;

		Glide.with(context).load(new YouTubeVideo("watsQDZ3KNw")).into(imageView);
	}
	private void load(final ImageView imageView) {
		if (imageView.getHeight() == 0) {
			// wait for layout, same as glide SizeDeterminer does
			imageView.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
				@Override public boolean onPreDraw() {
					imageView.getViewTreeObserver().removeOnPreDrawListener(this);
					load(imageView); // call the same method, but we can be sure now getHeight() is a value
					return true;
				}
			});
		} else {
			Glide
					.with(imageView.getContext())
					.load("whatever")
					.fitCenter()
					.override(Target.SIZE_ORIGINAL, imageView.getHeight())
					.into(imageView);
		}
	}
}

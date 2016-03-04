package com.bumptech.glide.supportapp.github._527_crossfade_old;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.DrawableCrossFadeFactory;
import com.bumptech.glide.request.animation.GlideAnimation.ViewAdapter;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.supportapp.GlideImageFragment;
import com.bumptech.glide.supportapp.utils.LoggingListener;

public class TestFragment extends GlideImageFragment {
	public static final String[] IMAGES = {
			"http://tny.im/2UO",
			"http://tny.im/2UP",
			"http://tny.im/2UU",
			"http://tny.im/2UV",
			"http://tny.im/2UW"
	};
	int oldIndex = 0;
	@Override protected void load(Context context) throws Exception {
		int newIndex = (oldIndex + 1) % IMAGES.length;
		Glide
				.with(context)
				.load(IMAGES[newIndex])
				.fitCenter() // can be implicit, but see thumbnail
				.thumbnail(Glide
						.with(context)
						.load(IMAGES[oldIndex])
						.fitCenter() // have to be explicit here to match outer load exactly
				)
				.listener(new LoggingListener<String, GlideDrawable>() {
					@Override public boolean onResourceReady(GlideDrawable resource, String model,
							Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
						if (isFirstResource) {
							return false; // thumbnail was not shown, do as usual
						}
						return new DrawableCrossFadeFactory<Drawable>(/* customize timings here */)
								.build(false, false) // force crossFade() even if coming from memory cache
								.animate(resource, (ViewAdapter)target);
					}
				})
				.into(imageView)
		;
		oldIndex = newIndex;
	}
}

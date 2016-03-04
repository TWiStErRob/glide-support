package com.bumptech.glide.supportapp.groups._MAqPfuHpjr4_app_icons;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.supportapp.GlideImageFragment;
import com.bumptech.glide.supportapp.utils.PassthroughModelLoader;

public class TestFragment extends GlideImageFragment {
	@Override protected void load(Context context) throws Exception {
		// === imageView.setImageDrawable(appContext.getPackageManager().getApplicationIcon(context.getApplicationInfo()));
		Glide.with(context)
		     .using(new PassthroughModelLoader<ApplicationInfo, ApplicationInfo>(), ApplicationInfo.class)
		     .from(ApplicationInfo.class)
		     .as(Drawable.class)
		     .decoder(new ApplicationIconDecoder(context))
		     .diskCacheStrategy(DiskCacheStrategy.NONE) // cannot disk cache ApplicationInfo, nor Drawables
		     .load(context.getApplicationInfo())
		     .into(imageView)
		;
	}
}

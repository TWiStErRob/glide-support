package com.bumptech.glide.supportapp.github._1272_palette_cache;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.model.stream.StreamUriLoader;
import com.bumptech.glide.load.resource.bitmap.Downsampler;
import com.bumptech.glide.load.resource.bitmap.StreamBitmapDecoder;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.supportapp.GlideImageFragment;
import com.bumptech.glide.supportapp.utils.DelayTranscoder;
import com.bumptech.glide.supportapp.utils.LoggingListener;
import com.bumptech.glide.supportapp.utils.LoggingTarget;

import androidx.palette.graphics.Palette;

/**
 * Demonstrates how to handle Palette in a separate request, with full caching support.
 * PaletteCacheDecoder and PaletteCacheEncoder contain workarounds for #707.
 */
public class TestFragment_Separate extends GlideImageFragment {
	private GenericRequestBuilder<Uri, InputStream, Palette, Palette> paletteLoad;
	private LoggingTarget<Palette> paletteTarget;
	@Override public void onAttach(Context context) {
		super.onAttach(context);
		paletteLoad = Glide
				.with(this)
				.using(new StreamUriLoader(context), InputStream.class)
				.from(Uri.class)
				.as(Palette.class)
				.diskCacheStrategy(DiskCacheStrategy.ALL)
				.encoder(new PaletteCacheEncoder(new PaletteEncoder()))
				.sourceEncoder(new StreamEncoder())
				.cacheDecoder(new FileToStreamDecoder<>(
						new PaletteCacheDecoder(new PaletteDecoder(), new StreamBitmapDecoder(
								Downsampler.AT_MOST, Glide.get(context).getBitmapPool(), DecodeFormat.DEFAULT))))
				.override(256, 256) // rough size of the Bitmap to generate Palette from 
				.dontTransform() // default, but be explicit
				.dontAnimate() // default, but be explicit
				.skipMemoryCache(true) // debug to always go for disk
		;
	}

	private boolean flipUrl = false;
	@Override protected void load(Context context) throws Exception {
		String url1 =
				"https://cloud.githubusercontent.com/assets/700370/16167962/38fe3b18-34c2-11e6-8085-487af552ec9e.jpg";
		String url2 = "https://pmcdeadline2.files.wordpress.com/2015/10/game-of-thrones-2.jpg";
		Uri url = Uri.parse(flipUrl? url1 : url2);
		flipUrl = !flipUrl;

		// deal with palette first (special usage of Glide)
		if (paletteTarget != null) {
			Glide.clear(paletteTarget);
		}
		View bg = (View)getView().getParent();
		paletteTarget = paletteLoad
				.clone()
				.load(url)
				.listener(new LoggingListener<Uri, Palette>("palette")) // debug LoggingListener
				.into(new LoggingTarget<>(new PaletteViewTarget(bg))); // debug LoggingTarget

		// show actual image separately (usual usage of Glide)
		Glide
				.with(this)
				.load(url)
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				.thumbnail(Glide
						.with(this)
						.load(url)
						.sizeMultiplier(0.05f)
						.diskCacheStrategy(DiskCacheStrategy.ALL)
						.listener(new LoggingListener<Uri, GlideDrawable>("thumb")) // debug LoggingListener
				)
				.skipMemoryCache(true) // debug force transcode
				.transcoder(DelayTranscoder.drawable(1000, context)) // debug to emphasize bg no-change
				.listener(new LoggingListener<Uri, GlideDrawable>("full")) // debug LoggingListener
				.into(new LoggingTarget<>(new GlideDrawableImageViewTarget(imageView))) // debug LoggingTarget
		;
	}

	@Override protected void clear(ImageView imageView) {
		super.clear(imageView);
		Glide.clear(paletteTarget);
		// equivalent, but without having to save the target
		// (requires get/setRequest to be consistent with the constructor arg)
		//Glide.clear(new PaletteViewTarget((View)getView().getParent()));
	}

	/**
	 * A stateless target that can be used to change the background of a view.
	 * (Notice that BaseTarget would be stateful)
	 * It's also useful to clear without having to remember the actual target. 
	 */
	private class PaletteViewTarget extends SimpleTarget<Palette> {
		private final View view;
		public PaletteViewTarget(View view) {
			this.view = view;
		}

		@Override public void setRequest(Request request) {
			view.setTag(request);
		}

		@Override public Request getRequest() {
			Object tag = view.getTag();
			Request request = null;
			if (tag != null) {
				if (tag instanceof Request) {
					request = (Request)tag;
				} else {
					throw new IllegalArgumentException("You must not call setTag() on a view Glide is targeting");
				}
			}
			return request;
		}

		@Override public void onResourceReady(Palette palette,
				GlideAnimation<? super Palette> glideAnimation) {
			int color = palette.getLightVibrantColor(palette.getLightMutedColor(Color.WHITE));
			view.setBackgroundColor(color);
		}
		@SuppressWarnings("deprecation")
		@Override public void onLoadCleared(Drawable placeholder) {
			super.onLoadCleared(placeholder);
			view.setBackgroundDrawable(null);
		}
		@Override public void onLoadFailed(Exception e, Drawable errorDrawable) {
			super.onLoadFailed(e, errorDrawable);
			view.setBackgroundColor(Color.RED);
		}
	}
}

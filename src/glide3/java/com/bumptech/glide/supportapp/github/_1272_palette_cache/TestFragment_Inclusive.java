package com.bumptech.glide.supportapp.github._1272_palette_cache;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.*;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.model.stream.StreamUriLoader;
import com.bumptech.glide.load.resource.bitmap.*;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.supportapp.GlideImageFragment;
import com.bumptech.glide.supportapp.github._1013_palette.PaletteBitmap;
import com.bumptech.glide.supportapp.utils.*;

/**
 * Demonstrates how to handle Palette in the same request, with full caching support.
 * The cached file contains both the image and the serialized palette.
 * This saves some resources, because it prevents regeneration (if Bitmap bytes didn't change, Palette didn't either).
 * Palette needs to be written first, because Glide is too eager to read the stream.
 * It likely will read ahead a little than needed because of the buffered streams used in the process.
 * Notice that in this case the thumbnail and the full load has different palettes and the color changes slightly.
 */
public class TestFragment_Inclusive extends GlideImageFragment {
	private GenericRequestBuilder<Uri, InputStream, PaletteBitmap, PaletteBitmap> paletteLoad;
	@Override public void onAttach(Context context) {
		super.onAttach(context);
		BitmapPool pool = Glide.get(context).getBitmapPool();
		StreamBitmapDecoder bitmapDecoder = new StreamBitmapDecoder(Downsampler.AT_LEAST, pool, DecodeFormat.DEFAULT);
		paletteLoad = Glide
				.with(this)
				.using(new StreamUriLoader(context), InputStream.class)
				.from(Uri.class)
				.as(PaletteBitmap.class)
				.diskCacheStrategy(DiskCacheStrategy.ALL)
				.encoder(new PaletteBitmapEncoder(new BitmapEncoder(), new PaletteEncoder()))
				.sourceEncoder(new StreamEncoder())
				.cacheDecoder(new FileToStreamDecoder<>(
						new PaletteBitmapDecoder(pool, bitmapDecoder, new PaletteDecoder())))
				.dontAnimate()
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

		View bg = (View)getView().getParent();
		paletteLoad
				.clone()
				.load(url)
				.thumbnail(paletteLoad
						.clone()
						.load(url)
						.sizeMultiplier(0.05f)
						.listener(new LoggingListener<Uri, PaletteBitmap>("thumb")) // debug LoggingListener
				)
				.listener(new LoggingListener<Uri, PaletteBitmap>("full")) // debug LoggingListener
				.transcoder(DelayTranscoder.<PaletteBitmap>unit(1000)) // debug to emphasize bg change
				.into(new LoggingTarget<>(new PaletteBitmapTarget(imageView, bg))) // debug LoggingTarget
		;
	}

	private class PaletteBitmapTarget extends ImageViewTarget<PaletteBitmap> {
		private final View background;
		public PaletteBitmapTarget(ImageView view, View background) {
			super(view);
			this.background = background;
		}

		@Override public void onResourceReady(PaletteBitmap pb, GlideAnimation<? super PaletteBitmap> glideAnimation) {
			super.onResourceReady(pb, glideAnimation);
			int color = pb.palette.getLightVibrantColor(pb.palette.getLightMutedColor(Color.WHITE));
			background.setBackgroundColor(color);
		}
		@SuppressWarnings("deprecation")
		@Override public void onLoadCleared(Drawable placeholder) {
			super.onLoadCleared(placeholder);
			background.setBackgroundDrawable(null);
		}
		@Override public void onLoadFailed(Exception e, Drawable errorDrawable) {
			super.onLoadFailed(e, errorDrawable);
			background.setBackgroundColor(Color.RED);
		}

		@Override protected void setResource(PaletteBitmap resource) {
			view.setImageBitmap(resource.bitmap);
		}
	}
}


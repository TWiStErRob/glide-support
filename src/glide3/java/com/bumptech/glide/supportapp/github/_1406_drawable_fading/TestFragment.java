package com.bumptech.glide.supportapp.github._1406_drawable_fading;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.*;
import android.support.v4.content.ContextCompat;

import com.bumptech.glide.*;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.model.stream.StreamStringLoader;
import com.bumptech.glide.load.resource.bitmap.*;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.bumptech.glide.load.resource.gif.*;
import com.bumptech.glide.load.resource.gifbitmap.*;
import com.bumptech.glide.load.resource.transcode.*;
import com.bumptech.glide.supportapp.*;
import com.bumptech.glide.supportapp.utils.*;

public class TestFragment extends GlideImageFragment {
	private GenericRequestBuilder<String, ?, GifBitmapWrapper, Drawable> urlGlide;
	private GenericRequestBuilder<Drawable, ?, Drawable, Drawable> drawableGlide;
	/**
	 * Doesn't matter what was the last load just store it.
	 * This way, when the decision is made to load the next url/drawable, there's less cases to handle.
	 */
	private GenericRequestBuilder<?, ?, ?, Drawable> lastLoad;

	@Override public void onAttach(Context context) {
		super.onAttach(context);
		BitmapPool pool = Glide.get(context).getBitmapPool();
		// default decoder, this is the base complexity Glide.with.load.into() hides from you
		GifBitmapWrapperStreamResourceDecoder decoder = new GifBitmapWrapperStreamResourceDecoder(
				new GifBitmapWrapperResourceDecoder(
						new ImageVideoBitmapDecoder(
								new StreamBitmapDecoder(pool),
								new FileDescriptorBitmapDecoder(context)
						),
						new GifResourceDecoder(context, pool),
						pool
				)
		);

		// all the defaults hardcoded here in order to end up with a normal Drawable instead of a GlideDrawable
		urlGlide = Glide
				.with(this)
				.using(new StreamStringLoader(getActivity()), InputStream.class)
				.from(String.class)
				.as(GifBitmapWrapper.class)
				.transcode(new GeneralizingTranscoder<GifBitmapWrapper, GlideDrawable, Drawable>(
								new GifBitmapWrapperDrawableTranscoder(new GlideBitmapDrawableTranscoder(context))),
						Drawable.class)
				.decoder(decoder)
				.sourceEncoder(new StreamEncoder())
				.cacheDecoder(new FileToStreamDecoder<>(decoder))
				.animate(new AlwaysCrossFade(true))
				.encoder(new GifBitmapWrapperResourceEncoder(new BitmapEncoder(), new GifResourceEncoder(pool)))
				.diskCacheStrategy(DiskCacheStrategy.ALL) // just to demonstrate it's working, pick your preference
				.transform(new GifBitmapWrapperTransformation(pool, new FitCenter(context))) // == .fitCenter()
				.listener(new LoggingListener<String, Drawable>("url")) // debug
		;

		// see https://github.com/bumptech/glide/issues/122#issuecomment-99629392
		drawableGlide = Glide
				.with(this)
				.using(new PassthroughModelLoader<Drawable, Drawable>(), Drawable.class)
				.from(Drawable.class)
				.as(Drawable.class)
				// this works even if the drawables don't behave well regarding constantState.newDrawable
				// Beware: might be problematic if constant state is supported, but altered (e.g. color filters)
				.decoder(new SimpleResourceDecoder<Drawable>()) // prefer DrawableResourceDecoder if possible!
				.animate(new AlwaysCrossFade(true))
				.diskCacheStrategy(DiskCacheStrategy.NONE)
				.listener(new LoggingListener<Drawable, Drawable>("drawable")) // debug
		;

		// prevents null check the first time
		// don't forge to null everything out explicitly (error/placeholder/fallback/thumbnail), if they're set above
		lastLoad = urlGlide.clone().load(null).listener(null).dontAnimate();
	}

	private static final String[] URLS = new String[] {
			"https://pixabay.com/static/uploads/photo/2015/10/01/21/39/background-image-967820_960_720.jpg",
			"http://indy100.independent.co.uk/image/31869-c6d5w5.jpg",
			"http://www.space.com/images/i/000/009/146/original/heic1012a_EDIT.jpg",
			"android.resource://" + App.getInstance().getPackageName() + "/drawable/glide_jpeg",
			"android.resource://" + App.getInstance().getPackageName() + "/drawable/glide_gif", // transparent
			"android.resource://" + App.getInstance().getPackageName() + "/drawable/glide_anim", // animated
	};

	private static final Drawable[] DRAWABLES = new Drawable[] {
			new ColorDrawable(Color.RED),
			new ColorDrawable(Color.GREEN),
			ContextCompat.getDrawable(App.getInstance(), R.drawable.github_1261_nine_to_five), // vector, animated
			new ColorDrawable(Color.BLUE),
	};

	@Override protected void load(Context context) throws Exception {
		lastLoad.thumbnail(null);
		if (Math.random() < 0.5) {
			String url = URLS[(int)(Math.random() * URLS.length)];
			lastLoad = urlGlide
					.clone()
					.load(url)
					.thumbnail(lastLoad)
			;
		} else {
			Drawable drawable = DRAWABLES[(int)(Math.random() * DRAWABLES.length)];
			lastLoad = drawableGlide
					.clone()
					.load(drawable)
					// Something needs to be used for allowing memory cache, otherwise the whole "force"-cross-fade
					// doesn't work. The thumbnail load (i.e. lastLoad) needs to hit the memory cache in order to have
					// a nice transition between shown images.
					.signature(new ObjectIdentitySignature(drawable))
					.thumbnail(lastLoad)
			;
		}
		lastLoad.into(new LoggingTarget<>(new AnimatableDrawableImageViewTarget(imageView)));
	}
}

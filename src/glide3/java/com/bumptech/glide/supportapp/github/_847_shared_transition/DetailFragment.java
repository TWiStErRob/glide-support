package com.bumptech.glide.supportapp.github._847_shared_transition;

import java.util.List;

import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.transition.Transition;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.supportapp.R;
import com.bumptech.glide.supportapp.utils.ForceCrossfadeListener;
import com.bumptech.glide.supportapp.utils.LoggingListener;
import com.bumptech.glide.supportapp.utils.LoggingTransitionListener;
import com.bumptech.glide.supportapp.utils.MultiRequestListener;
import com.bumptech.glide.supportapp.utils.NetworkDisablingLoader;

import androidx.annotation.Nullable;
import androidx.core.app.SharedElementCallback;
import androidx.fragment.app.Fragment;

public abstract class DetailFragment extends Fragment {
	static final String ARG_ITEM = "item";
	static final String ARG_TRANS_NAME_IMAGE = "trans.image";
	static final String ARG_TRANS_NAME_TEXT = "trans.text";

	private static final LoggingListener<Object, GlideDrawable> THUMB_LOGGER = new LoggingListener<>("thumb");
	private static final LoggingListener<Object, GlideDrawable> FULL_LOGGER = new LoggingListener<>("full");
	@SuppressWarnings("unchecked")
	private static final MultiRequestListener<Object, GlideDrawable> FORCE_FADER = new MultiRequestListener<>(
			FULL_LOGGER, new ForceCrossfadeListener<GlideDrawable>(1500)
	);

	protected Item item;
	protected ImageView imageView;

	private DrawableRequestBuilder<?> thumb;
	private DrawableRequestBuilder<?> full;

	@Override public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
			LoggingTransitionListener.listenForAll("onCreate", this);
		}
		item = (Item)getArguments().getSerializable(ARG_ITEM);
		setupGlide();
	}

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.github_847_detail, container, false);
	}

	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setupImage((ImageView)view.findViewById(R.id.image));
		setupText((TextView)view.findViewById(R.id.text));
	}

	private void setupGlide() {
		thumb = Glide
				.with(this)
				.using(new NetworkDisablingLoader<>()) // make sure we're hitting some cache (optional)
				// everything has to match the grid's adapter to hit the memory cache
				.load(item.thumbUrl)
				.override(256, 256)
				.centerCrop()
				.listener(THUMB_LOGGER) // irrelevant for cache
		;
		int width = getActivity().getWindow().getDecorView().getWidth(); // this assumes that the fragment is created
		// from an existing fragment inside the same activity (list-detail), otherwise getWidth() may be 0
		full = Glide
				.with(this)
				.load(item.fullUrl)
				.override(width, Target.SIZE_ORIGINAL)
				.listener(FULL_LOGGER)
		;
	}
	protected void loadThumb() {
		Log.i("GLIDE", "Starting thumb");
		thumb.into(imageView);
	}

	protected void loadFull(boolean force) {
		Log.i("GLIDE", "Starting full");
		full.listener(force? FORCE_FADER : FULL_LOGGER);
		full.thumbnail(thumb).into(imageView);
	}

	private void setupImage(ImageView view) {
		this.imageView = view;
		if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
			imageView.setTransitionName(getArguments().getString(ARG_TRANS_NAME_IMAGE));
			// set up listener for entering transition to finish it with full image
			((Transition)getSharedElementEnterTransition()).addListener(new LoggingTransitionListener("sharedEnter") {
				@Override public void onTransitionEnd(Transition transition) {
					super.onTransitionEnd(transition);
					loadFull(true); // finish loading the full image
					// without this the bad quality thumbnail would stay loaded
					// it cannot be loaded elsewhere because it would finish mid-transition

					// this will be called when the back-transition completes too, but Glide swallows that
				}
			});
			// set up callback for leaving transition (for some reason it works only with Enter and not Exit)
			setEnterSharedElementCallback(new SharedElementCallback() {
				@Override public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements,
						List<View> sharedElementSnapshots) {
					super.onSharedElementStart(sharedElementNames, sharedElements, sharedElementSnapshots);
					loadThumb(); // reset thumbnail when going back
					// without this the size of the ImageView jumps back to thumbnail size
				}
			});
			loadThumb(); // load the thumbnail that was shown in the grid
			// without this there would be no transition
			// this cannot be loadFull because it would resize badly
			full.preload(); // start loading so that when really needed we just need to do a crossfade
			// since the full load has override on it as well this will start to preload that
		} else {
			loadFull(false);
		}
	}

	private void setupText(TextView textView) {
		if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
			textView.setTransitionName(getArguments().getString(ARG_TRANS_NAME_TEXT));
		}
		textView.setText(item.color);
	}
}

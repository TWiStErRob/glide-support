package com.bumptech.glide.supportapp.github._1013_palette;

import java.util.*;

import android.animation.*;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.*;
import android.os.Build.*;
import android.os.Bundle;
import android.support.annotation.*;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.graphics.Palette;
import android.support.v7.graphics.Palette.Swatch;
import android.support.v7.widget.*;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.*;
import android.widget.*;

import static android.animation.ObjectAnimator.*;

import com.bumptech.glide.*;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.*;
import com.bumptech.glide.request.target.*;
import com.bumptech.glide.supportapp.*;

public class TestFragment extends GlideRecyclerFragment {
	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		listView.setAdapter(new PaletteAdapter(getContext(), Glide.with(this), Arrays.asList(
				"https://placeimg.com/640/480/nature?1",
				"https://placeimg.com/640/480/nature?2",
				"https://placeimg.com/640/480/nature?3",
				null,
				"https://placeimg.com/640/480/nature?4",
				"https://placeimg.com/640/480/nature?5",
				"https://placeimg.com/640/480/nature?6",
				null,
				"https://placeimg.com/640/480/nature?7",
				"https://placeimg.com/640/480/nature?8",
				"https://placeimg.com/640/480/nature?9",
				null,
				"https://placeimg.com/640/480/nature?10"
		)));
	}

	private static class PaletteAdapter extends Adapter<PaletteAdapter.ImageTextViewHolder> {
		private static final int FADE_DURATION = 3000;
		private final BitmapRequestBuilder<String, PaletteBitmap> glideRequest;
		private final List<String> data;
		private final @ColorInt int defaultColor;
		PaletteAdapter(Context context, RequestManager glide, List<String> data) {
			this.data = data;
			this.defaultColor = ContextCompat.getColor(context, R.color.github_1013_default);
			this.glideRequest = glide
					.fromString()
					.asBitmap()
					.transcode(new PaletteBitmapTranscoder(context), PaletteBitmap.class)
					.placeholder(R.drawable.glide_placeholder)
					.animate(new BitmapContainerCrossFadeFactory<PaletteBitmap>(FADE_DURATION) {
						@Override protected Bitmap getBitmap(PaletteBitmap bitmap) {
							return bitmap.bitmap;
						}
					})
					.fitCenter()
					.diskCacheStrategy(DiskCacheStrategy.ALL);
		}
		@Override public int getItemCount() {
			return data.size();
		}
		@Override public ImageTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			View view = inflater.inflate(R.layout.github_1013_item, parent, false);
			return new ImageTextViewHolder(view);
		}
		@Override public void onBindViewHolder(final ImageTextViewHolder holder, int position) {
			// reset color so it looks like the view was just inflated even if it was recycled
			// this is to prevent inheriting another position's colors
			holder.card.setCardBackgroundColor(defaultColor);
			holder.titleView.setBackgroundColor(defaultColor);
			holder.titleView.setTextColor(ColorUtils.setAlphaComponent(~defaultColor, 0xFF));
			String url = data.get(position);
			holder.titleView.setText(String.valueOf(url)); // explicitly show null
			if (url != null) { // simulate an optional url from the data item
				holder.imageView.setVisibility(View.VISIBLE);
				glideRequest
						.load(url)
						.into(holder.target);
			} else {
				// clear when no image is shown, don't use holder.imageView.setImageDrawable(null) to do the same
				Glide.clear(holder.imageView);
				holder.imageView.setVisibility(View.GONE);
			}
		}
		@Override public void onViewRecycled(ImageTextViewHolder holder) {
			super.onViewRecycled(holder);
			// optional, but recommended way to clear up the resources used by Glide
			Glide.clear(holder.imageView);
		}
		class ImageTextViewHolder extends RecyclerView.ViewHolder {
			final CardView card;
			final ImageView imageView;
			final TextView titleView;
			final Target<PaletteBitmap> target;
			ImageTextViewHolder(View itemView) {
				super(itemView);
				card = (CardView)itemView;
				imageView = (ImageView)itemView.findViewById(R.id.github_1013_image);
				titleView = (TextView)itemView.findViewById(R.id.github_1013_title);
				target = new PaletteBitmapImageViewTarget(this, defaultColor);
			}
		}

		private static class PaletteBitmapImageViewTarget extends ImageViewTarget<PaletteBitmap> {
			private final ImageTextViewHolder holder;
			private final int defaultColor;
			private AnimatorSet anims;
			PaletteBitmapImageViewTarget(ImageTextViewHolder holder, int defaultColor) {
				super(holder.imageView);
				this.holder = holder;
				this.defaultColor = defaultColor;
			}
			@Override protected void setResource(PaletteBitmap resource) {
				super.view.setImageBitmap(resource.bitmap);
			}
			@Override public void onResourceReady(PaletteBitmap resource,
					GlideAnimation<? super PaletteBitmap> glideAnimation) {
				if (glideAnimation == null || !glideAnimation.animate(resource, this)) {
					this.setResource(resource);
					setColors(resource.palette);
				} else {
					if (VERSION_CODES.HONEYCOMB <= VERSION.SDK_INT) {
						animateColors(resource.palette);
					} else {
						setColors(resource.palette);
					}
				}
			}
			@Override public void onLoadCleared(Drawable placeholder) {
				super.onLoadCleared(placeholder);
				if (anims != null) {
					anims.cancel();
					anims = null;
				}
			}
			private void setColors(Palette palette) {
				int color = palette.getVibrantColor(defaultColor);
				Swatch swatch = palette.getMutedSwatch();

				holder.card.setCardBackgroundColor(color);
				if (swatch != null) {
					holder.titleView.setBackgroundColor(swatch.getRgb());
					holder.titleView.setTextColor(swatch.getTitleTextColor());
				}
			}
			private void animateColors(Palette palette) {
				int color = palette.getVibrantColor(defaultColor);
				Swatch swatch = palette.getMutedSwatch();
				anims = new AnimatorSet();

				ValueAnimator cardBG = ValueAnimator.ofObject(new ArgbEvaluator(),
						defaultColor/* cannot retrieve card BG */, color);
				cardBG.addUpdateListener(new AnimatorUpdateListener() {
					@Override public void onAnimationUpdate(ValueAnimator animation) {
						holder.card.setCardBackgroundColor((Integer)animation.getAnimatedValue());
					}
				});
				anims.play(cardBG);
				if (swatch != null) {
					ObjectAnimator textColorBG = ofObject(holder.titleView, "backgroundColor", new ArgbEvaluator(),
							((ColorDrawable)holder.titleView.getBackground()).getColor(), swatch.getRgb());
					ObjectAnimator textColor = ofObject(holder.titleView, "textColor", new ArgbEvaluator(),
							holder.titleView.getCurrentTextColor(), swatch.getTitleTextColor());
					anims.play(textColor);
					anims.play(textColorBG);
				}
				anims.playTogether(anims.getChildAnimations());
				anims.setDuration(3000);
				anims.start();
			}
		}
	}
}

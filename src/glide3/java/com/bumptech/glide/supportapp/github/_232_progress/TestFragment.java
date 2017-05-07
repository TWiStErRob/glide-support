package com.bumptech.glide.supportapp.github._232_progress;

import java.util.*;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.*;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.*;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.target.*;
import com.bumptech.glide.supportapp.R;
import com.bumptech.glide.supportapp.utils.*;

public class TestFragment extends Fragment {
	@Override public @Nullable View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		RecyclerView list = new RecyclerView(container.getContext());
		list.setLayoutParams(new MarginLayoutParams(MarginLayoutParams.MATCH_PARENT, MarginLayoutParams.MATCH_PARENT));
		list.setLayoutManager(new LinearLayoutManager(container.getContext()));
		list.setAdapter(new ProgressAdapter(Arrays.asList(
				// few results from https://www.google.com/search?tbm=isch&q=image&tbs=isz:lt,islt:4mp
				"http://www.noaanews.noaa.gov/stories/images/goes-12%2Dfirstimage-large081701%2Ejpg",
				"http://www.spektyr.com/PrintImages/Cerulean%20Cross%203%20Large.jpg",
				"https://cdn.photographylife.com/wp-content/uploads/2014/06/Nikon-D810-Image-Sample-6.jpg",
				"https://upload.wikimedia.org/wikipedia/commons/5/5b/Ultraviolet_image_of_the_Cygnus_Loop_Nebula_crop.jpg",
				"https://upload.wikimedia.org/wikipedia/commons/c/c5/Polarlicht_2_kmeans_16_large.png",
				"https://www.hq.nasa.gov/alsj/a15/M1123519889LCRC_isometric_min-8000_g0dot5_enhanced_labeled.jpg",
				"http://oceanexplorer.noaa.gov/explorations/02fire/logs/hirez/octopus_hires.jpg",
				"https://upload.wikimedia.org/wikipedia/commons/b/bf/GOES-13_First_Image_jun_22_2006_1730Z.jpg",
				"http://www.zastavki.com/pictures/originals/2013/Photoshop_Image_of_the_horse_053857_.jpg",
				"http://www.marcogiordanotd.com/blog/wp-content/uploads/2014/01/image9Kcomp.jpg",
				"https://cdn.photographylife.com/wp-content/uploads/2014/06/Nikon-D810-Image-Sample-7.jpg",
				"https://www.apple.com/v/imac-with-retina/a/images/overview/5k_image.jpg",
				"https://www.gimp.org/tutorials/Lite_Quickies/lordofrings_hst_big.jpg",
				"http://www.cesbio.ups-tlse.fr/multitemp/wp-content/uploads/2015/07/Mad%C3%A8re-022_0_1.jpg",
				"https://www.spacetelescope.org/static/archives/fitsimages/large/slawomir_lipinski_04.jpg",
				"https://upload.wikimedia.org/wikipedia/commons/b/b4/Mardin_1350660_1350692_33_images.jpg",
				"http://4k.com/wp-content/uploads/2014/06/4k-image-tiger-jumping.jpg"
		)));
		return list;
	}

	private static class ProgressViewHolder extends ViewHolder {
		private final ImageView image;
		private final TextView text;
		private final ProgressBar progress;
		/** Cache target because all the views are tied to this view holder. */
		private final ProgressTarget<String, Bitmap> target;
		ProgressViewHolder(View root) {
			super(root);
			image = (ImageView)root.findViewById(R.id.image);
			text = (TextView)root.findViewById(R.id.text);
			progress = (ProgressBar)root.findViewById(R.id.progress);
			image.setOnClickListener(new OnClickListener() {
				@Override public void onClick(View v) {
					bind(target.getModel());
				}
			});
			target = new MyProgressTarget<>(new BitmapImageViewTarget(image), progress, image, text);
		}
		void bind(String url) {
			target.setModel(url); // update target's cache
			Glide
					.with(image.getContext())
					.load(url)
					.asBitmap()
					.placeholder(R.drawable.github_232_progress)
					.centerCrop() // needs explicit transformation, because we're using a custom target
					.transform(new CenterCrop(image.getContext()), new DelayBitmapTransformation(1000))
					.listener(new LoggingListener<String, Bitmap>())
					.into(target)
			;
		}
	}

	/**
	 * Demonstrates 3 different ways of showing the progress:
	 * <ul>
	 * <li>Update a full fledged progress bar</li>
	 * <li>Update a text view to display size/percentage</li>
	 * <li>Update the placeholder via Drawable.level</li>
	 * </ul>
	 * This last one is tricky: the placeholder that Glide sets can be used as a progress drawable
	 * without any extra Views in the view hierarchy if it supports levels via <code>usesLevel="true"</code>
	 * or <code>level-list</code>.
	 *
	 * @param <Z> automatically match any real Glide target so it can be used flexibly without reimplementing.
	 */
	@SuppressLint("SetTextI18n") // text set only for debugging
	private static class MyProgressTarget<Z> extends ProgressTarget<String, Z> {
		private final TextView text;
		private final ProgressBar progress;
		private final ImageView image;
		public MyProgressTarget(Target<Z> target, ProgressBar progress, ImageView image, TextView text) {
			super(target);
			this.progress = progress;
			this.image = image;
			this.text = text;
		}

		@Override public float getGranualityPercentage() {
			return 0.1f; // this matches the format string for #text below
		}

		@Override protected void onConnecting() {
			progress.setIndeterminate(true);
			progress.setVisibility(View.VISIBLE);
			image.setImageLevel(0);
			text.setVisibility(View.VISIBLE);
			text.setText("connecting");
		}
		@Override protected void onDownloading(long bytesRead, long expectedLength) {
			progress.setIndeterminate(false);
			progress.setProgress((int)(100 * bytesRead / expectedLength));
			image.setImageLevel((int)(10000 * bytesRead / expectedLength));
			text.setText(String.format(Locale.ROOT, "downloading %.2f/%.2f MB %.1f%%",
					bytesRead / 1e6, expectedLength / 1e6, 100f * bytesRead / expectedLength));
		}
		@Override protected void onDownloaded() {
			progress.setIndeterminate(true);
			image.setImageLevel(10000);
			text.setText("decoding and transforming");
		}
		@Override protected void onDelivered() {
			progress.setVisibility(View.INVISIBLE);
			image.setImageLevel(0); // reset ImageView default
			text.setVisibility(View.INVISIBLE);
		}
	}

	private static class ProgressAdapter extends Adapter<ProgressViewHolder> {
		private final List<String> models;
		public ProgressAdapter(List<String> models) {
			this.models = models;
		}
		@Override public ProgressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.github_232_item, parent, false);
			return new ProgressViewHolder(view);
		}
		@Override public void onBindViewHolder(ProgressViewHolder holder, int position) {
			holder.bind(models.get(position));
		}
		@Override public int getItemCount() {
			return models.size();
		}
	}
}

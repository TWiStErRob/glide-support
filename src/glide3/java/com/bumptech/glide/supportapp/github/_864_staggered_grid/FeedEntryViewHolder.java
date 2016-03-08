package com.bumptech.glide.supportapp.github._864_staggered_grid;

import org.json.*;

import android.support.percent.PercentFrameLayout;
import android.support.percent.PercentLayoutHelper.PercentLayoutInfo;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.supportapp.utils.LoggingListener;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

class FeedEntryViewHolder extends RecyclerView.ViewHolder {
	private final RequestManager glide;
	ImageView image;
	TextView title;
	ImageView author;
	private int previousPosition = -1;

	public FeedEntryViewHolder(View itemView, RequestManager glide) {
		super(itemView);
		this.glide = glide;
		image = (ImageView)itemView.findViewById(android.R.id.icon);
		title = (TextView)itemView.findViewById(android.R.id.text1);
		author = (ImageView)itemView.findViewById(android.R.id.icon1);
	}

	public void bind(JSONObject entry) throws JSONException {
		bindImage(entry.getJSONObject("media$group").getJSONArray("media$content").getJSONObject(0));
		bindAuthor(entry.getJSONArray("author").getJSONObject(0));

		previousPosition = getAdapterPosition();
	}

	private void bindAuthor(JSONObject author) throws JSONException {
		this.title.setText(author.getJSONObject("name").getString("$t"));
		String url = author.getJSONObject("gphoto$thumbnail").getString("$t");
		glide
				.load(url)
				.asBitmap()
				.transform(new CropCircleTransformation(this.author.getContext()))
				.into(this.author);
	}

	private void bindImage(JSONObject media) throws JSONException {
		String url = media.getString("url");
		int width = media.getInt("width");
		int height = media.getInt("height");
		fixlayout(width, height);
		glide
				.load(url)
				//.sizeMultiplier(.5f) // smaller disk and memory footprint on account of quality loss
				.listener(new LoggingListener<String, GlideDrawable>())
				.into(this.image);
	}

	private void fixlayout(float width, float height) {
		PercentFrameLayout.LayoutParams layoutParams =
				(PercentFrameLayout.LayoutParams)image.getLayoutParams();
		PercentLayoutInfo info = layoutParams.getPercentLayoutInfo();

		float oldAspect = info.aspectRatio;
		float newAspect = width / height;
		Log.v("LAYOUT", String.format("recycled %d to %d: %dx%d, aspect %.3f -> %.3f",
				previousPosition, getAdapterPosition(), image.getWidth(), image.getHeight(), oldAspect, newAspect));

		info.aspectRatio = newAspect;
		layoutParams.height = 0; // @see PercentLayoutHelper.PercentLayoutInfo.fillLayoutParams()
		image.setLayoutParams(layoutParams);
	}
}

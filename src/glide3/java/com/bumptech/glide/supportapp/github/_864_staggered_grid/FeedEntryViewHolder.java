package com.bumptech.glide.supportapp.github._864_staggered_grid;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.supportapp.R;
import com.bumptech.glide.supportapp.utils.LoggingListener;

import androidx.recyclerview.widget.RecyclerView;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

class FeedEntryViewHolder extends RecyclerView.ViewHolder {
	private final RequestManager glide;
	private final ImageView image;
	private final TextView title;
	private final ImageView author;
	/** Only for logging, it stores the previous adapter position.
	 * {@link RecyclerView.ViewHolder#getOldPosition()} doesn't do this. */
	private int previousPosition = -1;

	public FeedEntryViewHolder(View itemView, RequestManager glide) {
		super(itemView);
		this.glide = glide;
		this.image = (ImageView)itemView.findViewById(R.id.github_864_image);
		this.title = (TextView)itemView.findViewById(R.id.github_864_title);
		this.author = (ImageView)itemView.findViewById(R.id.github_864_author_icon);
	}

	public void bind(JSONObject entry) throws JSONException {
		try {
			bindImage(entry.getJSONObject("media$group").getJSONArray("media$content").getJSONObject(0));
			bindAuthor(entry.getJSONArray("author").getJSONObject(0));
		} finally {
			previousPosition = getBindingAdapterPosition();
		}
	}

	@SuppressWarnings("unchecked")
	private void bindAuthor(JSONObject author) throws JSONException {
		this.title.setText(author.getJSONObject("name").getString("$t"));
		String url = author.getJSONObject("gphoto$thumbnail").getString("$t");
		BitmapPool pool = Glide.get(this.author.getContext()).getBitmapPool();
		glide
				.load(url)
				.asBitmap()
				.transform(new CropCircleTransformation(pool))
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
		@SuppressWarnings("deprecation") // legacy code, TODO migrate to ConstraintLayout.
		androidx.percentlayout.widget.PercentFrameLayout.LayoutParams layoutParams =
				(androidx.percentlayout.widget.PercentFrameLayout.LayoutParams)image.getLayoutParams();
		@SuppressWarnings("deprecation") // legacy code, TODO migrate to ConstraintLayout.
		androidx.percentlayout.widget.PercentLayoutHelper.PercentLayoutInfo info = layoutParams.getPercentLayoutInfo();

		float oldAspect = info.aspectRatio;
		float newAspect = width / height;
		Log.v("LAYOUT", String.format("recycled %d to %d: %dx%d, aspect %.3f -> %.3f",
				previousPosition, getBindingAdapterPosition(), image.getWidth(), image.getHeight(), oldAspect, newAspect));

		info.aspectRatio = newAspect;
		layoutParams.height = 0; // @see PercentLayoutHelper.PercentLayoutInfo.fillLayoutParams()
		image.setLayoutParams(layoutParams);
	}
}

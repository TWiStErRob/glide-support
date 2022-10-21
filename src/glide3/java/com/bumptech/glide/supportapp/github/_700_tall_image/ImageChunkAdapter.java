package com.bumptech.glide.supportapp.github._700_tall_image;

import java.util.Locale;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.supportapp.utils.LoggingListener;

import androidx.recyclerview.widget.RecyclerView;

public class ImageChunkAdapter extends RecyclerView.Adapter<ImageChunkAdapter.ImageChunkViewHolder> {
	private final String url;
	private final Point image;
	private final int imageChunkHeight;
	private final float ratio;

	public ImageChunkAdapter(Point screen, String url, Point image) {
		this.url = url;
		this.image = image;

		// calculate a chunk's height
		this.ratio = screen.x / (float)image.x; // image will be fit to width
		// this will result in having the chunkHeight between 1/3 and 2/3 of screen height, making sure it fits in memory
		int minScreenChunkHeight = screen.y / 3;
		int screenChunkHeight = leastMultiple(screen.x / gcd(screen.x, image.x), minScreenChunkHeight);
		// GCD helps to keep this a whole number
		// worst case GCD is 1 so screenChunkHeight == screen.x -> imageChunkHeight == image.x
		this.imageChunkHeight = Math.round(screenChunkHeight / ratio);
		// screen: Point(720, 1280), image: Point(500, 4784), ratio: 1.44, screenChunk: 396 (396.000031), imageChunk: 275 (275)
		// screen: Point(1280, 720), image: Point(7388, 16711), ratio: 0.173254, screenChunk: 320 (320.000000), imageChunk: 1847 (1847.000000)
		Log.wtf("GLIDE", String.format(Locale.ROOT,
				"screen: %s, image: %s, ratio: %f, screenChunk: %d (%f), imageChunk: %d (%f)",
				screen, image, ratio,
				screenChunkHeight, imageChunkHeight * ratio,
				imageChunkHeight, screenChunkHeight / ratio));
	}

	/** Greatest Common Divisor */
	private static int gcd(int a, int b) {
		while (b != 0) {
			int t = b;
			b = a % b;
			a = t;
		}
		return a;
	}

	/**
	 * @param base positive whole number
	 * @param threshold positive whole number
	 * @return multiple of base that is >= threshold
	 */
	private static int leastMultiple(int base, int threshold) {
		int minMul = Math.max(1, threshold / base);
		return base * minMul;
	}

	@Override public int getItemCount() {
		// round up for last partial row
		return image.y / imageChunkHeight + (image.y % imageChunkHeight == 0? 0 : 1);
	}

	@Override public long getItemId(int position) {
		return imageChunkHeight * position;
	}

	@Override public ImageChunkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		ImageView view = new ImageView(parent.getContext());
		view.setScaleType(ScaleType.CENTER);
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, (int)(imageChunkHeight * ratio)));
		return new ImageChunkViewHolder(view);
	}

	@Override public void onBindViewHolder(ImageChunkViewHolder holder, int position) {
		int left = 0, top = imageChunkHeight * position;
		int width = image.x, height = imageChunkHeight;
		if (position == getItemCount() - 1 && image.y % imageChunkHeight != 0) {
			height = image.y % imageChunkHeight; // height of last partial row, if any
		}
		Rect rect = new Rect(left, top, left + width, top + height);
		float viewWidth = width * ratio;
		float viewHeight = height * ratio;

		final String bind = String.format(Locale.ROOT, "Binding %s w=%d (%d->%f) h=%d (%d->%f)",
				rect.toShortString(),
				rect.width(), width, viewWidth,
				rect.height(), height, viewHeight);

		Context context = holder.itemView.getContext();
		// See https://docs.google.com/drawings/d/1KyOJkNd5Dlm8_awZpftzW7KtqgNR6GURvuF6RfB210g/edit?usp=sharing
		Glide
				.with(context)
				.load(url)
				.asBitmap()
				.placeholder(new ColorDrawable(Color.BLUE))
				.error(new ColorDrawable(Color.RED))
				// overshoot a little so fitCenter uses width's ratio (see minPercentage)
				.override(Math.round(viewWidth), (int)Math.ceil(viewHeight))
				.fitCenter()
				// Cannot use .imageDecoder, only decoder; see bumptech/glide#708
				//.imageDecoder(new RegionStreamDecoder(context, rect))
				.decoder(new RegionImageVideoDecoder(context, rect))
				.cacheDecoder(new RegionFileDecoder(context, rect))
				// Cannot use RESULT cache; see bumptech/glide#707
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				.listener(new LoggingListener<String, Bitmap>(bind))
				.into(new BitmapImageViewTarget(holder.imageView) {
					@Override protected void setResource(Bitmap resource) {
						if (resource != null) {
							LayoutParams params = view.getLayoutParams();
							if (params.height != resource.getHeight()) {
								params.height = resource.getHeight();
							}
							view.setLayoutParams(params);
						}
						super.setResource(resource);
					}
				})
		;
	}

	static class ImageChunkViewHolder extends RecyclerView.ViewHolder {
		ImageView imageView;

		public ImageChunkViewHolder(View itemView) {
			super(itemView);
			imageView = (ImageView)itemView;
		}
	}
}

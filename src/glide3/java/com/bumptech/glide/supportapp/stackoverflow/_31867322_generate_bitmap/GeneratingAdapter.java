package com.bumptech.glide.supportapp.stackoverflow._31867322_generate_bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.ImageView;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapEncoder;
import com.bumptech.glide.load.resource.bitmap.StreamBitmapDecoder;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.bumptech.glide.load.resource.transcode.BitmapToGlideDrawableTranscoder;

import androidx.recyclerview.widget.RecyclerView;

class GeneratingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	// See https://docs.google.com/drawings/d/1KyOJkNd5Dlm8_awZpftzW7KtqgNR6GURvuF6RfB210g/edit?usp=sharing
	//                                  ModelType/A,    DataType/T,     ResourceType/Z, TranscodeType/R
	private final GenericRequestBuilder<GenerateParams, GenerateParams, Bitmap, GlideDrawable> generator;

	public GeneratingAdapter(final Context context) {
		generator = Glide // this part should be cleaner in Glide 4.0, but that's not released yet
		                  .with(context)
		                  .using(new GeneratePassthroughModelLoader(), GenerateParams.class)          // custom class
		                  .from(GenerateParams.class)
		                  .as(Bitmap.class)
		                  .transcode(new BitmapToGlideDrawableTranscoder(context), GlideDrawable.class)     // builtin
		                  .decoder(new GenerateBitmapResourceDecoder(context))                        // custom class
		                  .encoder(new BitmapEncoder(Bitmap.CompressFormat.PNG, 0/*ignored for lossless*/)) // builtin
		                  .cacheDecoder(new FileToStreamDecoder<Bitmap>(new StreamBitmapDecoder(context)))  // builtin
		//.placeholder(new ColorDrawable(Color.YELLOW)) // you can pre-set placeholder and error
		//.error(new ColorDrawable(Color.RED))          // so it's easier when binding
		//.diskCacheStrategy(DiskCacheStrategy.NONE)    // only for debugging to always regenerate
		//.skipMemoryCache(true)                        // only for debugging to always regenerate
		;
	}
	@Override public int getItemCount() {
		return 1000;
	}

	private final float[] colorCache = new float[] {0, 1.0f, 0.5f};
	private final float[] bgCache = new float[] {0, 0.5f, 1.0f};
	@Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		colorCache[0] = bgCache[0] = (position * 15) % 360; // just to have a fancy example :)
		GenerateParams params = new GenerateParams(
				// omit position to see Glide caching in action (every 24th item / 12th row is the same)
				"android text"/* + " #" + position*/,
				Color.HSVToColor(colorCache),
				Color.HSVToColor(bgCache)
		);
		generator/*.clone() in case you see weird behavior*/.load(params).into((ImageView)holder.itemView);
	}

	@Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		ImageView view = new ImageView(parent.getContext());
		int height = parent.getContext().getResources().getDisplayMetrics().heightPixels / 3;
		view.setLayoutParams(new RecyclerView.LayoutParams(MATCH_PARENT, height));
		view.setAdjustViewBounds(true);
		view.setScaleType(ImageView.ScaleType.FIT_CENTER);
		return new RecyclerView.ViewHolder(view) {
		};
	}
}

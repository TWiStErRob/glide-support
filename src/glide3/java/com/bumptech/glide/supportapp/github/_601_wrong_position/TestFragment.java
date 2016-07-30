package com.bumptech.glide.supportapp.github._601_wrong_position;

import java.util.Arrays;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;

import com.bumptech.glide.*;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.supportapp.*;
import com.bumptech.glide.supportapp.utils.*;

/**
 * The way I could reproduce the issue: scroll to the bottom of the list,
 * and then scroll the bottom 3-5 items out of the view and back again a few times.
 * After the 2nd time it usually shows ePIX instead of Cinemax.
 */
public class TestFragment extends GlideRecyclerFragment {
	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		listView.setAdapter(new SimpleUrlAdapter(Glide.with(this), Arrays.asList(
				"https://s.yimg.com/pe/vguide/netflix.png",
				"https://s.yimg.com/pe/vguide/hulu.png",
				"https://s.yimg.com/pe/vguide/amazon.png",
				"https://s.yimg.com/pe/vguide/hbo.png",
				"https://s.yimg.com/pe/vguide/showtime.png",
				"https://s.yimg.com/pe/vguide/youtube.png",
				"https://s.yimg.com/pe/vguide/fox.png",
				"https://s.yimg.com/pe/vguide/cbs.png",
				"https://s.yimg.com/pe/vguide/nbc.png",
				"https://s.yimg.com/pe/vguide/abc.png",
				"https://s.yimg.com/pe/vguide/tbs.png",
				"https://s.yimg.com/pe/vguide/the-cw.png",
				"https://s.yimg.com/pe/vguide/crackle.png",
				"https://s.yimg.com/pe/vguide/a-e.png",
				"https://s.yimg.com/pe/vguide/abc-family.png",
				"https://s.yimg.com/pe/vguide/amc.png",
				"https://s.yimg.com/pe/vguide/cartoon-network.png",
				"https://s.yimg.com/pe/vguide/adult-swim.png",
				"https://s.yimg.com/pe/vguide/comedy-central.png",
				"https://s.yimg.com/pe/vguide/disney.png",
				"https://s.yimg.com/pe/vguide/lifetime.png",
				"https://s.yimg.com/pe/vguide/mtv.png",
				"https://s.yimg.com/pe/vguide/nick-com.png",
				"https://s.yimg.com/pe/vguide/pbs.png",
				"https://s.yimg.com/pe/vguide/bravo.png",
				"https://s.yimg.com/pe/vguide/fx.png",
				"https://s.yimg.com/pe/vguide/history.png",
				"https://s.yimg.com/pe/vguide/tnt.png",
				"https://s.yimg.com/pe/vguide/usa-network.png",
				"https://s.yimg.com/pe/vguide/vh-1.png",
				"https://s.yimg.com/pe/vguide/spike.png",
				"https://s.yimg.com/pe/vguide/bet.png",
				"https://s.yimg.com/pe/vguide/we-tv.png",
				"https://s.yimg.com/pe/vguide/food-network.png",
				"https://s.yimg.com/pe/vguide/epix.png",
				"https://s.yimg.com/pe/vguide/cinemax.png",
				"https://s.yimg.com/pe/vguide/xfinity.png"
		)) {
			@Override protected View onCreateView(LayoutInflater inflater, ViewGroup parent, int viewType) {
				return inflater.inflate(R.layout.github_601_item, parent, false);
			}
			int tempPosition = -1;
			@Override public void onBindViewHolder(
					SimpleViewHolder holder, @SuppressLint("RecyclerView") int position) {
				tempPosition = position;
				super.onBindViewHolder(holder, position);
			}
			@Override protected void load(Context context, RequestManager glide, String url, ImageView imageView)
					throws Exception {
				final String row = "pos" + tempPosition;
				glide
						.load(url)
						.asBitmap()
						.fitCenter()
						.listener(new LoggingListener<String, Bitmap>(Log.DEBUG, row))
						.into(new LoggingTarget<>(row, Log.VERBOSE, new BitmapImageViewTarget(imageView)))
				;
			}
		});
	}
}

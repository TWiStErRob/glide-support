package com.bumptech.glide.supportapp.github._1094_gif_disposal;

import java.util.Arrays;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.*;
import android.widget.ImageView;

import com.bumptech.glide.*;
import com.bumptech.glide.supportapp.*;
import com.bumptech.glide.supportapp.utils.LoggingListener;

/**
 * @see <a href="http://www.imagemagick.org/Usage/anim_basics/">Source Images</a>
 */
public class TestFragment extends GlideRecyclerFragment {
	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		listView.setAdapter(new SimpleUrlAdapter(Glide.with(this), Arrays.asList(
				"http://www.imagemagick.org/Usage/anim_basics/animation.gif#4 icons on a blue square",
				"http://www.imagemagick.org/Usage/anim_basics/rose_sparkle.gif#3 different star fields on a rose",
				"http://www.imagemagick.org/Usage/anim_basics/anim_none.gif#4 icons on transparent",
				"http://www.imagemagick.org/Usage/anim_basics/canvas_none.gif#4 icons on a blue circle",
				"http://www.imagemagick.org/Usage/anim_basics/canvas_prev.gif#4 icons alone on a blue circle",
				"http://www.imagemagick.org/Usage/anim_basics/anim_bgnd.gif#4 icons on transparent",
				"http://www.imagemagick.org/Usage/anim_basics/canvas_bgnd.gif#4 icons alone on a blue circle, leaving transparent behind",
				"http://www.imagemagick.org/Usage/anim_basics/script_k.gif",
				"http://www.imagemagick.org/Usage/anim_basics/deconstruct_erase.gif#4 icons on a blue circle, leaving blocky transparent behind",
				"http://www.imagemagick.org/Usage/anim_basics/smoke_skull_anim.gif#skull appear and fade out in smoke",
				"http://www.imagemagick.org/Usage/anim_basics/dl_world_anim.gif#download the world",
				"http://www.imagemagick.org/Usage/anim_basics/bunny_anim.gif#4 frame running bunny",
				"http://www.imagemagick.org/Usage/anim_basics/bunny_on_grass.gif#4 frame running bunny on grass, blink",
				"http://www.imagemagick.org/Usage/anim_basics/bunny_on_grass2.gif#offset 4 frame running bunny on grass, blink",
				"http://www.imagemagick.org/Usage/anim_basics/bunny_bgnd.gif#4 frame running bunny on grass"
		)) {
			@Override public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
				SimpleViewHolder holder = super.onCreateViewHolder(parent, viewType);
				holder.imageView.setBackgroundResource(R.drawable.glide);
				return holder;
			}
			@Override protected void load(Context context, RequestManager glide, String url, ImageView imageView)
					throws Exception {
				glide
						.load(url)
						.listener(new LoggingListener<Drawable>())
						.into(imageView);
			}
		});
	}
}

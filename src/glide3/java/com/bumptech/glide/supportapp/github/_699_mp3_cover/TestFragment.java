package com.bumptech.glide.supportapp.github._699_mp3_cover;

import java.io.File;

import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.supportapp.GlideImageFragment;
import com.bumptech.glide.supportapp.R;

public class TestFragment extends GlideImageFragment {
	@Override protected void load(Context context) throws Exception {
		@SuppressWarnings("deprecation") // Historical code.
		File music = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
		String filePath = new File(music, "Maid with the Flaxen Hair.mp3").getAbsolutePath();
		// create a list of AudioCover objects and use those in the adapter,
		// so signature can be cached at least while scrolling the list
		AudioCover model = new AudioCover(filePath); // this involves I/O

		// at binding
		Glide
				.with(context)
				.load(model)
				.signature(model.signature)
				.error(R.drawable.glide_error)
				.into(imageView);
	}
}

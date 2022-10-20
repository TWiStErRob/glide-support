package com.bumptech.glide.supportapp.github._80_save_bitmap;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.supportapp.GlideImageFragment;

public class TestFragment extends GlideImageFragment {
	@Override protected void load(Context context) throws Exception {
		Glide
				.with(context)
				.load("image_url")
				.asBitmap()
				.toBytes(Bitmap.CompressFormat.JPEG, 100)
				// .centerCrop().override(2000, 2000).atMost() // optional extras
				.into(new SimpleTarget<byte[]>() {
					@Override public void onResourceReady(final byte[] resource,
							GlideAnimation<? super byte[]> glideAnimation) {
						new AsyncTask<Void, Void, Void>() {
							@Override protected Void doInBackground(Void... params) {
								File sdcard = Environment.getExternalStorageDirectory();
								File file = new File(sdcard + "/YourDir/imageName.jpg");
								File dir = file.getParentFile();
								try {
									if (!dir.mkdirs() && (!dir.exists() || !dir.isDirectory())) {
										throw new IOException("Cannot ensure parent directory for file " + file);
									}
									BufferedOutputStream s = new BufferedOutputStream(new FileOutputStream(file));
									s.write(resource);
									s.flush();
									s.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
								return null;
							}
						}.execute();
					}
				})
		;
	}
}

package com.bumptech.glide.supportapp.github._466_big_images;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.supportapp.GlideImageFragment;
import com.bumptech.glide.supportapp.utils.LoggingListener;

public class TestFragment extends GlideImageFragment {
	@SuppressWarnings("deprecation") // Historical code.
	private final File folder = Environment.getExternalStorageDirectory();
	// download https://drive.google.com/file/d/0B6kFDyLnqV5FXzBieGpoYUxET1k/view?usp=sharing as 1..5.jpg

	@Override protected void load(final Context context) {
		thread("1.jpg");
		thread("1.jpg");
		thread("1.jpg");
		thread("1.jpg");
		thread("1.jpg");
//		glide("1.jpg");
//		glide("2.jpg");
//		glide("3.jpg");
//		glide("4.jpg");
//		glide("5.jpg");
	}
	private void glide(String file) {
		Glide
				.with(this)
				.load(new File(folder, file))
				.asBitmap() // cleaner to debug
				.diskCacheStrategy(DiskCacheStrategy.NONE) // force reload
				.skipMemoryCache(true) // force reload
				.listener(new LoggingListener<File, Bitmap>())
				.override(100, 100) // behave like an adapter (same size for each item)
				.preload()
		;
	}
	private void thread(final String file) {
		new Thread("Thr" + file) {
			@Override public void run() {
				try {
					BitmapFactory.Options opts = new BitmapFactory.Options();
					opts.inSampleSize = 16;
					Bitmap bitmap = BitmapFactory.decodeFile(folder + "/" + file, opts);
					Log.d("BitmapFactory", file + " SUCCESS: " + bitmap);
				} catch (Throwable ex) {
					Log.d("BitmapFactory", file + " FAILED", ex);
				}
			}
		}.start();
	}
}

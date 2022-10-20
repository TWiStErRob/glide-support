package com.bumptech.glide.supportapp.github._700_tall_image;

import java.io.File;
import java.util.concurrent.ExecutionException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Point;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.supportapp.GlideRecyclerFragment;

import androidx.annotation.Nullable;

public class TestFragment extends GlideRecyclerFragment {
	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		download();
	}

	@SuppressWarnings("deprecation") // Historical code.
	private void download() {
		new android.os.AsyncTask<Void, Void, Point>() {
			String url = "http://imgfave-herokuapp-com.global.ssl.fastly.net/image_cache/142083463797243_tall.jpg";
			//String url = "https://upload.wikimedia.org/wikipedia/commons/a/a3/Berliner_Fernsehturm,_Sicht_vom_Neptunbrunnen_-_Berlin_Mitte.jpg";
			@Override protected Point doInBackground(Void... params) {
				try {
					File image = Glide
							.with(TestFragment.this)
							.load(url)
							.downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
							.get();
					Options opts = new Options();
					opts.inJustDecodeBounds = true;
					BitmapFactory.decodeFile(image.getAbsolutePath(), opts);
					return new Point(opts.outWidth, opts.outHeight);
				} catch (InterruptedException | ExecutionException ignored) {
					return null;
				}
			}
			@Override protected void onPostExecute(Point imageSize) {
				if (imageSize != null) {
					listView.setAdapter(new ImageChunkAdapter(getScreenSize(), url, imageSize));
				}
			}
		}.execute();
	}

	@SuppressLint("ObsoleteSdkInt") // keep it in case someone copies this function for < 14
	@SuppressWarnings("deprecation")
	private Point getScreenSize() {
		WindowManager window = (WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE);
		Display display = window.getDefaultDisplay();
		Point screen = new Point();
		if (VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB_MR2) {
			display.getSize(screen);
		} else {
			screen.set(display.getWidth(), display.getHeight());
		}
		return screen;
	}
}

package com.bumptech.glide.supportapp.utils;

import java.io.File;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.bumptech.glide.request.FutureTarget;

public class Downloader extends AsyncTask<FutureTarget<File>, Void, File> {
	private final String targetName;
	public Downloader(String targetName) {
		this.targetName = targetName;
	}

	@SafeVarargs
	@Override protected final File doInBackground(FutureTarget<File>... params) {
		try {
			File file = params[0].get();
			@SuppressWarnings("deprecation") // Historical code.
			File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
			File result = new File(dir, targetName);
			Utils.copy(file, result);
			return result;
		} catch (Exception e) {
			return null;
		}
	}
	@Override protected void onPostExecute(File file) {
		Log.wtf("APP", "File downloaded: " + file);
	}
}

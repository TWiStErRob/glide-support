package com.bumptech.glide.supportapp.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class ClearCachesTask extends AsyncTask<Void, Void, Void> {
	private final Context context;
	private final boolean clearMemory;
	private final boolean clearDisk;

	public ClearCachesTask(Context context, boolean clearMemory, boolean clearDisk) {
		this.context = context.getApplicationContext();
		this.clearMemory = clearMemory;
		this.clearDisk = clearDisk;
	}

	@Override protected void onPreExecute() {
		if (clearMemory) {
			Log.i("GLIDE", "Clearing memory cache");
			Glide.get(context).clearMemory();
			Log.i("GLIDE", "Clearing memory cache finished");
		}
	}

	@Override protected Void doInBackground(Void[] params) {
		if (clearDisk) {
			Log.i("GLIDE", "Clearing disk cache");
			Glide.get(context).clearDiskCache();
			Log.i("GLIDE", "Clearing disk cache finished");
		}
		return null;
	}

	@Override protected void onPostExecute(Void result) {
		Toast.makeText(context, getClearMessage() + " cleared.", Toast.LENGTH_SHORT).show();
	}

	private String getClearMessage() {
		if (clearMemory && clearDisk) {
			return "Caches";
		} else if (clearMemory) {
			return "Memory Cache";
		} else if (clearDisk) {
			return "Disk Cache";
		} else {
			return "Nothing";
		}
	}
}

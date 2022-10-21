package com.bumptech.glide.supportapp.github._961_downloadonly;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.TimeUnit;

import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;

@SuppressWarnings("deprecation") // Historical code.
class Downloader extends android.os.AsyncTask<String, String, Downloader.Result> {
	private static final String TAG = "Downloader";

	private final RequestManager glide;
	public Downloader(RequestManager glide) {
		this.glide = glide;
	}

	@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
	@Override protected Result doInBackground(String... params) {
		@SuppressWarnings({"unchecked", "rawtypes"})
		FutureTarget<File>[] requests = new FutureTarget[params.length];
		// fire everything into Glide queue
		for (int i = 0; i < params.length; i++) {
			if (isCancelled()) {
				break;
			}
			requests[i] = glide
					.load(params[i])
					.downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
			;
		}
		// wait for each item
		Result result = new Result();
		for (int i = 0; i < params.length; i++) {
			if (isCancelled()) {
				for (int j = i; j < params.length; j++) {
					if (requests[i] != null) {
						Glide.clear(requests[i]);
					}
					result.failures.put(params[j], new CancellationException());
				}
				break;
			}
			try {
				File file = requests[i].get(10, TimeUnit.SECONDS);
				result.success.put(params[i], file);
			} catch (Exception e) {
				result.failures.put(params[i], e);
			} finally {
				Glide.clear(requests[i]);
			}
			publishProgress(params[i]);
		}
		return result;
	}

	@Override protected void onProgressUpdate(String... values) {
		for (String url : values) {
			Log.v(TAG, "Finished " + url);
		}
	}

	@Override protected void onPostExecute(Result result) {
		Log.i(TAG, String.format(Locale.ROOT, "Downloaded %d files, %d failed.",
				result.success.size(), result.failures.size()));
	}

	static class Result {
		final Map<String, File> success = new HashMap<>();
		final Map<String, Exception> failures = new HashMap<>();
	}
}

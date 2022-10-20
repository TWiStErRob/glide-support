package com.bumptech.glide.supportapp.utils;

import android.content.Context;
import android.util.Log;

import androidx.loader.content.AsyncTaskLoader;

public abstract class UsefulAsyncTaskLoader<T> extends AsyncTaskLoader<T> {
	private static final String TAG = "AsyncTaskLoader";
	private T mData;

	public UsefulAsyncTaskLoader(final Context context) {
		super(context);
	}

	@Override public void deliverResult(final T data) {
		Log.v(TAG, "deliverResult");
		if (isReset()) {
			// An async query came in while the loader is stopped.  We don't need the result.
			if (data != null) {
				onReleaseResources(data);
			}
		}
		T oldData = mData;
		mData = data;

		if (isStarted()) {
			// If the Loader is currently started, we can immediately
			// deliver its results.
			super.deliverResult(data);
		}

		// At this point we can release the resources associated with
		// 'oldData' if needed; now that the new result is delivered we
		// know that it is no longer in use.
		if (oldData != null) {
			onReleaseResources(oldData);
		}
	}

	@Override protected void onStartLoading() {
		Log.v(TAG, "onStartLoading");
		super.onStartLoading();
		if (mData != null) {
			// If we currently have a result available, deliver it
			// immediately.
			deliverResult(mData);
		}
		// check if config changed
		//boolean configChange = mLastConfig.applyNewConfig(getContext().getResources());

		if (takeContentChanged() || mData == null) { // || configChange) {
			// If the data has changed since the last time it was loaded
			// or is not currently available, start a load.
			forceLoad();
		}
	}

	@Override protected void onStopLoading() {
		Log.v(TAG, "onStopLoading");
		// Attempt to cancel the current load task if possible.
		cancelLoad();
	}

	@Override public void onCanceled(T data) {
		Log.v(TAG, "onCanceled:" + data);
		super.onCanceled(data);
		// At this point we can release the resources associated with 'data' if needed.
		onReleaseResources(data);
	}

	@Override protected void onReset() {
		Log.v(TAG, "onReset");
		super.onReset();
		// Ensure the loader is stopped
		onStopLoading();
		// At this point we can release the resources associated with 'data' if needed.
		if (mData != null) {
			onReleaseResources(mData);
			mData = null;
		}
	}

	protected void onReleaseResources(T data) {
		Log.v(TAG, "onReleaseResources");
		//  nothing to do.
	}
}

package com.bumptech.glide.supportapp;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;

public class BaseFragment extends Fragment {
	@SuppressWarnings("deprecation")
	@Override public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.d("SYS", "Starting " + getClass().getName() + " #" + getId() + ": " + this);
	}
	@Override public void onDetach() {
		Log.d("SYS", "Stopping " + getClass().getName() + " #" + getId() + ": " + this);
		super.onDetach();
	}
}

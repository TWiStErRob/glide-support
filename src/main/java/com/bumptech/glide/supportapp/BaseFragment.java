package com.bumptech.glide.supportapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

public class BaseFragment extends Fragment {
	@Override public void onAttach(Context context) {
		super.onAttach(context);
		Log.d("SYS", "Starting " + getClass().getName() + " #" + getId() + ": " + this);
	}

	@Override public void onDetach() {
		Log.d("SYS", "Stopping " + getClass().getName() + " #" + getId() + ": " + this);
		super.onDetach();
	}
}

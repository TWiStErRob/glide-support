package com.bumptech.glide.supportapp.issue;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ViewGroup.*;
import android.widget.FrameLayout;

import com.bumptech.glide.supportapp.GlideSupportActivity;
import com.bumptech.glide.supportapp.random.__quicky.QuickFragment;
import com.bumptech.glide.supportapp.utils.Utils;

public class IssueFragmentActivity extends GlideSupportActivity {
	public static final String CONTENT_FRAGMENT = "contentFragment";

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		FrameLayout root = new FrameLayout(this);
		root.setId(android.R.id.content);
		root.setLayoutParams(new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		setContentView(root);

		String fragmentClass = getFragmentClass();
		if (savedInstanceState == null) {
			Log.d("SYS", "Creating fragment: " + fragmentClass);
			Fragment fragment = Fragment.instantiate(this, fragmentClass);
			getSupportFragmentManager()
					.beginTransaction()
					.add(android.R.id.content, fragment)
					.commit()
			;
			setTitle(fragment);
		} else {
			setTitle(getSupportFragmentManager().findFragmentById(android.R.id.content));
		}
	}
	
	private void setTitle(Fragment fragment) {
		if (fragment == null) return;
		String name = fragment.getClass().getPackage().getName();
		int lastDot = name.lastIndexOf('.');
		setTitle(name.substring(lastDot + 1));
	}

	private String getFragmentClass() {
		String fragmentClass = QuickFragment.class.getName();
		if (getIntent().getExtras().containsKey(CONTENT_FRAGMENT)) {
			fragmentClass = getIntent().getStringExtra(CONTENT_FRAGMENT);
			Log.i("SYS", "Using " + fragmentClass + " from intent.");
		} else {
			String metadataValue = Utils.getMetadataValue(this, this.getComponentName(), CONTENT_FRAGMENT);
			if (metadataValue != null) {
				fragmentClass = metadataValue;
			}
		}
		return fragmentClass;
	}
}

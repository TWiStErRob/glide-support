package com.bumptech.glide.supportapp.issue;

import android.content.pm.*;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ViewGroup.*;
import android.widget.FrameLayout;

import com.bumptech.glide.supportapp.*;

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
			String name = fragment.getClass().getPackage().getName();
			int lastDot = name.lastIndexOf('.');
			setTitle(name.substring(lastDot + 1));
		}
	}

	private String getFragmentClass() {
		String fragmentClass = QuickFragment.class.getName();
		if (getIntent().getExtras().containsKey(CONTENT_FRAGMENT)) {
			fragmentClass = getIntent().getStringExtra(CONTENT_FRAGMENT);
			Log.i("SYS", "Using " + fragmentClass + " from intent.");
		} else {
			try {
				ActivityInfo ai = getPackageManager().getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
				if (ai.metaData != null) {
					String clazz = ai.metaData.getString(CONTENT_FRAGMENT);
					if (clazz != null) {
						fragmentClass = clazz;
						Log.i("SYS", "Using " + fragmentClass + " from metadata.");
					} else {
						Log.w("SYS",
								IssueFragmentActivity.class.getName() + " doesn't have a contentFragment metadata.");
					}
				} else {
					Log.w("SYS", IssueFragmentActivity.class.getName() + " doesn't have metadata.");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return fragmentClass;
	}
}

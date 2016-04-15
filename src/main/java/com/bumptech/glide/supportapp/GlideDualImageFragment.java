package com.bumptech.glide.supportapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

public abstract class GlideDualImageFragment extends GlideBaseImageFragment {
	protected static final String LI = "<br>&nbsp;&nbsp;*&nbsp;";

	protected ImageView imageView1;
	protected ImageView imageView2;
	private TextView usage;

	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override public @Nullable View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.base_image_dual, container, false);
	}

	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		usage = (TextView)view.findViewById(android.R.id.text1);
		imageView1 = (ImageView)view.findViewById(android.R.id.icon1);
		imageView2 = (ImageView)view.findViewById(android.R.id.icon2);
		imageView1.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				load1();
			}
		});
		imageView2.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				load2();
			}
		});
	}

	@Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		MenuItem clearImage1 =
				menu.add(0, 9, 0, "Clear image 1").setIcon(android.R.drawable.ic_menu_close_clear_cancel);
		MenuItem clearImage2 =
				menu.add(0, 10, 0, "Clear image 2").setIcon(android.R.drawable.ic_menu_close_clear_cancel);
		MenuItemCompat.setShowAsAction(clearImage1, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
		MenuItemCompat.setShowAsAction(clearImage2, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
	}

	@Override public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case 9:
				Log.i("GLIDE", "Clearing target 1 " + imageView1);
				clear(imageView1);
				Log.i("GLIDE", "Clearing target 1 " + imageView1 + " finished");
				Toast.makeText(getContext(), "Target 1 cleared", Toast.LENGTH_SHORT).show();
				return true;
			case 10:
				Log.i("GLIDE", "Clearing target 2 " + imageView2);
				clear(imageView2);
				Log.i("GLIDE", "Clearing target 2 " + imageView2 + " finished");
				Toast.makeText(getContext(), "Target 2 cleared", Toast.LENGTH_SHORT).show();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Call from {@link #onViewCreated(View, Bundle)}.
	 * Refer to images as first and second to preserve generality (e.g. landscape may be left and right).
	 * Supported tags: {@code <a><b><big><blockquote><br><cite><dfn><div><em><font color face><i><img src><p><small><strong><sub><sup><tt><u>
	 * @see #LI
	 */
	protected final void setUsage(String htmlText) {
		usage.setText(Html.fromHtml(htmlText));
		usage.setMovementMethod(LinkMovementMethod.getInstance());
	}

	private void load1() {
		Log.i("GLIDE", "Loading #1");
		try {
			load1(getContext(), imageView1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.i("GLIDE", "Loaded #1");
	}

	private void load2() {
		Log.i("GLIDE", "Loading #2");
		try {
			load2(getContext(), imageView2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.i("GLIDE", "Loaded #2");
	}

	@Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		load1();
		load2();
	}

	protected abstract void load1(Context context, ImageView imageView) throws Exception;
	protected abstract void load2(Context context, ImageView imageView) throws Exception;
}

package com.bumptech.glide.supportapp;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;

public abstract class GlideDualImageFragment extends GlideBaseImageFragment {
	protected static final String LI = "<br>&nbsp;&nbsp;*&nbsp;";

	protected ImageView imageView1;
	protected ImageView imageView2;
	private TextView usage;

	@Override public @Nullable View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.base_image_dual, container, false);
	}

	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		requireActivity().addMenuProvider(new DualImageMenuProvider(), getViewLifecycleOwner());
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
		load1();
		load2();
	}

	/**
	 * Call from {@link #onViewCreated(View, Bundle)}.
	 * Refer to images as first and second to preserve generality (e.g. landscape may be left and right).
	 * Supported tags: {@code <a><b><big><blockquote><br><cite><dfn><div><em><font color face><i><img src><p><small><strong><sub><sup><tt><u>
	 *
	 * @see #LI
	 */
	@SuppressWarnings("deprecation")
	protected final void setUsage(String htmlText) {
		usage.setText(Html.fromHtml(htmlText));
		usage.setMovementMethod(LinkMovementMethod.getInstance());
	}

	private void load1() {
		Log.i("GLIDE", "Starting load #1");
		try {
			load1(getContext(), imageView1);
		} catch (Exception e) {
			Log.e("GLIDE", "Failed to start load #1", e);
			Toast.makeText(getContext(), "Load #1: " + e, Toast.LENGTH_SHORT).show();
		}
		Log.i("GLIDE", "Load #1 started");
	}

	private void load2() {
		Log.i("GLIDE", "Starting load #2");
		try {
			load2(getContext(), imageView2);
		} catch (Exception e) {
			Log.e("GLIDE", "Failed to start load #2", e);
			Toast.makeText(getContext(), "Load #2: " + e, Toast.LENGTH_SHORT).show();
		}
		Log.i("GLIDE", "Load #2 started");
	}

	protected abstract void load1(Context context, ImageView imageView) throws Exception;
	protected abstract void load2(Context context, ImageView imageView) throws Exception;

	private class DualImageMenuProvider implements MenuProvider {

		@Override public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
			MenuItem clearImage1 = menu.add(0, 9, 0, "Clear image 1")
			                           .setIcon(android.R.drawable.ic_menu_close_clear_cancel);
			MenuItem clearImage2 = menu.add(0, 10, 0, "Clear image 2")
			                           .setIcon(android.R.drawable.ic_menu_close_clear_cancel);
			clearImage1.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
			clearImage2.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		}

		@Override public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
			switch (menuItem.getItemId()) {
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
					return false;
			}
		}
	}
}

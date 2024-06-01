package com.bumptech.glide.supportapp;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import static com.bumptech.glide.supportapp.GlideBaseActivity.createMenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;

public abstract class GlideBaseImageActivity extends GlideBaseActivity {
	protected void clear(ImageView imageView) {
		Glide.with(this).clear(imageView);
	}

	@Override protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addMenuProvider(new BaseImageMenuProvider(this));
	}
}

class BaseImageMenuProvider implements MenuProvider {

	private final @NonNull Context context;

	BaseImageMenuProvider(@NonNull Context context) {
		this.context = context;
	}

	@Override public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
		createMenuItem(context, menu, 5, "Glide.arrayPool.clearMemory", "#8800ff", false);
	}

	@Override public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
		switch (menuItem.getItemId()) {
			case 5:
				Glide.get(context).getArrayPool().clearMemory();
				return true;
			default:
				return false;
		}
	}
}

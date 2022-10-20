package com.bumptech.glide.supportapp.github._1021_leak_back;

import java.util.Locale;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import com.bumptech.glide.supportapp.GlideRecyclerFragment;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TestFragment extends GlideRecyclerFragment {
	private static final String URL_TEMPLATE = "http://placehold.it/300x200/FFFFFF/000000&text=%d";
	@Override public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		class ViewHolder extends RecyclerView.ViewHolder {
			public ViewHolder(@NonNull View itemView) {
				super(itemView);
			}
		}
		listView.setAdapter(new RecyclerView.Adapter<ViewHolder>() {
			@Override public @NonNull ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
				ImageView view = new ImageView(parent.getContext());
				float height = TypedValue.applyDimension(COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
				view.setLayoutParams(new MarginLayoutParams(MATCH_PARENT, (int)height));
				return new ViewHolder(view);
			}
			@Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
				ImageView imageView = (ImageView)holder.itemView;
				String url = String.format(Locale.ROOT, URL_TEMPLATE, position);
				/*Glide
						.with(TestFragment.this)
						.load(url)
						.listener(new LoggingListener<String, GlideDrawable>())
						.into(imageView);*/
			}
			@Override public int getItemCount() {
				return 100;
			}
		});
	}
}

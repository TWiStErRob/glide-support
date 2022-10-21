package com.bumptech.glide.supportapp.github._662_detail_cache_hit;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.supportapp.R;
import com.bumptech.glide.supportapp.utils.LoggingListener;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListFragment extends Fragment {
	public interface Callback {
		void selected(int position, ListItem model);
	}

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// simulate    <androidx.recyclerview.widget.RecyclerView android:id="@android:id/list"
		// inflate of   android:layout_width="match_parent" android:layout_height="match_parent" />
		RecyclerView view = new RecyclerView(getActivity());
		view.setLayoutParams(
				new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		view.setId(android.R.id.list);
		return view;
	}

	@Override public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		RecyclerView list = (RecyclerView)view.findViewById(android.R.id.list);
		list.setLayoutManager(new GridLayoutManager(list.getContext(), 2));
		list.setAdapter(new ListAdapter((Callback)getActivity()));
	}

	static class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
		private final List<ListItem> items = Arrays.asList(
				new ListItem("first"),
				new ListItem("second"),
				new ListItem("third"),
				new ListItem("fourth"),
				new ListItem("fifth"),
				new ListItem("sixth"),
				new ListItem("seventh"),
				new ListItem("eight"),
				new ListItem("ninth"),
				new ListItem("tenth"),
				new ListItem("eleventh"),
				new ListItem("twelfth")
		);
		private final Callback cb;

		public ListAdapter(Callback cb) {
			this.cb = cb;
		}

		@Override public int getItemCount() {
			return items.size();
		}
		private ListItem getItem(int position) {
			return items.get(position);
		}
		@Override public long getItemId(int position) {
			return getItem(position).getText().hashCode();
		}
		@Override public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
			ImageView view = new ImageView(parent.getContext());
			int padding = dp(parent.getContext(), 4);
			int height = dp(parent.getContext(), 100);
			MarginLayoutParams params = new MarginLayoutParams(MATCH_PARENT, height);
			params.setMargins(padding, padding, padding, padding);
			view.setLayoutParams(params);
			view.setScaleType(ScaleType.CENTER_CROP);
			return new ViewHolder(view, cb);
		}
		private int dp(Context context, float size) {
			return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size,
					context.getResources().getDisplayMetrics());
		}

		@Override public void onBindViewHolder(final ViewHolder holder, int position) {
			holder.bind(getItem(position));
		}

		static class ViewHolder extends RecyclerView.ViewHolder {
			private ImageView image;
			private ListItem bound;

			public ViewHolder(View itemView, final Callback cb) {
				super(itemView);
				image = (ImageView)itemView;

				itemView.setOnClickListener(new OnClickListener() {
					@Override public void onClick(View v) {
						cb.selected(getBindingAdapterPosition(), bound);
					}
				});
			}

			private void bind(final ListItem model) {
				bound = model;
				Glide
						.with(itemView.getContext())
						.load(model.getLowUrl())
						.diskCacheStrategy(DiskCacheStrategy.SOURCE)
						.listener(new LoggingListener<String, GlideDrawable>())
						.into(new GlideDrawableImageViewTarget(image) {
							@Override public void onLoadFailed(Exception e, Drawable errorDrawable) {
								Glide
										.with(itemView.getContext())
										.load(model.getThumbUrl())
										.error(R.drawable.glide_error)
										.listener(new LoggingListener<String, GlideDrawable>())
										.into(image);
							}
						});
			}
		}
	}
}

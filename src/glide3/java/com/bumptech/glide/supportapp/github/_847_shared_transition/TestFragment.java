package com.bumptech.glide.supportapp.github._847_shared_transition;

import java.util.*;

import android.annotation.*;
import android.os.*;
import android.os.Build.VERSION_CODES;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.*;
import android.transition.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.supportapp.*;
import com.bumptech.glide.supportapp.utils.*;

public class TestFragment extends GlideRecyclerFragment {
	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		listView.setLayoutManager(new GridLayoutManager(null, 2));
		listView.setAdapter(new ItemAdapter());
	}

	private void clicked(ItemAdapter.ItemViewHolder vh) {
		DetailFragment fragment = new DetailFragmentWithPager();
		Bundle args = new Bundle();
		args.putSerializable(DetailFragment.ARG_ITEM, vh.item);
		fragment.setArguments(args);
		@SuppressLint("CommitTransaction")
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction()
				.replace(android.R.id.content, fragment)
				.addToBackStack("details");

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			TransitionInflater inf = TransitionInflater.from(getActivity());
			setExitTransition(inf.inflateTransition(android.R.transition.fade));
			fragment.setEnterTransition(inf.inflateTransition(android.R.transition.fade));
			fragment.setSharedElementEnterTransition(getTrans(inf));
			setSharedElementReturnTransition(inf.inflateTransition(R.transition.github_847));

			args.putString(DetailFragment.ARG_TRANS_NAME_IMAGE, vh.image.getTransitionName());
			transaction.addSharedElement(vh.image, vh.image.getTransitionName());

			args.putString(DetailFragment.ARG_TRANS_NAME_TEXT, vh.text.getTransitionName());
			transaction.addSharedElement(vh.text, vh.text.getTransitionName());
		}

		transaction.commit();
	}
	@TargetApi(VERSION_CODES.KITKAT)
	private TransitionSet getTrans(TransitionInflater inf) {
		TransitionSet set = (TransitionSet)inf.inflateTransition(R.transition.github_847);
		set.addListener(new LoggingTransitionListener("list")); // need to add a dummy listener
		// without this the listener in the detail fragment would not be called
		return set;
	}

	class ItemAdapter extends Adapter<ItemAdapter.ItemViewHolder> {
		List<Item> items = Arrays.asList(
				new Item(0xffff0000),
				new Item(0xff00ff00),
				new Item(0xff0000ff),
				new Item(0xffffff00),
				new Item(0xff00ffff),
				new Item(0xffff00ff)
		);
		@Override public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.github_847_item, parent, false);
			return new ItemViewHolder(view);
		}
		@Override public void onBindViewHolder(ItemViewHolder holder, int position) {
			holder.bind(items.get(position), position);
		}
		@Override public int getItemCount() {
			return items.size();
		}

		class ItemViewHolder extends ViewHolder {
			public final ImageView image;
			public final TextView text;
			public Item item;
			public ItemViewHolder(View view) {
				super(view);
				image = (ImageView)view.findViewById(R.id.image);
				text = (TextView)view.findViewById(R.id.text);
				view.setOnClickListener(new OnClickListener() {
					@Override public void onClick(View v) {
						clicked(ItemViewHolder.this);
					}
				});
			}
			void bind(Item item, int position) {
				this.item = item;
				Glide
						.with(TestFragment.this)
						.load(item.thumbUrl)
						.override(256, 256)
						.centerCrop()
						.listener(new LoggingListener<String, GlideDrawable>("adapter"))
						.into(image)
				;
				text.setText(item.color);

				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
					text.setTransitionName("transtext" + position);
					image.setTransitionName("transimage" + position);
				}
			}
		}
	}
}


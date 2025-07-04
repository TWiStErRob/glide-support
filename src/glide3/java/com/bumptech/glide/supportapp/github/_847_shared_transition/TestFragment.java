package com.bumptech.glide.supportapp.github._847_shared_transition;

import java.util.Arrays;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.supportapp.GlideRecyclerFragment;
import com.bumptech.glide.supportapp.R;
import com.bumptech.glide.supportapp.utils.LoggingListener;
import com.bumptech.glide.supportapp.utils.LoggingTransitionListener;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
		FragmentTransaction transaction = getParentFragmentManager()
				.beginTransaction()
				.replace(android.R.id.content, fragment)
				.addToBackStack("details");

		TransitionInflater inf = TransitionInflater.from(getActivity());
		setExitTransition(inf.inflateTransition(android.R.transition.fade));
		fragment.setEnterTransition(inf.inflateTransition(android.R.transition.fade));
		fragment.setSharedElementEnterTransition(getTrans(inf));
		setSharedElementReturnTransition(inf.inflateTransition(R.transition.github_847));

		args.putString(DetailFragment.ARG_TRANS_NAME_IMAGE, vh.image.getTransitionName());
		transaction.addSharedElement(vh.image, vh.image.getTransitionName());

		args.putString(DetailFragment.ARG_TRANS_NAME_TEXT, vh.text.getTransitionName());
		transaction.addSharedElement(vh.text, vh.text.getTransitionName());

		transaction.commit();
	}

	private TransitionSet getTrans(TransitionInflater inf) {
		TransitionSet set = (TransitionSet)inf.inflateTransition(R.transition.github_847);
		set.addListener(new LoggingTransitionListener("list")); // need to add a dummy listener
		// without this the listener in the detail fragment would not be called
		return set;
	}

	class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
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

		class ItemViewHolder extends RecyclerView.ViewHolder {
			public final ImageView image;
			public final TextView text;
			public Item item;
			public ItemViewHolder(View view) {
				super(view);
				image = view.findViewById(R.id.image);
				text = view.findViewById(R.id.text);
				view.setOnClickListener(v -> clicked(ItemViewHolder.this));
			}
			void bind(Item item, int position) {
				this.item = item;
				Glide
						.with(TestFragment.this)
						.load(item.thumbUrl)
						.override(256, 256)
						.centerCrop()
						.listener(new LoggingListener<>("adapter"))
						.into(image)
				;
				text.setText(item.color);

				text.setTransitionName("transtext" + position);
				image.setTransitionName("transimage" + position);
			}
		}
	}
}

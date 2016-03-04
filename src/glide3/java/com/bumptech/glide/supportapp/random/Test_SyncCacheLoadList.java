package com.bumptech.glide.supportapp.random;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.*;
import android.widget.*;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.supportapp.*;
import com.bumptech.glide.supportapp.utils.SyncLoadImageViewTarget;

/**
 * @see <a href="https://groups.google.com/d/msgid/glidelibrary/03644dfd-6a00-4441-b078-0edaf8bb76f4%40googlegroups.com">
 *     Load image only if present in memory cache</a>
 */
public class Test_SyncCacheLoadList extends GlideListFragment {
	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		listView.setAdapter(new SyncCacheLoadAdapter());
	}

	private class SyncCacheLoadAdapter extends BaseAdapter {
		String[] images = {
				"http://placehold.it/300x200/fe6f5e/000000&text=1",
				"http://placehold.it/300x200/fe6f5e/000000&text=2",
				"http://placehold.it/300x200/fe6f5e/000000&text=3",
				"http://placehold.it/300x200/fe6f5e/000000&text=4",
				"http://placehold.it/300x200/fe6f5e/000000&text=5",
				"http://placehold.it/300x200/fe6f5e/000000&text=6",
				"http://placehold.it/300x200/fe6f5e/000000&text=7",
				"http://placehold.it/300x200/fe6f5e/000000&text=8",
				"http://placehold.it/300x200/fe6f5e/000000&text=9"
		};
		@Override public int getCount() {
			return images.length;
		}
		@Override public String getItem(int position) {
			return images[position];
		}
		@Override public long getItemId(int position) {
			return images[position].hashCode();
		}
		@Override public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = recycle(convertView, parent);

			//.with(getActivity().getApplicationContext())
			//or
			//Glide.with(this).resumeRequests();
			// AND imageview size must not have match_parent
			SyncLoadImageViewTarget target = Glide
					.with(Test_SyncCacheLoadList.this)
					.load(getItem(position))
					.diskCacheStrategy(DiskCacheStrategy.ALL)
					.placeholder(R.drawable.glide_placeholder)
					.listener(new RequestListener<String, GlideDrawable>() {
						@Override public boolean onException(Exception e, String model,
								Target<GlideDrawable> target,
								boolean isFirstResource) {
							return false;
						}

						@Override public boolean onResourceReady(GlideDrawable resource, String model,
								Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
							Log.d("resource ready", "isFromMemoryCache: " + isFromMemoryCache);
							return false;
						}
					})
					.into(new SyncLoadImageViewTarget(imageView));

			Log.d("isLoaded", target.isLoaded() + "");
			if (!target.isLoaded()) {
				//Glide.clear(target); NOT CLEARING ANYMORE
			}

			return imageView;
		}
	}
}

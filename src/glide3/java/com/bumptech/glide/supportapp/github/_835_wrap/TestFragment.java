package com.bumptech.glide.supportapp.github._835_wrap;

import java.util.Arrays;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.*;
import android.widget.*;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.supportapp.*;
import com.bumptech.glide.supportapp.utils.LoggingListener;

public class TestFragment extends GlideListFragment {
	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		listView.setAdapter(
				new ArrayAdapter<String>(view.getContext(), R.layout.github_835_item, R.id.text, Arrays.asList(
						"https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Intel-logo.svg/2000px-Intel-logo.svg.png",
						"http://www.download-free-wallpaper.com/img85/nxxqigvaodaeivvvevms.png",
						"http://blogs.fasos.maastrichtuniversity.nl/logoimago/mpiersotte/wp-content/uploads/sites/139/2015/04/mixed-logo.jpg",
						"http://www.wired.com/wp-content/uploads/2015/09/google-logo.jpg",
						"http://wallpapercave.com/wp/M54Q1Bq.png",
						"https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Intel-logo.svg/2000px-Intel-logo.svg.png",
						"http://www.download-free-wallpaper.com/img85/nxxqigvaodaeivvvevms.png",
						"http://blogs.fasos.maastrichtuniversity.nl/logoimago/mpiersotte/wp-content/uploads/sites/139/2015/04/mixed-logo.jpg",
						"http://www.wired.com/wp-content/uploads/2015/09/google-logo.jpg",
						"http://wallpapercave.com/wp/M54Q1Bq.png",
						"https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Intel-logo.svg/2000px-Intel-logo.svg.png",
						"http://www.download-free-wallpaper.com/img85/nxxqigvaodaeivvvevms.png",
						"http://blogs.fasos.maastrichtuniversity.nl/logoimago/mpiersotte/wp-content/uploads/sites/139/2015/04/mixed-logo.jpg",
						"http://www.wired.com/wp-content/uploads/2015/09/google-logo.jpg",
						"http://wallpapercave.com/wp/M54Q1Bq.png",
						"https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Intel-logo.svg/2000px-Intel-logo.svg.png",
						"http://www.download-free-wallpaper.com/img85/nxxqigvaodaeivvvevms.png",
						"http://blogs.fasos.maastrichtuniversity.nl/logoimago/mpiersotte/wp-content/uploads/sites/139/2015/04/mixed-logo.jpg",
						"http://www.wired.com/wp-content/uploads/2015/09/google-logo.jpg",
						"http://wallpapercave.com/wp/M54Q1Bq.png",
						"https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Intel-logo.svg/2000px-Intel-logo.svg.png",
						"http://www.download-free-wallpaper.com/img85/nxxqigvaodaeivvvevms.png",
						"http://blogs.fasos.maastrichtuniversity.nl/logoimago/mpiersotte/wp-content/uploads/sites/139/2015/04/mixed-logo.jpg",
						"http://www.wired.com/wp-content/uploads/2015/09/google-logo.jpg",
						"http://wallpapercave.com/wp/M54Q1Bq.png",
						"https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Intel-logo.svg/2000px-Intel-logo.svg.png",
						"http://www.download-free-wallpaper.com/img85/nxxqigvaodaeivvvevms.png",
						"http://blogs.fasos.maastrichtuniversity.nl/logoimago/mpiersotte/wp-content/uploads/sites/139/2015/04/mixed-logo.jpg",
						"http://www.wired.com/wp-content/uploads/2015/09/google-logo.jpg",
						"http://wallpapercave.com/wp/M54Q1Bq.png",
						"https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Intel-logo.svg/2000px-Intel-logo.svg.png",
						"http://www.download-free-wallpaper.com/img85/nxxqigvaodaeivvvevms.png",
						"http://blogs.fasos.maastrichtuniversity.nl/logoimago/mpiersotte/wp-content/uploads/sites/139/2015/04/mixed-logo.jpg",
						"http://www.wired.com/wp-content/uploads/2015/09/google-logo.jpg",
						"http://wallpapercave.com/wp/M54Q1Bq.png",
						"https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Intel-logo.svg/2000px-Intel-logo.svg.png",
						"http://www.download-free-wallpaper.com/img85/nxxqigvaodaeivvvevms.png",
						"http://blogs.fasos.maastrichtuniversity.nl/logoimago/mpiersotte/wp-content/uploads/sites/139/2015/04/mixed-logo.jpg",
						"http://www.wired.com/wp-content/uploads/2015/09/google-logo.jpg",
						"http://wallpapercave.com/wp/M54Q1Bq.png"
				)) {
					@Override public View getView(int position, View convertView, ViewGroup parent) {
						convertView = super.getView(position, convertView, parent);
						ImageView image = (ImageView)convertView.findViewById(R.id.image);
						Glide
								.with(TestFragment.this)
								.load(getItem(position))
								.override(Target.SIZE_ORIGINAL, convertView.getLayoutParams().height)
								.listener(new LoggingListener<String, GlideDrawable>())
								.into(image);
						return convertView;
					}
				});
	}
}

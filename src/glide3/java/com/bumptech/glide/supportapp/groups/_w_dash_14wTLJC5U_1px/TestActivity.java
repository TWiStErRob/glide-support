package com.bumptech.glide.supportapp.groups._w_dash_14wTLJC5U_1px;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import com.bumptech.glide.Glide;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class TestActivity extends AppCompatActivity {
	@Override protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		FrameLayout layout = new FrameLayout(this);
		layout.setLayoutParams(new LayoutParams(MATCH_PARENT, MATCH_PARENT));
		ViewPager pager = new ViewPager(this);
		//pager.setId(View.generateViewId());
		pager.setLayoutParams(new LayoutParams(MATCH_PARENT, MATCH_PARENT));
		pager.setAdapter(new PagerAdapter() {
			@Override public int getCount() {
				return 1;
			}
			@Override public Object instantiateItem(ViewGroup container, int position) {
				ImageView image = new ImageView(container.getContext());
				image.setLayoutParams(new LayoutParams(MATCH_PARENT, MATCH_PARENT, Gravity.CENTER));
				image.setScaleType(ScaleType.FIT_CENTER);
				container.addView(image);

				Glide.with(image.getContext())
				     .load("http://www.gettyimages.co.uk/gi-resources/images/Homepage/Hero/UK/CMS_Creative_164657191_Kingfisher.jpg")
				     .into(image);

				return image;
			}
			@Override public boolean isViewFromObject(View view, Object object) {
				return view == object;
			}
		});

		layout.addView(pager);
		setContentView(layout);
	}
}

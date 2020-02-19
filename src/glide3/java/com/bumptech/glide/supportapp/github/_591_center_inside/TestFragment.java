package com.bumptech.glide.supportapp.github._591_center_inside;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.supportapp.GlidePagerFragment;
import com.squareup.picasso.*;

public class TestFragment extends GlidePagerFragment {
	@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		list.setAdapter(new PagerAdapter() {
			private final int GLIDE_DEFAULT_SMALL = 0;
			private final int GLIDE_DEFAULT_BIG = 1;
			private final int GLIDE_CENTER_INSIDE_SMALL = 2;
			private final int GLIDE_CENTER_INSIDE_BIG = 3;
			private final int PICASSO_DEFAULT_SMALL = 4;
			private final int PICASSO_DEFAULT_BIG = 5;
			private final int PICASSO_CENTER_INSIDE_SMALL = 6;
			private final int PICASSO_CENTER_INSIDE_BIG = 7;
			private static final String SMALL_IMAGE = "http://placehold.it/200x200/5DADE2/FFFFFF?text=200x200%0a";
			private static final String BIG_IMAGE = "http://placehold.it/2000x2000/2DCC70/FFFFFF?text=2000x2000%0a";
			@Override public CharSequence getPageTitle(int position) {
				return (position < 4? "Glide" : "Picasso")
						+ " "
						+ (position % 2 == 0? "small" : "big")
						+ (position % 4 >= 2? " centerInside" : "")
						;
			}
			@Override public int getCount() {
				return 8;
			}
			@Override public Object instantiateItem(ViewGroup container, final int position) {
				final ImageView image = createView(container);
				container.addView(image);

				switch (position) {
					case GLIDE_DEFAULT_SMALL:
						Glide.with(image.getContext())
						     .load(SMALL_IMAGE + "GS")
						     .listener(new MyRequestListener(position))
						     .into(image);
						break;
					case GLIDE_DEFAULT_BIG:
						Glide.with(image.getContext())
						     .load(BIG_IMAGE + "GS")
						     .listener(new MyRequestListener(position))
						     .into(image);
						break;
					case GLIDE_CENTER_INSIDE_SMALL:
						image.setScaleType(ScaleType.CENTER_INSIDE);
						Glide.with(image.getContext())
						     .load(SMALL_IMAGE + "GSC")
						     .dontTransform()
						     .listener(new MyRequestListener(position))
						     .into(image);
						break;
					case GLIDE_CENTER_INSIDE_BIG:
						image.setScaleType(ScaleType.CENTER_INSIDE);
						Glide.with(image.getContext())
						     .load(BIG_IMAGE + "GBC")
						     .dontTransform()
						     .listener(new MyRequestListener(position))
						     .into(image);
						break;
					case PICASSO_DEFAULT_SMALL:
						Picasso.get()
						       .load(SMALL_IMAGE + "PS")
						       .into(image, new MyCallback(image, position))
						;
						break;
					case PICASSO_DEFAULT_BIG:
						Picasso.get()
						       .load(BIG_IMAGE + "PB")
						       .into(image, new MyCallback(image, position))
						;
						break;
					case PICASSO_CENTER_INSIDE_SMALL:
						// java.lang.IllegalStateException: Center inside requires calling resize with positive width and height.
						image.setScaleType(ScaleType.CENTER_INSIDE);
						Picasso.get()
						       .load(SMALL_IMAGE + "PSC")
						       .resize(768, 768)
						       .centerInside()
						       .into(image, new MyCallback(image, position))
						;
						break;
					case PICASSO_CENTER_INSIDE_BIG:
						// java.lang.IllegalStateException: Center inside requires calling resize with positive width and height.
						image.setScaleType(ScaleType.CENTER_INSIDE);
						Picasso.get()
						       .load(BIG_IMAGE + "PBC")
						       .resize(768, 768)
						       .centerInside()
						       .into(image, new MyCallback(image, position))
						;
						break;
				}
				return image;
			}
			@Override public void destroyItem(ViewGroup container, int position, Object object) {
				container.removeView((View)object);
			}
			@Override public boolean isViewFromObject(View view, Object object) {
				return view == object;
			}

			class MyCallback implements Callback {
				private final ImageView image;
				private final int position;
				public MyCallback(ImageView image, int position) {
					this.image = image;
					this.position = position;
				}
				@Override public void onSuccess() {
					Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
					Log.wtf(getPageTitle(position).toString(),
							"Loaded " + bitmap.getWidth() + "x" + bitmap.getHeight());
				}
				@Override public void onError(Exception e) {

				}
			}

			class MyRequestListener implements RequestListener<String, GlideDrawable> {
				private final int position;
				public MyRequestListener(int position) {
					this.position = position;
				}
				@Override public boolean onException(Exception e, String model, Target<GlideDrawable> target,
						boolean isFirstResource) {
					return false;
				}
				@Override public boolean onResourceReady(GlideDrawable resource, String model,
						Target<GlideDrawable> target, boolean isFromMemoryCache,
						boolean isFirstResource) {
					Bitmap bitmap = ((GlideBitmapDrawable)resource).getBitmap();
					Log.wtf(position + " " + getPageTitle(position).toString(),
							"Loaded " + bitmap.getWidth() + "x" + bitmap.getHeight());
					return false;
				}
			}
		});
	}
}

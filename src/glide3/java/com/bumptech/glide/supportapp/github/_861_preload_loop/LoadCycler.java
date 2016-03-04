package com.bumptech.glide.supportapp.github._861_preload_loop;

import java.util.*;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.*;
import com.bumptech.glide.request.target.*;

public class LoadCycler<M, T extends Drawable> {
	private Iterable<M> models = Collections.emptyList();
	private Iterator<M> data;
	private GenericRequestBuilder<M, ?, ?, T> prev, curr, next;
	private final Target<? super T> target;
	private GlideAnimationFactory<Drawable> factory = new DrawableCrossFadeFactory<>();
	private boolean isLoading = false;

	public LoadCycler(GenericRequestBuilder<M, ?, ?, T> request, ImageView imageView) {
		this(request, new DrawableImageViewTarget(imageView));
	}
	public LoadCycler(GenericRequestBuilder<M, ?, ?, T> request, Target<? super T> target) {
		this.target = target;
		prev = copy(request);
		curr = copy(request);
		next = copy(request);
	}
	private GenericRequestBuilder<M, ?, ?, T> copy(GenericRequestBuilder<M, ?, ?, T> request) {
		return request.clone().dontAnimate().thumbnail(null).listener(null).load(null);
	}

	protected M getNextModel() {
		if (data == null || !data.hasNext()) {
			data = models.iterator();
		}
		return data.hasNext()? data.next() : null;
	}

	private void rotate() {
		GenericRequestBuilder<M, ?, ?, T> temp = prev;
		prev = curr;
		curr = next;
		next = temp;
	}

	public void startNext() {
		if (isLoading) {
			return; // prevent showing white when next is called too fast
			// the current load must have finished before going to the next one
			// this ensures that the current load will always be in memory cache so it can be used as thumbnail
		}
		rotate();
		prev.thumbnail(null).listener(null);
		next.load(getNextModel());
		@SuppressWarnings("unchecked") Target<T> target = (Target)this.target;
		isLoading = true;
		curr.thumbnail(prev).listener(animator).into(target);
	}

	public void setData(Iterable<M> models) {
		this.models = models != null? models : Collections.<M>emptyList();
		prev.load(null);
		curr.load(null);
		next.load(getNextModel());
	}

	public void setAnimation(GlideAnimationFactory<Drawable> factory) {
		this.factory = factory;
	}

	private final SizeReadyCallback preloader = new SizeReadyCallback() {
		@Override public void onSizeReady(int width, int height) {
			next.preload(width, height);
		}
	};

	private final RequestListener<M, T> animator = new RequestListener<M, T>() {
		@Override public boolean onException(Exception e, M model, Target<T> target, boolean isFirstResource) {
			return false;
		}
		@Override public boolean onResourceReady(T resource, M model, Target<T> target, boolean ignore, boolean thumb) {
			isLoading = false;
			target.getSize(preloader); // calls onSizeReady
			return factory != null && factory.build(false, thumb).animate(resource, (GlideAnimation.ViewAdapter)target);
		}
	};
}

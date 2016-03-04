package com.bumptech.glide.supportapp.utils;

import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class MultiRequestListener<A, B> implements RequestListener<A, B> {
	private final RequestListener<? super A, B>[] listeners;
	@SafeVarargs
	public MultiRequestListener(RequestListener<? super A, B>... listeners) {
		this.listeners = listeners;
	}
	public RequestListener<? super A, B>[] getListeners() {
		return listeners;
	}
	@Override public boolean onException(Exception e, A model, Target<B> target, boolean isFirstResource) {
		for (RequestListener<? super A, B> listener : listeners) {
			if (listener.onException(e, model, target, isFirstResource)) {
				return true;
			}
		}
		return false;
	}
	@Override public boolean onResourceReady(B resource, A model, Target<B> target, boolean isFromMemoryCache,
			boolean isFirstResource) {
		for (RequestListener<? super A, B> listener : listeners) {
			if (listener.onResourceReady(resource, model, target, isFromMemoryCache, isFirstResource)) {
				return true;
			}
		}
		return false;
	}
}

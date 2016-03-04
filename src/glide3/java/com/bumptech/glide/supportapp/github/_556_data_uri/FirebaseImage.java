package com.bumptech.glide.supportapp.github._556_data_uri;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import com.firebase.client.*;

class FirebaseImage {
	// final Firebase fb; // see FirebaseImageModelLoader ctor
	final String key;
	public FirebaseImage(String key) {
		this.key = key;
	}

	public String getDataUri(Firebase fb) throws InterruptedException, FirebaseException {
		return (String)syncGetValue(fb.child("images").child(key));
	}

	private static Object syncGetValue(Firebase image) throws InterruptedException, FirebaseException {
		final AtomicReference<Object> resultRef = new AtomicReference<>();
		final AtomicReference<FirebaseException> thrownRef = new AtomicReference<>();
		final CountDownLatch latch = new CountDownLatch(1);
		image.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override public void onDataChange(DataSnapshot snapshot) {
				resultRef.set(snapshot.getValue());
				latch.countDown();
			}
			@Override public void onCancelled(FirebaseError error) {
				thrownRef.set(error.toException());
				latch.countDown();
			}
		});
		latch.await();
		FirebaseException thrown = thrownRef.get();
		Object result = resultRef.get();
		if (thrown != null) {
			throw thrown;
		} else if (result == null) {
			throw new NullPointerException("No data returned from " + image);
		} else {
			return result;
		}
	}
}

package com.bumptech.glide.supportapp.github._556_data_uri;

import java.io.*;

import android.content.Context;
import android.util.Base64;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.*;
import com.bumptech.glide.load.model.stream.StreamModelLoader;
import com.firebase.client.Firebase;

// TODO read https://github.com/bumptech/glide/wiki/Downloading-custom-sizes-with-Glide
class FirebaseModelLoader implements StreamModelLoader<FirebaseImage> {
	private final Firebase fb;
	public FirebaseModelLoader(Firebase fb) {
		this.fb =
				fb; // TODO *alternatively* add a Firebase field to FirebaseImage and use that from model in DataFetcher
	}

	@Override public DataFetcher<InputStream> getResourceFetcher(final FirebaseImage model, int width, int height) {
		return new DataFetcher<InputStream>() {
			private Thread decodingThread;
			@Override public InputStream loadData(Priority priority) throws Exception {
				decodingThread = Thread.currentThread();
				// somehow make sure that fb is ready after authWithPassword is called
				String dataUri = model.getDataUri(fb);
				String base64 = dataUri.substring("data:image/jpeg;base64,".length());
				// depending on how you encoded it, the below is just a guess:
				// here the full base64 is decoded into bytes and the bytes will be parsed by Glide
				return new ByteArrayInputStream(Base64.decode(base64, Base64.NO_WRAP | Base64.URL_SAFE));
				// if you don't want to delay decoding you can use something like:
				// Base64InputStream (http://stackoverflow.com/a/19981216/253468)
				// here the base64 bytes are passed to Base64InputStream and that to Glide
				// so base64 decoding will be done later when Glide reads from the stream.
				//return new Base64InputStream(new ByteArrayInputStream(base64.getBytes("utf-8")), Base64.NO_WRAP | Base64.URL_SAFE);
			}
			@Override public String getId() {
				return model.key; // for cache
			}
			@Override public void cancel() {
				// don't do this if it's likely to display in the future (read doc of cancel)
				decodingThread.interrupt(); // should fly out from await()
			}
			@Override public void cleanup() {
				decodingThread = null;
			}
		};
	}

	public static class Factory implements ModelLoaderFactory<FirebaseImage, InputStream> {
		private final Firebase fb;
		public Factory(Firebase fb) {
			this.fb = fb;
		}
		@Override public ModelLoader<FirebaseImage, InputStream> build(Context context,
				GenericLoaderFactory factories) {
			return new FirebaseModelLoader(fb);
		}
		@Override public void teardown() {
			// only called if Glide.register is called with same first 2 args again
			//fb.onDisconnect().cancel(); // TODO or whatever you call to disconnect
		}
	}
}

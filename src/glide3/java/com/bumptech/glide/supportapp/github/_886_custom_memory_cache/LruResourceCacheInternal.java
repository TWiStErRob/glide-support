package com.bumptech.glide.supportapp.github._886_custom_memory_cache;

import android.util.Log;

import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.cache.MemoryCache;
import com.bumptech.glide.util.LruCache;

class LruResourceCacheInternal extends LruCache<Key, Resource<?>> implements MemoryCache {
	public LruResourceCacheInternal(int size) {
		super(size);
	}
	@Override public Resource<?> get(Key key) {
		Log.i("vipin_get", key.toString());
		return super.get(key);
	}

	@Override public Resource<?> put(Key key, Resource<?> item) {
		Log.i("vipin_put", key.toString());

		return super.put(key, item);
	}

	@Override public void setResourceRemovedListener(ResourceRemovedListener listener) {

	}
	@Override public void trimMemory(int level) {

	}
}

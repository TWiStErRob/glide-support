package com.bumptech.glide.supportapp.random.__quicky;

import android.content.Context;

import com.bumptech.glide.*;
import com.bumptech.glide.module.GlideModule;

public class QuickModule implements GlideModule {
	@Override public void applyOptions(Context context, GlideBuilder builder) {
//		builder.setResizeService(new FifoPriorityThreadPoolExecutor(4, UncaughtThrowableStrategy.THROW));
//		builder.setDiskCacheService(new FifoPriorityThreadPoolExecutor(2, UncaughtThrowableStrategy.THROW));
//		builder.setDiskCache(new InternalCacheDiskCacheFactory(context));
//		builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
	}
	@Override public void registerComponents(Context context, Glide glide) {
//		glide.register(GlideUrl.class, InputStream.class, new HttpUrlGlideUrlLoader.Factory());
//		OkHttpClient client = new OkHttpClient.Builder()
//				.connectTimeout(15, TimeUnit.SECONDS)
//				.readTimeout(15, TimeUnit.SECONDS)
//				.addNetworkInterceptor(new Interceptor() {
//					@Override public Response intercept(Chain chain) throws IOException {
//						Request oldRequest = chain.request();
//						Request.Builder newRequest = oldRequest.newBuilder();
//
//						Response oldResponse = chain.proceed(newRequest.build());
//
//						Response.Builder newResponse = oldResponse
//								.newBuilder()
//								.body(new okhttp3.internal.http.RealResponseBody(
//										oldResponse.headers(), oldResponse.body().source()));
//						return newResponse.build();
//					}
//				})
//				.build();
//		glide.register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(client));
	}
}

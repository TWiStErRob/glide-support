package com.bumptech.glide.supportapp.utils;

import java.io.*;

public class Utils {
	public static void copy(File source, File target) throws IOException {
		InputStream in = new FileInputStream(source);
		try {
			OutputStream out = new FileOutputStream(target);
			try {
				byte[] buffer = new byte[16 * 1024];
				int readCount;
				while ((readCount = in.read(buffer)) > 0) {
					out.write(buffer, 0, readCount);
				}
				out.flush();
			} finally {
				out.close();
			}
		} finally {
			in.close();
		}
	}
/*
	public static void setupVolley(Context context) {
		Glide.get(context).register(GlideUrl.class, InputStream.class, new VolleyUrlLoader.Factory(context));
	}
	public static void setupVolleyAdvanced(Context context) {
		RequestQueue requestQueue = setupVolleyCookies(context);
		Glide.get(context).register(GlideUrl.class, InputStream.class, new VolleyUrlLoader.Factory(requestQueue));
	}

	@SuppressWarnings("deprecation")
	private static RequestQueue setupVolleyCookies(Context context) {
		// copied from http://stackoverflow.com/a/21271347/253468 (option 1 for <2.3)
		DefaultHttpClient httpclient = new DefaultHttpClient();

		CookieStore cookieStore = new BasicCookieStore();
		httpclient.setCookieStore(cookieStore);

		HttpStack httpStack = new HttpClientStack(httpclient);
		return Volley.newRequestQueue(context, httpStack);
	}

	public static void setupOkHttp(Context context) {
		OkHttpClient client = new OkHttpClient();
		client.interceptors().add(new Interceptor() {
			@Override public Response intercept(Chain chain) throws IOException {
				Request request = chain.request();
				long t1 = System.nanoTime();
				Log.i("OKHTTP", String.format("Sending request %s on %s%n%s",
						request.url(), chain.connection(), request.headers()));

				Response response = chain.proceed(request);

				long t2 = System.nanoTime();
				Log.i("OKHTTP", String.format("Received response for %s in %.1fms%n%s",
						response.request().url(), (t2 - t1) / 1e6d, response.headers()));

				return response;
			}
		});
		Glide.get(context).register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(client));
	}
	*/
}

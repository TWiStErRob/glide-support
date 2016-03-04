package com.bumptech.glide.supportapp.github._921_different_headers;

import android.content.Context;

class TokenManager {
	private static final TokenManager INSTANCE = new TokenManager();
	public static TokenManager getInstance(Context context) {
		return INSTANCE;
	}
	public String getToken() throws InterruptedException {
		return "token" + System.currentTimeMillis();
	}
}

interface HeadersContract {
	String HEADER_CLIENT_ID = "ClientId";
	String CLIENT_ID = "myClientID";
	String HEADER_AUTHORIZATION = "Authorization";
	String O_AUTH_AUTHENTICATION = "OAuth";
}

abstract class UrlModel {
	String url;
	UrlModel(String url) {
		this.url = url;
	}
}

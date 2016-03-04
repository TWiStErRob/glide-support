package com.bumptech.glide.supportapp.github._606_fallback_in_fetcher;

import java.util.Locale;

class YouTubeVideo {
	String id;
	public YouTubeVideo(String id) {
		this.id = id;
	}
	public String getSDUrl() {
		return String.format(Locale.ROOT, "http://img.youtube.com/vi/%s/sddefault.jpg", id);
	}
	public String getMDUrl() {
		return String.format(Locale.ROOT, "http://img.youtube.com/vi/%s/mddefault.jpg", id);
	}
}

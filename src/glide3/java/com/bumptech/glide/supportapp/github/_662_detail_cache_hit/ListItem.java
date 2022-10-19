package com.bumptech.glide.supportapp.github._662_detail_cache_hit;

import java.io.Serializable;
import java.util.Locale;

public class ListItem implements Serializable {
	private static final long serialVersionUID = 7319185367136708644L;

	private static final String PLACEHOLD_IT_FORMAT = // "http://placehold.it/%1$dx%2$d/?text=%3$s %4$s\n(%1$dx%2$d)"
			"https://placeholdit.imgix.net/~text?txtsize=80&txt=%3$s %4$s&w=%1$d&h=%2$d";
	private final String text;
	public ListItem(String text) {
		this.text = text;
	}

	public String getStandardUrl() {
		return getLink(1920, 1080, "standard");
	}
	public String getLowUrl() {
		return getLink(1024, 768, "low");
	}
	public String getThumbUrl() {
		return getLink(320, 240, "thumb");
	}
	public String getText() {
		return text;
	}
	private String getLink(int width, int height, String type) {
		return String.format(Locale.ROOT, PLACEHOLD_IT_FORMAT, width, height, text, type);
	}
}

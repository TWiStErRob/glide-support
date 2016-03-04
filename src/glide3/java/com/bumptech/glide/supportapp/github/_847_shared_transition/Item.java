package com.bumptech.glide.supportapp.github._847_shared_transition;

import java.io.Serializable;
import java.util.*;

import android.support.annotation.ColorInt;

class Item implements Serializable {
	final String color;
	final String thumbUrl;
	final String fullUrl;
	final List<String> images = Arrays.asList(
			"http://www.gettyimages.ca/gi-resources/images/Homepage/Hero/UK/CMS_Creative_164657191_Kingfisher.jpg",
			"http://www.gettyimages.ca/gi-resources/images/Homepage/Category-Creative/UK/UK_Creative_462809583.jpg"
	);
	Item(@ColorInt int color) {
		this.color = String.format(Locale.ROOT, "%08x", color);
		this.fullUrl = link(512, 1024, color, ~color | 0xff000000, "full");
		this.thumbUrl = link(128, 256, color, ~color | 0xff000000, "thumb");
	}
	static String link(int w, int h, @ColorInt int bg, @ColorInt int fg, String text) {
		return String.format(Locale.ROOT, "http://placehold.it/%dx%d/%08x/%08x?text=%s", w, h, bg, fg, text);
	}
}

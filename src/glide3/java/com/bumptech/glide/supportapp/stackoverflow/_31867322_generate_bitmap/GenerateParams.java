package com.bumptech.glide.supportapp.stackoverflow._31867322_generate_bitmap;

import java.util.Locale;

/** Extracted params from imageWithText, left some hardcoded values like 20sp/bold/center in {@link Generators}. */
class GenerateParams {
	final String text;
	final int color;
	final int background;

	public GenerateParams(String text, int color, int bg) {
		this.text = text;
		this.color = color;
		this.background = bg;
	}

	public String getId() {
		// TODO make sure it's unique for every possible instance of GenerateParams
		// because it will affect how the resulting bitmap is cached
		// the below is correct correct for the current fields, if those change this has to change
		return String.format(Locale.ROOT, "%s-%08x-%08x", text, color, background);
	}
}

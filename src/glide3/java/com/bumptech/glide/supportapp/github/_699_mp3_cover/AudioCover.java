package com.bumptech.glide.supportapp.github._699_mp3_cover;

public class AudioCover {
	final String path;
	final AudioCoverSignature signature;
	public AudioCover(String path) {
		this.path = path;
		this.signature = new AudioCoverSignature(path);
		// this list creation should already be in the background (Loader?),
		// so it's fine to call blocking I/O there
		signature.refresh();
	}
}

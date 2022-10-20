package com.bumptech.glide.supportapp.github._699_mp3_cover;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

import com.bumptech.glide.load.Key;

public class AudioCoverSignature implements Key {
	private static final int AUDIO_SIZE = Long.SIZE / 8;
	private static final int FALLBACK_SIZE = 2 * Long.SIZE / 8;
	private final File file;
	private final ByteBuffer signature =
			ByteBuffer.allocate(AUDIO_SIZE + AudioCoverFetcher.FALLBACKS.length * FALLBACK_SIZE);

	public AudioCoverSignature(String path) {
		this.file = new File(path);
	}
	public void refresh() {
		File parent = file.getParentFile();
		signature.putLong(parent.lastModified());
		//signature.putLong(parent.length());
		for (String fallback : AudioCoverFetcher.FALLBACKS) {
			File cover = new File(parent, fallback);
			// no need for File.exists() or File.isFile() checks, both fall back to 0 if invalid
			signature.putLong(cover.lastModified());
			signature.putLong(cover.length());
		}
	}
	@Override public void updateDiskCacheKey(MessageDigest messageDigest) throws UnsupportedEncodingException {
		messageDigest.update(signature);
	}
}

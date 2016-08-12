package com.bumptech.glide.supportapp.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import com.bumptech.glide.load.Key;

public class LongSignature implements Key {
	private final long signature;

	public LongSignature(long signature) {
		this.signature = signature;
	}

	@SuppressWarnings("PointlessBitwiseExpression")
	@Override public void updateDiskCacheKey(MessageDigest messageDigest) throws UnsupportedEncodingException {
		messageDigest.update((byte)((signature >>> 0) & 0xFF)); // lowest byte
		messageDigest.update((byte)((signature >>> 8) & 0xFF));
		messageDigest.update((byte)((signature >>> 16) & 0xFF));
		messageDigest.update((byte)((signature >>> 24) & 0xFF)); // highest byte
	}

	@Override public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		LongSignature other = (LongSignature)o;
		return signature == other.signature;
	}

	@Override public int hashCode() {
		return (int)(signature ^ (signature >>> 32));
	}

	@Override public String toString() {
		return "LongSignature{"
				+ "signature='" + signature + '\''
				+ '}';
	}
}

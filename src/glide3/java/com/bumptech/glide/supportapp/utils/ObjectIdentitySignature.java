package com.bumptech.glide.supportapp.utils;

public class ObjectIdentitySignature extends LongSignature {
	public ObjectIdentitySignature(Object object) {
		super(System.identityHashCode(object));
	}
}

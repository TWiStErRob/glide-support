package com.bumptech.glide.supportapp;

import java.lang.reflect.Field;
import java.util.List;

import android.content.Context;

import com.bumptech.glide.*;
import com.bumptech.glide.module.GlideModule;

public class GlideReset extends BaseGlideReset {
	private static final Field GLIDE_FIELD;

	static {
		try {
			GLIDE_FIELD = Glide.class.getDeclaredField("glide");
			GLIDE_FIELD.setAccessible(true);
		} catch (Exception ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

	public GlideReset(Context context) {
		super(context);
	}

	@Override protected GlideBuilder newBuilder() {
		return new GlideBuilder(applicationContext);
	}

	@SuppressWarnings("deprecation")
	@Override protected Glide createGlide(GlideBuilder builder) {
		Glide.setup(builder);
		return Glide.get(applicationContext);
	}

	@SuppressWarnings("deprecation")
	@Override protected boolean isSetup() {
		return Glide.isSetup();
	}

	@Override protected void doTearDown() {
		try {
			GLIDE_FIELD.set(null, null);
		} catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
	}

	@Override protected void registerComponents(List<GlideModule> modules, Glide glide) {
		for (GlideModule module : modules) {
			module.registerComponents(applicationContext, glide);
		}
	}

	@Override protected void applyOptions(List<GlideModule> modules, GlideBuilder builder) {
		for (GlideModule module : modules) {
			module.applyOptions(applicationContext, builder);
		}
	}
}

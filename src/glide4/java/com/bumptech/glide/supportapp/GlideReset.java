package com.bumptech.glide.supportapp;

import java.lang.reflect.*;
import java.util.List;

import android.content.Context;

import com.bumptech.glide.*;
import com.bumptech.glide.module.GlideModule;

public class GlideReset extends BaseGlideReset {
	private static final Field GLIDE_FIELD;
	private static final Constructor<GlideBuilder> BUILDER_CONSTRUCTOR;
	private static final Method CREATEGLIDE_METHOD;

	static {
		try {
			GLIDE_FIELD = Glide.class.getDeclaredField("glide");
			GLIDE_FIELD.setAccessible(true);
			BUILDER_CONSTRUCTOR = GlideBuilder.class.getDeclaredConstructor(Context.class);
			BUILDER_CONSTRUCTOR.setAccessible(true);
			CREATEGLIDE_METHOD = GlideBuilder.class.getDeclaredMethod("createGlide");
			CREATEGLIDE_METHOD.setAccessible(true);
		} catch (Exception ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

	public GlideReset(Context context) {
		super(context);
	}

	@Override protected GlideBuilder newBuilder() {
		try {
			return BUILDER_CONSTRUCTOR.newInstance(applicationContext);
		} catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
	}

	@Override protected Glide createGlide(GlideBuilder builder) {
		try {
			return (Glide)CREATEGLIDE_METHOD.invoke(builder);
		} catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
	}

	@Override protected void applyOptions(List<GlideModule> modules, GlideBuilder builder) {
		for (GlideModule module : modules) {
			module.applyOptions(applicationContext, builder);
		}
	}

	@Override protected void registerComponents(List<GlideModule> modules, Glide glide) {
		for (GlideModule module : modules) {
			module.registerComponents(applicationContext, glide.getRegistry());
		}
	}

	@Override protected void doTearDown() {
		try {
			GLIDE_FIELD.set(null, null);
		} catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
	}

	@Override protected boolean isSetup() {
		try {
			return GLIDE_FIELD.get(null) != null;
		} catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
	}
}

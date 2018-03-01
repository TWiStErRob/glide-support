package com.bumptech.glide.supportapp;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.module.GlideModule;

@SuppressLint("VisibleForTests")
public class GlideReset {

	private static final Field GLIDE_FIELD;
	private static final String TAG = "GlideReset";

	static {
		try {
			GLIDE_FIELD = Glide.class.getDeclaredField("glide");
			GLIDE_FIELD.setAccessible(true);
		} catch (Exception ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

	private final Context applicationContext;

	public GlideReset(Context context) {
		this.applicationContext = context.getApplicationContext();
	}

	/** Mimics Glide.get with a specific set of modules */
	public void replace(Iterable<Class<? extends GlideModule>> moduleClasses) {
		Glide originalGlide = null;
		if (isSetup()) {
			originalGlide = Glide.get(applicationContext);
			tearDown();
			AppGlideModule.modules = Collections.emptyList();
		}
		setup(moduleClasses);
		Glide newGlide = Glide.get(applicationContext);
		Log.i(TAG, "Glide has been replaced, original=" + originalGlide + ", new=" + newGlide);
	}

	private void setup(Iterable<Class<? extends GlideModule>> moduleClasses) {
		Log.d(TAG, "Setting up new Glide...");
		GlideBuilder builder = new GlideBuilder();
		Collection<GlideModule> modules = createModules(moduleClasses);
		Log.v(TAG, "using modules: " + modules);
		AppGlideModule.modules = modules;
		Glide.init(applicationContext, builder);
	}

	/** new ManifestParser(applicationContext).parse(); */
	private Collection<GlideModule> createModules(Iterable<Class<? extends GlideModule>> moduleClasses) {
		Collection<GlideModule> modules = new ArrayList<>();
		for (Class<? extends GlideModule> moduleClass : moduleClasses) {
			try {
				modules.add(moduleClass.newInstance());
			} catch (Exception ex) {
				throw new IllegalArgumentException("Cannot create GlideModule: " + moduleClass, ex);
			}
		}
		return modules;
	}

	/** Try to get rid of references and clean as much as possible */
	public void tearDown() {
		if (!isSetup()) {
			return;
		}
		Log.v(TAG, "Tearing down Glide, it was set up before");
		Glide.with(applicationContext).onDestroy();
		Glide.get(applicationContext).clearMemory();
		Glide.tearDown();
	}

	private boolean isSetup() {
		try {
			return GLIDE_FIELD.get(null) != null;
		} catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
	}
}

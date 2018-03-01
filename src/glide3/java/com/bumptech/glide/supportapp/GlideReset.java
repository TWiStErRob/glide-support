package com.bumptech.glide.supportapp;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.module.GlideModule;

@SuppressWarnings("deprecation")
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
		if (Glide.isSetup()) {
			originalGlide = Glide.get(applicationContext);
			tearDown();
		}
		Log.d(TAG, "Setting up new Glide...");
		GlideBuilder builder = new GlideBuilder(applicationContext);
		List<GlideModule> modules = createModules(moduleClasses);
		Log.v(TAG, "using modules: " + modules);
		applyOptions(modules, builder);
		Glide.setup(builder);
		Glide newGlide = Glide.get(applicationContext);
		registerComponents(modules, newGlide);
		Log.i(TAG, "Glide has been replaced, original=" + originalGlide + ", new=" + newGlide);
	}

	/** Try to get rid of references and clean as much as possible */
	public void tearDown() {
		if (!Glide.isSetup()) {
			return;
		}
		Log.v(TAG, "Tearing down Glide, it was set up before");
		Glide.with(applicationContext).onDestroy();
		// TODO discover other contexts and destroy recursively
		// Note: there are likely none, because the activities that were shows prior should have been destroyed already
		Glide.get(applicationContext).clearMemory();
		// TODO RequestManagerRetriever.get().applicationManager = null;
		setGlide(null);
	}

	private void registerComponents(Iterable<GlideModule> modules, Glide glide) {
		for (GlideModule module : modules) {
			module.registerComponents(applicationContext, glide);
		}
	}

	private void applyOptions(Iterable<GlideModule> modules, GlideBuilder builder) {
		for (GlideModule module : modules) {
			module.applyOptions(applicationContext, builder);
		}
	}

	private void setGlide(Glide glide) {
		try {
			GLIDE_FIELD.set(null, glide);
		} catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
	}

	/** new ManifestParser(applicationContext).parse(); */
	private List<GlideModule> createModules(Iterable<Class<? extends GlideModule>> moduleClasses) {
		List<GlideModule> modules = new ArrayList<>();
		for (Class<? extends GlideModule> moduleClass : moduleClasses) {
			try {
				modules.add(moduleClass.newInstance());
			} catch (Exception ex) {
				throw new IllegalArgumentException("Cannot create GlideModule: " + moduleClass, ex);
			}
		}
		return modules;
	}
}

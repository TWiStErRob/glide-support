package com.bumptech.glide.supportapp.issue;

import java.lang.reflect.Field;
import java.util.*;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.*;
import com.bumptech.glide.module.GlideModule;

public class GlideReset {
	private static final String TAG = "GlideReset";
	private final Context applicationContext;
	public GlideReset(Context context) {
		this.applicationContext = context.getApplicationContext();
	}
	/** Mimics Glide.get with a specific set of modules */
	public void replace(List<Class<? extends GlideModule>> moduleClasses) {
		Glide originalGlide = null;
		if (Glide.isSetup()) {
			originalGlide = Glide.get(applicationContext);
			tearDown();
		}
		Log.i(TAG, "Setting up new Glide...");
		GlideBuilder builder = new GlideBuilder(applicationContext);
		// new ManifestParser(applicationContext).parse();
		List<GlideModule> modules = createModules(moduleClasses);
		Log.d(TAG, "using modules: " + modules);
		// same as in .get()
		for (GlideModule module : modules) {
			module.applyOptions(applicationContext, builder);
		}
		// glide = builder.createGlide();
		Glide.setup(builder);
		Glide glide = Glide.get(applicationContext);
		// same as in .get()
		for (GlideModule module : modules) {
			module.registerComponents(applicationContext, glide);
		}
		Log.w(TAG, "Glide has been replaced, original=" + originalGlide + ", new=" + glide);
	}

	/** Mimics ManifestParser but with static input */
	private List<GlideModule> createModules(List<Class<? extends GlideModule>> moduleClasses) {
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

	/** Try to get rid of references and clean as much as possible */
	public void tearDown() {
		if (!Glide.isSetup()) {
			return;
		}
		Log.d(TAG, "Tearing down Glide, it was set up before");
		destroy();
		Glide.get(applicationContext).clearMemory();
		try {
			// Glide.glide = null;
			Field glideField = Glide.class.getDeclaredField("glide");
			glideField.setAccessible(true);
			glideField.set(null, null);
		} catch (Exception ex) {
			throw new IllegalStateException("Cannot tear down Glide", ex);
		}
	}
	private void destroy() {
		Glide.with(applicationContext).onDestroy();
		// TODO discover other contexts and destroy recursively
		// Note: there are likely none, because the activities that were shows prior should have been destroyed already
	}
}

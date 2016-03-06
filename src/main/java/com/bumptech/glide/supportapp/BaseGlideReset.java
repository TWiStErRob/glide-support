package com.bumptech.glide.supportapp;

import java.util.*;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.*;
import com.bumptech.glide.module.GlideModule;

@SuppressWarnings("WeakerAccess")
public abstract class BaseGlideReset {
	private static final String TAG = "GlideReset";

	protected final Context applicationContext;

	public BaseGlideReset(Context context) {
		this.applicationContext = context.getApplicationContext();
	}

	/** new GlideBuilder(context) */
	protected abstract GlideBuilder newBuilder();
	/** builder.createGlide() */
	protected abstract Glide createGlide(GlideBuilder builder);
	/** Glide.isSetup() */
	protected abstract boolean isSetup();
	/** Glide.glide = null; */
	protected abstract void doTearDown();
	protected abstract void registerComponents(List<GlideModule> modules, Glide glide);
	protected abstract void applyOptions(List<GlideModule> modules, GlideBuilder builder);

	/** Mimics Glide.get with a specific set of modules */
	public void replace(List<Class<? extends GlideModule>> moduleClasses) {
		Glide originalGlide = null;
		if (isSetup()) {
			originalGlide = Glide.get(applicationContext);
			tearDown();
		}
		Log.i(TAG, "Setting up new Glide...");
		GlideBuilder builder = newBuilder();
		List<GlideModule> modules = createModules(moduleClasses);
		Log.d(TAG, "using modules: " + modules);
		applyOptions(modules, builder);
		Glide glide = createGlide(builder);
		registerComponents(modules, glide);
		Log.w(TAG, "Glide has been replaced, original=" + originalGlide + ", new=" + glide);
	}

	/** new ManifestParser(applicationContext).parse(); */
	protected List<GlideModule> createModules(List<Class<? extends GlideModule>> moduleClasses) {
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
		if (!isSetup()) {
			return;
		}
		Log.d(TAG, "Tearing down Glide, it was set up before");
		destroy();
		Glide.get(applicationContext).clearMemory();
		doTearDown();
	}

	protected void destroy() {
		Glide.with(applicationContext).onDestroy();
		// TODO discover other contexts and destroy recursively
		// Note: there are likely none, because the activities that were shows prior should have been destroyed already
	}
}

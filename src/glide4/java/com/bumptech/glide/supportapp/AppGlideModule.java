package com.bumptech.glide.supportapp;

import java.util.Collections;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.Excludes;
import com.bumptech.glide.annotation.GlideModule;

/**
 * Since there can be only one {@link com.bumptech.glide.module.AppGlideModule} that single one has to be able to
 * dynamically load different Glide instances with the specified modules.
 */
@GlideModule
@Excludes({
		com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule.class,
		com.bumptech.glide.integration.volley.VolleyLibraryGlideModule.class,
		com.bumptech.glide.supportapp.random.__quicky.QuickModule.class,
		com.bumptech.glide.supportapp.github._471_global_header.GlideModule.class,
		com.bumptech.glide.supportapp.github._1133_stetho_integration.GlideModule.class,
})
public class AppGlideModule extends com.bumptech.glide.module.AppGlideModule {

	/**
	 * These modules will be used instead of the ones parsed from manifest,
	 * and also every module should be excluded above,
	 * so {@link GlideReset} has full control over which modules will be initialized.
	 */
	public static @NonNull Iterable<com.bumptech.glide.module.GlideModule> modules = Collections.emptyList();

	/**
	 * @see #modules
	 */
	@Override public boolean isManifestParsingEnabled() {
		return false;
	}

	@Override public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
		for (com.bumptech.glide.module.GlideModule module : modules) {
			module.applyOptions(context, builder);
		}
	}

	@Override public void registerComponents(
			@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
		for (com.bumptech.glide.module.GlideModule module : modules) {
			module.registerComponents(context, glide, registry);
		}
	}
}

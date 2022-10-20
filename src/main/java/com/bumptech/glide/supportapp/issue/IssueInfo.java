package com.bumptech.glide.supportapp.issue;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.module.GlideModule;
import com.bumptech.glide.supportapp.R;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class IssueInfo {
	private static final Map<String, String> SOURCES = new HashMap<>();

	static {
		SOURCES.put("github", "https://github.com/bumptech/glide/issues/%s");
		SOURCES.put("groups", "https://groups.google.com/forum/#!topic/glidelibrary/%s");
		SOURCES.put("stackoverflow", "http://stackoverflow.com/questions/%s");
		SOURCES.put("random", null);
	}

	private static final Map<String, Integer> ICONS = new HashMap<>();

	static {
		ICONS.put("github", R.drawable.source_github);
		ICONS.put("groups", R.drawable.source_groups);
		ICONS.put("stackoverflow", R.drawable.source_stackoverflow);
		ICONS.put("random", R.drawable.source_random);
	}

	private final ClassNameSplitter clazz;
	private final String source;
	private final String name;
	private String display;
	private String id;
	private List<Class<? extends GlideModule>> modules;

	public IssueInfo(ClassNameSplitter clazz, List< Class<? extends GlideModule>> modules) {
		this.clazz = clazz;
		this.source = clazz.getHostPackageName();
		this.name = clazz.getPackageName();
		this.modules = modules;
	}

	public String getSource() {
		return source;
	}
	public String getName() {
		ensureSplit();
		return display;
	}
	public String getID() {
		ensureSplit();
		return id;
	}

	public String getLink() {
		String format = SOURCES.get(source);
		if (format == null) {
			return null;
		}
		return String.format(Locale.ROOT, format, getID());
	}
	public @NonNull Drawable getIcon(@NonNull Context context) {
		return ContextCompat.getDrawable(context, ICONS.get(source));
	}

	public Class<?> getEntryClass() {
		return clazz.getClassObject();
	}
	public boolean isActivity() {
		return Activity.class.isAssignableFrom(clazz.getClassObject());
	}
	public boolean isFragment() {
		return Fragment.class.isAssignableFrom(clazz.getClassObject());
	}

	private void ensureSplit() {
		if (display != null) {
			return;
		}
		String name = this.name.charAt(0) == '_'? this.name.substring(1) : this.name;
		name = name.replace("_dash_", "-");
		name = name.replace("_under_", "\0");
		int under = name.indexOf('_');
		id = name.substring(0, under).replace("\0", "_");
		display = name.substring(under + 1, name.length()).replace("_", " ");
	}
	
	public static Collection<String> getSources() {
		return Collections.unmodifiableSet(SOURCES.keySet());
	}
	public List<Class<? extends GlideModule>> getModules() {
		return modules;
	}
}

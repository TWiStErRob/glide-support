package com.bumptech.glide.supportapp.issue;

import java.lang.reflect.Modifier;
import java.util.*;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.bumptech.glide.supportapp.utils.ClassScanner;
import com.bumptech.glide.supportapp.utils.Utils;

public class BaseIssueEntryClassScanner extends ClassScanner {

	private final Set<String> filterPackages = new HashSet<>();
	private final List<Class<?>> classes = new ArrayList<>();

	public BaseIssueEntryClassScanner(Context context, Collection<String> subpackages) {
		super(context);
		Package appPackage = Utils.getAppPackage(context);
		for (String subpackage : subpackages) {
			this.filterPackages.add(appPackage.getName() + "." + subpackage);
		}
	}

	/** Match <code>[appPackage].[testgroup].[testpackage].[testclass]</code> */
	@Override protected boolean isTargetClassName(String className) {
		ClassNameSplitter splitter = new ClassNameSplitter(className);
		return !className.contains("$")
				&& splitter.isValid()
				&& filterPackages.contains(splitter.getFullHostPackageName())
				;
	}

	@Override protected boolean isTargetClass(Class<?> clazz) {
		return isAccessible(clazz) && (
				Fragment.class.isAssignableFrom(clazz) || Activity.class.isAssignableFrom(clazz)
		);
	}

	protected final boolean isAccessible(Class<?> clazz) {
		return !Modifier.isAbstract(clazz.getModifiers()) && Modifier.isPublic(clazz.getModifiers());
	}

	@Override protected void onScanResult(Class<?> clazz) {
		classes.add(clazz);
	}

	public List<Class<?>> getClasses() {
		return Collections.unmodifiableList(classes);
	}
}

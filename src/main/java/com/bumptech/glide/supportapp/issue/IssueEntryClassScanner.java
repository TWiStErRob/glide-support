package com.bumptech.glide.supportapp.issue;

import java.lang.reflect.Modifier;
import java.util.*;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.bumptech.glide.supportapp.utils.*;

public class IssueEntryClassScanner extends ClassScanner {
	private final Set<String> filterPackages = new HashSet<>();
	private final List<Class<?>> classes = new ArrayList<>();
	private final Package appPackage;
	private final Collection<String> filterNames;

	public IssueEntryClassScanner(Context context, Collection<String> subpackages, Collection<String> classNames) {
		super(context);
		this.filterNames = classNames;
		this.appPackage = Utils.getAppPackage(context);
		for (String subpackage : subpackages) {
			this.filterPackages.add(appPackage.getName() + "." + subpackage);
		}
	}

	/** Match <code>[appPackage].[testgroup].[testpackage].[testclass]</code> */
	@Override protected boolean isTargetClassName(String className) {
		int lastDot = className.lastIndexOf('.');
		if (lastDot == -1) {
			return false;
		}
		String classPackage = className.substring(0, lastDot);
		String classSimpleName = className.substring(lastDot + 1, className.length());
		lastDot = classPackage.lastIndexOf('.');
		if (lastDot == -1) {
			return false;
		}
		classPackage = classPackage.substring(0, lastDot); // remove direct name of the package
		return !className.contains("$")
				&& filterPackages.contains(classPackage)
				&& filterNames.contains(classSimpleName);
	}

	@Override protected boolean isTargetClass(Class<?> clazz) {
		return !Modifier.isAbstract(clazz.getModifiers()) && Modifier.isPublic(clazz.getModifiers())
				&& (Fragment.class.isAssignableFrom(clazz) || Activity.class.isAssignableFrom(clazz))
				;
	}

	@Override protected void onScanResult(Class<?> clazz) {
		classes.add(clazz);
	}

	public List<Class<?>> getClasses() {
		return Collections.unmodifiableList(classes);
	}
}

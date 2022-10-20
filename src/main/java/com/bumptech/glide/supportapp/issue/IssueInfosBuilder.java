package com.bumptech.glide.supportapp.issue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.bumptech.glide.module.GlideModule;

public class IssueInfosBuilder {
	private final List<Class<?>> classes;
	private final Map<String, Set<ClassNameSplitter>> groups = new HashMap<>();
	private Comparator<? super IssueInfo> sort;
	private final Set<String> favored = new HashSet<>();
	private boolean wantFragments;
	private boolean wantActivities;

	public IssueInfosBuilder(List<Class<?>> classes) {
		this.classes = classes;
	}
	public IssueInfosBuilder sortBy(Comparator<? super IssueInfo> comparator) {
		this.sort = comparator;
		return this;
	}
	public IssueInfosBuilder favor(String favoredClass) {
		this.favored.add(favoredClass);
		return this;
	}
	public IssueInfosBuilder activities(boolean want) {
		this.wantActivities = want;
		return this;
	}
	public IssueInfosBuilder fragments(boolean want) {
		this.wantFragments = want;
		return this;
	}

	@SuppressWarnings("deprecation")
	public List<IssueInfo> build() {
		groupByPackage();
		List<IssueInfo> issues = new ArrayList<>(classes.size());
		for (Entry<String, Set<ClassNameSplitter>> group : groups.entrySet()) {
			List<Class<? extends GlideModule>> modules = new ArrayList<>();
			List<ClassNameSplitter> others = new ArrayList<>();
			for (ClassNameSplitter clazz : group.getValue()) {
				if (GlideModule.class.isAssignableFrom(clazz.getClassObject())) {
					@SuppressWarnings("unchecked")
					Class<? extends GlideModule> moduleClass = (Class<? extends GlideModule>)clazz.getClassObject();
					modules.add(moduleClass);
				} else {
					others.add(clazz);
				}
			}
			for (ClassNameSplitter clazz : others) {
				IssueInfo info = new IssueInfo(clazz, modules);
				if ((wantActivities && info.isActivity()) || (wantFragments && info.isFragment())) {
					issues.add(info);
				}
			}
		}
		Collections.sort(issues, new FavoringComparator(favored, sort));
		return issues;
	}

	private void groupByPackage() {
		for (Class<?> clazz : classes) {
			ClassNameSplitter splitter = new ClassNameSplitter(clazz);
			String key = splitter.getFullPackageName();
			Set<ClassNameSplitter> classes = groups.get(key);
			if (classes == null) {
				classes = new HashSet<>();
				groups.put(key, classes);
			}
			classes.add(splitter);
		}
	}

	private static class FavoringComparator implements Comparator<IssueInfo> {
		private final Set<String> favored;
		private final Comparator<? super IssueInfo> sort;
		public FavoringComparator(Set<String> favored, Comparator<? super IssueInfo> sort) {
			this.favored = favored;
			this.sort = sort;
		}
		@Override public int compare(IssueInfo lhs, IssueInfo rhs) {
			boolean lFav = favored.contains(lhs.getEntryClass().getName());
			boolean rFav = favored.contains(rhs.getEntryClass().getName());
			if (lFav && !rFav) {
				return -1;
			}
			if (!lFav && rFav) {
				return 1;
			}
			return sort != null ? sort.compare(lhs, rhs) : 0; // if sort is undefined, everything else is equal
		}
	}
}

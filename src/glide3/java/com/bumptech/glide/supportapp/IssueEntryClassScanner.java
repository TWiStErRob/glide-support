package com.bumptech.glide.supportapp;

import java.util.Collection;

import android.content.Context;

import com.bumptech.glide.module.GlideModule;
import com.bumptech.glide.supportapp.issue.BaseIssueEntryClassScanner;

public class IssueEntryClassScanner extends BaseIssueEntryClassScanner {

	public IssueEntryClassScanner(Context context, Collection<String> subpackages) {
		super(context, subpackages);
	}

	@Override
	protected boolean isTargetClass(Class<?> clazz) {
		return super.isTargetClass(clazz)
				|| (isAccessible(clazz) && GlideModule.class.isAssignableFrom(clazz));
	}
}

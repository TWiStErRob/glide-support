package com.bumptech.glide.supportapp;

import android.content.Context;

import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.module.GlideModule;
import com.bumptech.glide.module.LibraryGlideModule;
import com.bumptech.glide.supportapp.issue.BaseIssueEntryClassScanner;

import java.util.Collection;

public class IssueEntryClassScanner extends BaseIssueEntryClassScanner {
    public IssueEntryClassScanner(Context context, Collection<String> subpackages) {
        super(context, subpackages);
    }

    @Override
    protected boolean isTargetClass(Class<?> clazz) {
        return super.isTargetClass(clazz) || (isAccessible(clazz) && (
                GlideModule.class.isAssignableFrom(clazz)
                || AppGlideModule.class.isAssignableFrom(clazz)
                || LibraryGlideModule.class.isAssignableFrom(clazz)
        ));
    }
}

package com.bumptech.glide.supportapp.utils;

import java.io.*;

import android.content.*;
import android.content.pm.*;
import android.net.Uri;
import android.util.Log;

public class Utils {
	public static void copy(File source, File target) throws IOException {
		InputStream in = new FileInputStream(source);
		try {
			OutputStream out = new FileOutputStream(target);
			try {
				byte[] buffer = new byte[16 * 1024];
				int readCount;
				while ((readCount = in.read(buffer)) > 0) {
					out.write(buffer, 0, readCount);
				}
				out.flush();
			} finally {
				try {
					out.close();
				} catch (IOException ignore) {
				}
			}
		} finally {
			try {
				in.close();
			} catch (IOException ignore) {
			}
		}
	}

	public static Package getAppPackage(Context context) {
		try {
			Class<?> appClass = Class.forName(context.getApplicationInfo().className);
			return appClass.getPackage();
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("Cannot determine application package", e);
		}
	}

	public static Uri toResourceUri(Context context, int resID) {
		return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
				context.getResources().getResourcePackageName(resID) + '/' +
				context.getResources().getResourceTypeName(resID) + '/' +
				context.getResources().getResourceEntryName(resID));
	}

	public static String getMetadataValue(Context context, ComponentName name, String metadataName) {
		String result = null;
		try {
			ActivityInfo ai = context.getPackageManager().getActivityInfo(name, PackageManager.GET_META_DATA);
			if (ai.metaData != null) {
				String clazz = ai.metaData.getString(metadataName);
				if (clazz != null) {
					result = clazz;
					Log.i("SYS", "Using " + result + " from metadata.");
				} else {
					Log.w("SYS", name + " doesn't have a " + metadataName + " metadata.");
				}
			} else {
				Log.w("SYS", name + " doesn't have metadata.");
			}
		} catch (Exception e) {
			Log.w("SYS", name + " not registered in package manager.", e);
		}
		return result;
	}
}

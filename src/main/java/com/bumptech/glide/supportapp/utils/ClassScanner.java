package com.bumptech.glide.supportapp.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Enumeration;

import android.content.Context;
import android.util.Log;

import dalvik.system.*;

/** @see <a href="http://stackoverflow.com/a/31088067/253468">Find all classes in a package in Android</a> */
public abstract class ClassScanner {
	private static final String TAG = "ClassScanner";
	private Context mContext;
	private boolean debug;

	public ClassScanner(Context context) {
		mContext = context.getApplicationContext();
	}

	public Context getContext() {
		return mContext;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public void scan() throws IOException, ClassNotFoundException, NoSuchMethodException {
		long timeBegin = System.currentTimeMillis();

		PathClassLoader classLoader = (PathClassLoader)getContext().getClassLoader();
		//PathClassLoader classLoader = (PathClassLoader) Thread.currentThread().getContextClassLoader();//This also works good
		File mainApk = new File(getContext().getPackageCodePath());
		Log.i(TAG, "Main APK: " + mainApk.getAbsolutePath());
		for(File apk : mainApk.getParentFile().listFiles(new FilenameFilter() {
			@Override public boolean accept(File dir, String name) {
				return name.endsWith(".apk") && !name.equals("split_lib_dependencies_apk.apk");
			}
		})) {
			Log.i(TAG, "Scanning APK: " + apk.getAbsolutePath());
			DexFile dexFile = new DexFile(apk);
			Enumeration<String> classNames = dexFile.entries();
			while (classNames.hasMoreElements()) {
				String className = classNames.nextElement();
				if (debug) Log.v(TAG, "\tMatching class name: " + className);
				if (isTargetClassName(className)) {
					if (debug) Log.v(TAG, "\tMatched class name, loading: " + className);
					// java.lang.ExceptionInInitializerError
					//Class<?> aClass = Class.forName(className);
					// tested on 魅蓝Note(M463C)_Android4.4.4 and Mi2s_Android5.1.1
					//Class<?> aClass = Class.forName(className, false, classLoader);
					// tested on 魅蓝Note(M463C)_Android4.4.4 and Mi2s_Android5.1.1
					Class<?> aClass = classLoader.loadClass(className);
					if (isTargetClass(aClass)) {
						if (debug) Log.v(TAG, "\tMatched class: " + className);
						onScanResult(aClass);
					}
				}
			}
		}

		long timeEnd = System.currentTimeMillis();
		long timeElapsed = timeEnd - timeBegin;
		Log.d(TAG, "scan() cost " + timeElapsed + "ms");
	}

	protected abstract boolean isTargetClassName(String className);

	protected abstract boolean isTargetClass(Class<?> clazz);

	protected abstract void onScanResult(Class<?> clazz);
}

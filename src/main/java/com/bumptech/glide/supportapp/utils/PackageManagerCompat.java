package com.bumptech.glide.supportapp.utils;

import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.os.Build;

public class PackageManagerCompat {

	/**
	 * @see PackageManager#getActivityInfo(ComponentName, int)
	 */
	@SuppressWarnings("deprecation")
	public static ActivityInfo getActivityInfo(
			PackageManager $this, ComponentName packageName, long flags
	) throws PackageManager.NameNotFoundException {
		if (Build.VERSION_CODES.TIRAMISU <= Build.VERSION.SDK_INT) {
			return $this.getActivityInfo(packageName, PackageManager.ComponentInfoFlags.of(flags));
		} else {
			return $this.getActivityInfo(packageName, (int)flags);
		}
	}

	/**
	 * @see PackageManager#getPackageInfo(String, int)
	 */
	@SuppressWarnings("deprecation")
	public static PackageInfo getPackageInfo(
			PackageManager $this, String packageName, long flags
	) throws PackageManager.NameNotFoundException {
		if (Build.VERSION_CODES.TIRAMISU <= Build.VERSION.SDK_INT) {
			return $this.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(flags));
		} else {
			return $this.getPackageInfo(packageName, (int)flags);
		}
	}

	/**
	 * @see PackageManager#resolveContentProvider(String, int)
	 */
	@SuppressWarnings("deprecation")
	public static ProviderInfo resolveContentProvider(
			PackageManager $this, String packageName, long flags
	) {
		if (Build.VERSION_CODES.TIRAMISU <= Build.VERSION.SDK_INT) {
			return $this.resolveContentProvider(packageName,
					PackageManager.ComponentInfoFlags.of(flags));
		} else {
			return $this.resolveContentProvider(packageName, (int)flags);
		}
	}
}

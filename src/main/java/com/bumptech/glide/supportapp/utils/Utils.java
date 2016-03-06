package com.bumptech.glide.supportapp.utils;

import java.io.*;

import android.content.Context;

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
}

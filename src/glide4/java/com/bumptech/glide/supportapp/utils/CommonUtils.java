package com.bumptech.glide.supportapp.utils;

public class CommonUtils {

    public static boolean isGif(String url) {
        return getExtension(url).contains("gif");
    }

    @SuppressWarnings("WeakerAccess")
    public static String getExtension(String url) {
        return url.substring(url.lastIndexOf("."));
    }
}

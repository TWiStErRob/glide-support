# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in P:/tools/android-sdk-windows/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-dontwarn okio.Okio # uses java.nio.*
-dontwarn okio.DeflaterSink # uses some random class
-dontwarn com.squareup.picasso.OkHttp3Downloader # uses okhttp3.**
-dontwarn com.squareup.picasso.OkHttpDownloader # uses com.squareup.okhttp.**
-dontwarn com.firebase.client.utilities.HttpUtilities # uses org.apache.http.**
-dontwarn com.firebase.client.authentication.* # uses org.apache.http.**
-dontwarn com.fasterxml.jackson.databind.ext.DOMSerializer # uses org.w3c.dom.bootstrap.DOMImplementationRegistry

# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile



# ============================
# Room Rules
# ============================

-keep class androidx.room.** { *; }
-keep interface androidx.room.** { *; }
-keep class **_Impl { *; }
-keep @androidx.room.Entity class * { *; }
-keep @androidx.room.Database class * { *; }
-keep @androidx.room.Dao interface * { *; }
-keep class * extends androidx.room.RoomDatabase { *; }
-keepclassmembers class * {
    @androidx.room.* <methods>;
}

# ============================
# Apache POI Rules (Android port)
# ============================

-keep class org.apache.poi.** { *; }
-keep class org.slf4j.** { *; }
-keep class org.apache.logging.log4j.** { *; }
-keepclassmembers class * {
    public <init>(...);
}
-keep class javax.xml.parsers.** { *; }
-keep class org.xml.sax.** { *; }
-keep class javax.xml.bind.** { *; }
-keep class javax.xml.stream.** { *; }

# ============================
# OpenCV Rules
# ============================

-keep class org.opencv.** { *; }
-keepclasseswithmembernames class * {
    native <methods>;
}
-keep class org.opencv.core.CvException { *; }
-keepattributes *Annotation*,SourceFile,LineNumberTable
-dontwarn org.opencv.**

# ============================
# Kotlin and Coroutines support
# ============================

-keepclassmembers class kotlin.Metadata { *; }
-keep class kotlin.** { *; }
-keepclassmembers class kotlinx.coroutines.** { *; }
-keepclassmembers class kotlin.reflect.** { *; }

# ============================
# General Android recommended rules
# ============================

-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepattributes *Annotation*

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
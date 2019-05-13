# Print result proguard in case of problems
#-printconfiguration ./build/tmp/full-r8-config.txt

# Kotlin Reflection
# These classes are used via kotlin reflection and the keep might not be required anymore once Proguard supports
# Kotlin reflection directly.
-keep public class kotlin.reflect.jvm.internal.impl.builtins.* { public *; }
-keep public class kotlin.reflect.jvm.internal.impl.serialization.deserialization.builtins.* { public *; }
-keep public class kotlin.reflect.jvm.internal.impl.load.java.* { public *; }
# If Companion objects are instantiated via Kotlin reflection and they extend/implement a class that Proguard
# would have removed or inlined we run into trouble as the inheritance is still in the Metadata annoation
# read by Kotlin reflection.
# FIXME: Remove if Kotlin reflection is supported by Pro/Dexguard
-if class **$Companion extends **
-keep class <2>
-if class **$Companion implements **
-keep class <2>
# https://medium.com/@AthorNZ/kotlin-metadata-jackson-and-proguard-f64f51e5ed32
-keep class kotlin.Metadata { *; }

# Need to keep class name due to kotlin-reflect
-keep interface com.airbnb.mvrx.MvRxState
# !! Tweak this once https://issuetracker.google.com/issues/112386012 is fixed !!
# Need to keep class name due to kotlin-reflect
-keep class * implements com.airbnb.mvrx.MvRxState { *; }
-keep class * implements com.airbnb.mvrx.MvRxViewModelFactory { *; }

# Fabric/Crashlytics
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable

-keep public class * extends java.lang.Exception
-keep class com.crashlytics.** { *; }
-keep class com.crashlytics.android.**
-dontwarn com.crashlytics.**

# Logger slf4j
-dontwarn org.slf4j.**

# Retrofit 2
# Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod
# Retrofit does reflection on method and parameter annotations.
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**
# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit
# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.KotlinExtensions
# With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
# and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

# OkHttp3
# JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*
# OkHttp platform used only on JVM and when Conscrypt dependency is available.
-dontwarn okhttp3.internal.platform.ConscryptPlatform
-dontwarn okhttp3.internal.platform.*

# Gson
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature
# For using GSON @Expose annotation
-keepattributes *Annotation*
# Gson specific classes
-dontwarn sun.misc.**
-keep class com.google.gson.stream.** { *; }
# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
#R8 rule https://r8.googlesource.com/r8/+/refs/heads/master/compatibility-faq.md
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# Logback
-keep class ch.qos.** { *; }
-keep class org.slf4j.** { *; }
-keepattributes *Annotation*
-dontwarn ch.qos.logback.core.net.*

# Inner lambda issues https://youtrack.jetbrains.com/issue/KT-16084

# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}

# ========================== webview 中js交互 ==========================

#保留annotation， 例如 @JavascriptInterface 等 annotation
-keepattributes *Annotation*

#保留跟 javascript相关的属性
-keepattributes JavascriptInterface

#保留JavascriptInterface中的方法
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

#这个根据自己的project来设置，这个类用来与js交互，所以这个类中的 字段 ，方法，等尽量保持
-keepclassmembers public class com.xxjr.cfs.cfscustomer.module.center.gold.recharge.fast.BaseGoldWebActivity{
   <fields>;
   <methods>;
   public *;
   private *;
}

-keepclassmembers public class com.xxjr.cfs.cfscustomer.module.im.feedback.FeedbackWebActivity{
   <fields>;
   <methods>;
   public *;
   private *;
}



# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Glide
-keep public class * implements com.bumptech.glide.module.AppGlideModule
-keep public class * implements com.bumptech.glide.module.LibraryGlideModule
-keep class com.bumptech.glide.** { *; }
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-dontwarn com.bumptech.glide.load.resource.bitmap.VideoDecoder

# bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
# tinker混淆规则
-dontwarn com.tencent.tinker.**
-keep class com.tencent.tinker.** { *; }
# 如果你使用了support-v4包，你还需要配置以下混淆规则：
-keep class android.support.** { *; }


-dontwarn com.firebase.ui.auth.data.remote.**

# Retrofit
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions

# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain service method parameters.
-keepclassmembernames,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

-keep class retrofit2.** { *; }


# okhttp
-dontwarn okio.**

# OkHttp3
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**

-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase


# ========================== 极光推送 ==========================
-dontoptimize
-dontpreverify

-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-keep class * extends cn.jpush.android.helpers.JPushMessageReceiver { *; }

-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }

#==================gson && protobuf==========================
-dontwarn com.google.**
-keep class com.google.gson.** {*;}
-keep class com.google.protobuf.** {*;}

## ========================== 指纹解锁 ==========================
# MeiZuFingerprint
-keep class com.fingerprints.service.** { *; }
-dontwarn com.fingerprints.service.**

# SmsungFingerprint
-keep class com.samsung.android.sdk.** { *; }
-dontwarn com.samsung.android.sdk.**


#Warning: com.xxjr.cfs.library.util.RxUtils$$Lambda$1: can't find referenced class java.lang.invoke.LambdaForm$Hidden
#-keep class com.xxjr.cfs.library.util.RxUtils.**{*;}
#-dontwarn com.xxjr.cfs.library.util.RxUtils.**

#Warning: com.xxjr.cfs.cfscustomer.di.module.HttpModule$$Lambda$1: can't find referenced class java.lang.invoke.LambdaForm$Hidden
#-keep class com.xxjr.cfs.cfscustomer.di.module.HttpModule.**{*;}

-dontwarn org.codehaus.**
-dontwarn java.nio.**
-dontwarn java.lang.invoke.**


#========================== utilCode ==========================
-keep class com.blankj.utilcode.** { *; }
-keepclassmembers class com.blankj.utilcode.** { *; }
-dontwarn com.blankj.utilcode.**

# Suppress warnings from gRPC dependencies
-dontwarn com.google.common.**
-dontwarn com.google.api.client.**
-dontwarn com.google.protobuf.**
-dontwarn io.grpc.**
-dontwarn okio.**
-dontwarn com.google.errorprone.annotations.**
-keep class io.grpc.internal.DnsNameResolveProvider
-keep class io.grpc.okhttp.OkHttpChannelProvider

# ========================== banner 的混淆代码 ==========================
-keep class com.youth.banner.** {
    *;
 }

#指定压缩级别
-optimizationpasses 5

#不跳过非公共的库的类成员
-dontskipnonpubliclibraryclassmembers

#混淆时采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#把混淆类中的方法名也混淆了
-useuniqueclassmembernames

#优化时允许访问并修改有修饰符的类和类的成员
-allowaccessmodification

#将文件来源重命名为“SourceFile”字符串
-renamesourcefileattribute SourceFile
#保留行号
-keepattributes SourceFile,LineNumberTable

#保持所有实现 Serializable 接口的类成员
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

#Fragment不需要在AndroidManifest.xml中注册，需要额外保护下
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.Fragment

# ========================== 保持测试相关的代码 ==========================
-dontnote junit.framework.**
-dontnote junit.runner.**
-dontwarn android.test.**
-dontwarn android.support.test.**
-dontwarn org.junit.**


#========================== BaseRecyclerViewAdapterHelper ==========================
-keep class com.chad.library.adapter.** {
*;
}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
     <init>(...);
}
-keep class com.ch


-keep class com.newrelic.** { *; }

-dontwarn com.newrelic.**

-keepattributes Exceptions, Signature, InnerClasses

#========================== chipslayoutmanager ==========================
-keep class com.beloo.widget.chipslayoutmanager.**{ *; }
-dontwarn com.beloo.widget.chipslayoutmanager.**

#========================== RxJavaProGuardRules ==========================
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
-dontnote rx.internal.util.PlatformDependent

#========================== evenbus ==========================
-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

#========================== greendao ==========================
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties

# If you do not use SQLCipher:
-dontwarn org.greenrobot.greendao.database.**
# If you do not use Rx:
-dontwarn rx.**

#========================== GreenDaoUpgradeHelper ==========================
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
    public static void dropTable(org.greenrobot.greendao.database.Database, boolean);
    public static void createTable(org.greenrobot.greendao.database.Database, boolean);
}

#========================== 关于数据库 sqlcipher ==========================
#保留数据库驱动--数据库驱动往往是动态加载的
-keep class * implements java.sql.Driver
-keep  class net.sqlcipher.** {
    *;
}
-keep  class net.sqlcipher.database.** {
    *;
}

#========================== fastjson ==========================
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.**{*;}

#========================== ARouter ==========================
-keep public class com.alibaba.android.arouter.routes.**{*;}
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}
-keep interface * implements com.alibaba.android.arouter.facade.template.IProvider
-dontwarn javax.lang.model.element.**

#========================== Android Annotations ==========================
-dontwarn org.springframework.**
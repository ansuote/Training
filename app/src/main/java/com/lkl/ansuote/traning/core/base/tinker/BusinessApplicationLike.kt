package com.lkl.ansuote.traning.core.base.tinker

import android.annotation.TargetApi
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.Utils
import com.lkl.ansuote.hdqlibrary.base.AppStatusTracker
import com.lkl.ansuote.modulemodel.base.ModuleModel
import com.lkl.ansuote.traning.BuildConfig
import com.lkl.ansuote.traning.core.base.Constants
import com.meituan.android.walle.WalleChannelReader
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.beta.interfaces.BetaPatchListener
import com.tencent.tinker.entry.DefaultApplicationLike


/**
 *
 * 原本 BusinessApplication 业务逻辑在此处实现
 * @author huangdongqiang
 * @date 30/01/2019
 */
class BusinessApplicationLike(application: Application,
                              tinkerFlags: Int,
                              tinkerLoadVerifyFlag: Boolean,
                              applicationStartElapsedTime: Long,
                              applicationStartMillisTime: Long,
                              tinkerResultIntent: Intent) :
        DefaultApplicationLike(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent)  {

    override fun onCreate() {
        super.onCreate()

        initBugly()
    }

    private fun initBugly() {

        // 设置是否开启热更新能力，默认为true
        Beta.enableHotfix = true
        // 设置是否自动下载补丁，默认为true
        Beta.canAutoDownloadPatch = true

        // 设置是否自动合成补丁，默认为true
        Beta.canAutoPatch = true

        // 设置是否显示弹窗提示用户重启。默认为false，如果想弹窗提示用户重启，设置为true即可。
        Beta.canNotifyUserRestart = true

        Beta.betaPatchListener = object : BetaPatchListener{
            override fun onApplySuccess(p0: String?) {
                //补丁应用成功
            }

            override fun onPatchReceived(p0: String?) {
                //补丁下载地址
            }

            override fun onApplyFailure(p0: String?) {
                //补丁应用失败
            }

            override fun onDownloadReceived(p0: Long, p1: Long) {

            }

            override fun onDownloadSuccess(p0: String?) {
                //补丁下载成功
            }

            override fun onDownloadFailure(p0: String?) {
                //补丁下载失败
            }

            override fun onPatchRollback() {
            }

        }

        //设置渠道号
        WalleChannelReader.getChannel(application)?.let {
            Bugly.setAppChannel(application, it)
        }

        // 指定为开发设备
        Bugly.setIsDevelopmentDevice(application, BuildConfig.DEBUG)

        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
        // 调试时，将第三个参数改为true
        Bugly.init(application, Constants.BUGLY_ID, BuildConfig.DEBUG) //BuildConfig.DEBUG //true
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    override fun onBaseContextAttached(base: Context) {
        super.onBaseContextAttached(base)
        MultiDex.install(base)

        // 安装tinker
        // TinkerManager.installTinker(this); 替换成下面Bugly提供的方法
        Beta.installTinker(this)

        ModuleModel.init(application, com.lkl.ansuote.modulebussiness.base.Constants.DB_NAME)
        initLibraryApplication()
        initARouter()
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    fun registerActivityLifecycleCallback(callbacks: Application.ActivityLifecycleCallbacks) {
        application.registerActivityLifecycleCallbacks(callbacks)
    }


    private fun initARouter() {
        if (BuildConfig.DEBUG) {
            //调试模式必须在 init之前
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(application)
    }

    private fun initLibraryApplication() {
        AppStatusTracker.init(application)
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
        Utils.init(application)
    }

}
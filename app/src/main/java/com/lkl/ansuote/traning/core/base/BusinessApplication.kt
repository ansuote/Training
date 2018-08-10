package com.lkl.ansuote.traning.core.base

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.blankj.utilcode.util.Utils
import com.lkl.ansuote.hdqlibrary.base.AppStatusTracker
import com.lkl.ansuote.traning.BuildConfig
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

/**
 *
 *
 * @author huangdongqiang
 * @date 20/07/2018
 */
class BusinessApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        initLibraryApplication()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    private fun initLibraryApplication() {
        AppStatusTracker.init(this)
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
        Utils.init(this)
    }
}
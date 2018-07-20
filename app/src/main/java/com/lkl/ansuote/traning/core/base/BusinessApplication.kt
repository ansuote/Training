package com.lkl.ansuote.traning.core.base

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex

/**
 *
 *
 * @author huangdongqiang
 * @date 20/07/2018
 */
class BusinessApplication : Application(){

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}
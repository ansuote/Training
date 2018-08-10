package com.lkl.ansuote.traning.module.dagger

import android.content.Context
import com.orhanobut.logger.Logger

/**
 *
 *
 * @author huangdongqiang
 * @date 08/08/2018
 */
class ServiceApi constructor(context: Context, url: String) {

    val context = context
    val url = url


    fun doServiceWork() {
        Logger.i("ServiceApi; ---- context = $context ---- url = $url")
    }
}
package com.lkl.ansuote.traning.module.dagger

import android.content.Context
import com.orhanobut.logger.Logger

/**
 *
 *
 * @author huangdongqiang
 * @date 08/08/2018
 */
class LocalApi(context: Context) {

    val context = context


    fun doLocalWork() {
        Logger.i("doLocalWork; ---- context = $context")

    }
}
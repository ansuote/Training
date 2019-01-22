package com.lkl.ansuote.traning.module.bus

import android.app.Service
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.IBinder
import com.blankj.utilcode.util.ToastUtils
import com.lkl.ansuote.modulebus.LiveEventBus

/**
 *
 * 手动注册/注销消息对象的服务
 * @author huangdongqiang
 * @date 22/01/2019
 */
class BusTestByHandService : Service() {
    private val observer: Observer<String> = Observer<String> { t -> ToastUtils.showShort(t) }

    override fun onCreate() {
        super.onCreate()
        LiveEventBus.get().with(EVENT_TEST_TO_SERVICE, String::class.java).observeForever(observer)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        LiveEventBus.get().with(EVENT_TEST_TO_SERVICE, String::class.java).removeObserver(observer)
        super.onDestroy()
    }

}
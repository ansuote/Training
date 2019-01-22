package com.lkl.ansuote.traning.module.bus

import android.arch.lifecycle.LifecycleService
import android.arch.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import com.lkl.ansuote.modulebus.LiveEventBus

/**
 *
 * 实现 LifecycleOwner,实现消息对象生命周期自处理的服务
 * @author huangdongqiang
 * @date 22/01/2019
 */
class BusTestAutoService: LifecycleService() {

    override fun onCreate() {
        super.onCreate()

        LiveEventBus.get().with(EVENT_TEST_TO_SERVICE, String::class.java).observe(this, Observer{
            ToastUtils.showShort(it + this@BusTestAutoService.toString())
        })
    }
}
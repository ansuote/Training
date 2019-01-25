package com.lkl.ansuote.traning.module.jetpack.lifecycles

import android.arch.lifecycle.DefaultLifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.util.Log

/**
 *
 * 优先使用 DefaultLifecycleObserver（只要支持 java & kotlin 8）,代替注解的形式 @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
 * @author huangdongqiang
 * @date 23/01/2019
 */
class LocationHelper : DefaultLifecycleObserver{

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        Log.i("lkl", "LocationHelper -- onCreate")
    }


    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        Log.i("lkl", "LocationHelper -- onDestroy")
    }
}
package com.lkl.ansuote.traning.module.jetpack.lifecycles

import android.arch.lifecycle.Lifecycle
import android.os.Bundle
import android.util.Log
import com.lkl.ansuote.hdqlibrary.base.BaseActivity
import com.lkl.ansuote.traning.R

/**
 *
 *
 * @author huangdongqiang
 * @date 23/01/2019
 */
class LifecyclesTestActivity: BaseActivity() {
    private val mLocationHelper = LocationHelper()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("lkl", "LifecyclesTestActivity -- onCreate")

        setContentView(R.layout.life_cycles_test_activity)

        //加入观察者
        lifecycle.addObserver(mLocationHelper)


        //可以获取状态，在当前界面可见的时候才进行处理
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {

        }

    }

    override fun onDestroy() {
        //移除观察者
        lifecycle.removeObserver(mLocationHelper)
        Log.i("lkl", "LifecyclesTestActivity -- onDestroy 前面")
        super.onDestroy()
    }
}
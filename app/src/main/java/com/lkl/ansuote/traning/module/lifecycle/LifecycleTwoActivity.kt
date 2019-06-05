package com.lkl.ansuote.traning.module.lifecycle

import android.os.Bundle
import android.util.Log
import com.lkl.ansuote.hdqlibrary.base.BaseActivity
import com.lkl.ansuote.traning.R

/**
 *
 *
 * @author huangdongqiang
 * @date 03/06/2019
 */
class LifecycleTwoActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.lifecycle_two_activity)
        Log.i("lkl", "LifecycleTwoActivity -- onCreate()")
        //finish()
    }


    override fun onStart() {
        //finish()
        super.onStart()
        Log.i("lkl", "LifecycleTwoActivity -- onStart()")
    }

    override fun onResume() {
        super.onResume()
        //界面不会一闪而过，调用 onResume -- onPause -- onStop -- onDestroy
        finish()
        Log.i("lkl", "LifecycleTwoActivity -- onResume()")
    }

    /**
     *  直接启动该界面，不会调用一下的方法。
     */
    override fun onPause() {
        //finish()
        super.onPause()
        Log.i("lkl", "LifecycleTwoActivity -- onPause()")
    }

    override fun onStop() {
        //finish()
        super.onStop()
        Log.i("lkl", "LifecycleTwoActivity -- onStop()")
    }

    override fun onDestroy() {
        //finish()
        super.onDestroy()
        Log.i("lkl", "LifecycleTwoActivity -- onDestroy()")
    }


}
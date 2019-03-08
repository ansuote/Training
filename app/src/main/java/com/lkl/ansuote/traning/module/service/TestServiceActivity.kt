package com.lkl.ansuote.traning.module.service

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.lkl.ansuote.hdqlibrary.base.BaseActivity
import com.lkl.ansuote.traning.R
import kotlinx.android.synthetic.main.test_service_activity.*

/**
 *
 *
 * @author huangdongqiang
 * @date 08/03/2019
 */
class TestServiceActivity: BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_service_activity)

        btn_start_service?.setOnClickListener {
            TestService.actionStart(this)
        }

        btn_bind_service?.setOnClickListener {
            TestService.actionBind(this, mConn)
        }

        btn_stop_service?.setOnClickListener {
            this.stopService(Intent(this, TestService::class.java))
        }


        btn_unbind_service?.setOnClickListener {
            /**
             * unbind 必须捕捉异常，防止多次解绑报错的情况
             */
            try {
                this.unbindService(mConn)
            } catch (ex: Exception) {

            }
        }
    }

    var mConn = object : ServiceConnection{

        /**
         * Service的onBind方法返回值不为null时，才会被调用
         * 在主线程
         * @param name ComponentName?
         * @param service IBinder?
         */
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.i("lkl", "onServiceConnected -- 所在线程 -- ${Thread.currentThread()}")
            (service as? TestService.MyBinder)?.getService()?.let {
                it.doServiceWork()
            }
        }

        /**
         * //这个方法只有出现异常时才会调用，服务器正常退出不会调用。
         * @param name ComponentName?
         */
        override fun onServiceDisconnected(name: ComponentName?) {
            Log.i("lkl", "onServiceDisconnected 所在线程 -- ${Thread.currentThread()}")
        }
    }
}
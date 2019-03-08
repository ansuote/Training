package com.lkl.ansuote.traning.module.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.IBinder
import android.util.Log

/**
 *
 *
 * @author huangdongqiang
 * @date 08/03/2019
 */
class TestService : Service(){
    private var mMyBinder = MyBinder()
    private var mListener: OnServiceListener? = null


    override fun onCreate() {
        Log.i("lkl", "TestService -- onCreate")
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("lkl", "TestService -- onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }


    override fun onBind(intent: Intent?): IBinder? {
        Log.i("lkl", "TestService -- onBind")
        return mMyBinder
    }

    inner class MyBinder : Binder() {

        fun getService(): TestService {
            return this@TestService
        }
    }

    /**
     * activity 可以调用该方法
     */
    fun doServiceWork() {
        Log.i("lkl", "TestService -- doServiceWork -- 这是 service 里面的方法")
    }


    fun setOnServiceListener(listener: OnServiceListener) {
        mListener = listener
    }

    interface OnServiceListener {
        fun onDestory()
    }

    companion object {

        fun actionStart(context : Context?) {
            context?.startService(Intent(context, TestService::class.java))
        }


        fun actionBind(context: Context?, conn: ServiceConnection) {
            context?.bindService(Intent(context, TestService::class.java), conn, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onDestroy() {
        Log.i("lkl", "TestService -- onDestroy()")

        mListener?.onDestory()
        super.onDestroy()
    }
}
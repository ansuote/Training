package com.lkl.ansuote.traning.module.bus

import android.arch.lifecycle.Observer
import android.os.Bundle
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ServiceUtils
import com.blankj.utilcode.util.ToastUtils
import com.lkl.ansuote.hdqlibrary.base.BaseActivity
import com.lkl.ansuote.modulebus.LiveEventBus
import com.lkl.ansuote.traning.R
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.bus_test_activity.*
import java.util.concurrent.TimeUnit

/**
 *
 * 总线测试
 * @author huangdongqiang
 * @date 22/01/2019
 */
const val EVENT_TEST_UI = "event_ui"
const val EVENT_TEST_THREAD = "event_test_thread"
const val EVENT_TEST_TO_SERVICE = "event_test_to_service"
const val EVENT_TEST_STICKY = "event_test_sticky"
const val EVENT_TEST_ACTIVATED = "event_test_activated"

class BusTestActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bus_test_activity)

        initBus()

        initView()


    }

    private fun initBus() {
        //LiveEventBus.get().with(EVENT_TEST_UI, String::class.java).observe
//        LiveEventBus.get()
//                .with(EVENT_TEST_UI, String::class.java)
//                .observe(this, object : Observer<String> {
//                    override fun onChanged(s: String?) {
//                        Toast.makeText(this@BusTestActivity, s, Toast.LENGTH_SHORT).show()
//                    }
//                })

        LiveEventBus.get()
                .with(EVENT_TEST_UI, String::class.java)
                .observe(this, Observer<String> {
                    ToastUtils.showShort(it + " \n接收处： -- " + Thread.currentThread())
                })

        LiveEventBus.get()
                .with(EVENT_TEST_THREAD, String::class.java)
                .observe(this, Observer<String> {
                    ToastUtils.showShort(it + " \n接收处： -- " + Thread.currentThread())
                })

        LiveEventBus.get()
                .with(EVENT_TEST_ACTIVATED, String::class.java)
                .observe(this, Observer {
                    ToastUtils.showShort(it)

                })
    }

    private fun initView() {
        btn_in_ui.setOnClickListener {
            LiveEventBus.get()
                    .with(EVENT_TEST_UI, String::class.java)
                    .setValue("我在主线程发送消息")
        }

        btn_in_thread.setOnClickListener {_ ->
            Observable.just("我在子线程发送消息")
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        //LogUtils
                        LiveEventBus.get().with(EVENT_TEST_THREAD, String::class.java).postValue(it + " -- " + Thread.currentThread())
                    }, {

                    })
        }

        btn_start_by_hand_service.setOnClickListener {
            ServiceUtils.startService(BusTestByHandService::class.java)

            ToastUtils.showShort("开启手动注册 service")
        }

        tv_to_service.setOnClickListener {
            LiveEventBus.get().with(EVENT_TEST_TO_SERVICE, String::class.java).setValue("我发送到 service")
        }

        btn_start_lifecycle_owner_service.setOnClickListener {
            ServiceUtils.startService(BusTestAutoService::class.java)
        }

        btn_stop_all_service.setOnClickListener {
            ServiceUtils.stopService(BusTestByHandService::class.java)
            ServiceUtils.stopService(BusTestAutoService::class.java)
        }

        tv_sticky.setOnClickListener {
            LiveEventBus.get().with(EVENT_TEST_STICKY, String::class.java).setValue("黏性广播 -- 在发送消息后，再注册")

            //在发送消息后，再注册
            LiveEventBus.get()
                    .with(EVENT_TEST_STICKY, String::class.java)
                    .observeSticky(this, Observer {
                        ToastUtils.showShort(it)
                    })
        }

        tv_activated.setOnClickListener {_ ->
            ActivityUtils.startHomeActivity()
            Flowable.timer(2, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        LiveEventBus.get().with(EVENT_TEST_ACTIVATED, String::class.java).setValue("测试当前 activity 不活跃状态时候")
                    }
        }
    }
}

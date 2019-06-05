package com.lkl.ansuote.traning.module.lifecycle

import android.os.Bundle
import com.blankj.utilcode.util.ActivityUtils
import com.lkl.ansuote.hdqlibrary.base.BaseActivity
import com.lkl.ansuote.traning.R
import kotlinx.android.synthetic.main.lifecycle_one_activity.*

/**
 *
 *
 * @author huangdongqiang
 * @date 03/06/2019
 */
class LifecycleOneActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.lifecycle_one_activity)


        btn_start_two?.setOnClickListener {
            ActivityUtils.startActivity(LifecycleTwoActivity::class.java)
        }
    }
}
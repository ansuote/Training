package com.lkl.ansuote.module.arouter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.lkl.ansuote.module.R

/**
 * Created by huangdongqiang on 2018/10/5.
 */
@Route(path = "/com/lkl/ansuote/training/ARouterJumpActivity")
class ARouterJumpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.arouter_jump_activity)
    }
}
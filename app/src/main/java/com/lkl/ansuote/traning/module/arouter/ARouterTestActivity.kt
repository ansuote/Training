package com.lkl.ansuote.traning.module.arouter

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.lkl.ansuote.hdqlibrary.base.BaseActivity
import com.lkl.ansuote.traning.R
import com.lkl.ansuote.traning.core.base.Constants

/**
 * Created by huangdongqiang on 2018/10/5.
 */
class ARouterTestActivity : BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.arouter_test_activity)

        //ARouter inject 注入
        ARouter.getInstance().inject(this)
    }

    fun startARouterJumpActivity(v: View) {
        ARouter.getInstance()
                .build(Constants.URL_AROUTER_JUMP_ACTIVITY)
                .navigation()
        //actionStrat(this, ARouterJumpActivity::class.java)
    }

}
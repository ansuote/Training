package com.lkl.ansuote.traning.module.tinker

import android.os.Bundle
import android.widget.TextView
import com.lkl.ansuote.hdqlibrary.base.BaseActivity

/**
 *
 *
 * @author huangdongqiang
 * @date 01/02/2019
 */
class TinkerJumpActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(TextView(this).apply {
            this.text = "这是个跳转的 activity"
        })
    }
}
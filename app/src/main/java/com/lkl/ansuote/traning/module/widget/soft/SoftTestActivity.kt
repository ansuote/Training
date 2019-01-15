package com.lkl.ansuote.traning.module.widget.soft

import android.os.Bundle
import com.lkl.ansuote.hdqlibrary.base.BaseActivity
import com.lkl.ansuote.traning.R

/**
 *
 * 输入法相关测试
 * @author huangdongqiang
 * @date 22/11/2018
 */
class SoftTestActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * 按钮始终在输入法上面
         */
        // RelativeLayout 模式
        setContentView(R.layout.soft_test_one_activity)
        // LinearLayout 模式，在布局长度超过屏幕的时候，不满足该需求。
//        setContentView(R.layout.soft_test_two_activity)


    }
}

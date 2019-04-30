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
        // 如果设置了沉浸式：
        // localLayoutParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or localLayoutParams.flags
        // 还必须解决输入法的bug
        // KeyboardUtils.fixAndroidBug5497(this)


        // LinearLayout 模式，在布局长度超过屏幕的时候，不满足该需求。
//        setContentView(R.layout.soft_test_two_activity)


    }
}

package com.lkl.ansuote.traning.module.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ScreenUtils
import com.lkl.ansuote.hdqlibrary.base.BaseActivity
import com.lkl.ansuote.traning.R
import kotlinx.android.synthetic.main.login_test_activity.*

/**
 *
 * 实现登录界面： 登录按钮一直在输入法上面。同时 logo 图片缩放
 * 参考例子： https://github.com/wenzhihao123/Android-loginsmooth-master
 * @author huangdongqiang
 * @date 19/02/2019
 */
class LoginTestActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_test_activity)

        KeyboardUtils.registerSoftInputChangedListener(this) { keyboardHeight ->
            if (keyboardHeight > 0) {
                //打开软键盘
                val location = IntArray(2)
                layout_body.getLocationOnScreen(location)
                //layout_body 的左上角的坐标
                val x = location[0]
                val y = location[1]

                //垂直方向，layout_body 下面的剩余高度
                val leftHeight = ScreenUtils.getScreenHeight() - (y + layout_body.height) - BarUtils.getStatusBarHeight()

                //如果高度小于软键盘高度，则需要向上平移，才不会被键盘遮挡
                if (leftHeight < keyboardHeight) {
                    moveVertical(layout_body, 0.0f, -(keyboardHeight - leftHeight).toFloat())
                    moveAndScale(image_logo, 0.0f, -(keyboardHeight - leftHeight).toFloat(), 1.0f, 0.7f)
                }
            } else {
                //关闭软键盘
                //移动会原位置
                moveVertical(layout_body, layout_body.translationY, 0.0f)
                moveAndScale(image_logo, image_logo.translationY, 0.0f, 0.7f, 1.0f)
            }
        }
    }

    /**
     * 竖直方向移动
     * @param start Float
     * @param end Float
     */
    private fun moveVertical(view: View, start: Float, end: Float) {
        ObjectAnimator.ofFloat(view, "translationY", start, end).apply {
            this.duration = 300
            this.interpolator = AccelerateDecelerateInterpolator()
            this.start()
        }
    }

    /**
     * 组合动画，平移而且缩放
     * @param view View
     * @param startY Float
     * @param endY Float
     * @param startScale Float
     * @param endScale Float
     */
    private fun moveAndScale(view: View, startY: Float, endY: Float, startScale: Float, endScale: Float) {
        AnimatorSet().apply {
            this.play(ObjectAnimator.ofFloat(view, "translationY", startY, endY))
                    .with(ObjectAnimator.ofFloat(view, "scaleX", startScale, endScale))
                    .with(ObjectAnimator.ofFloat(view, "scaleY", startScale, endScale))
            this.duration = 300
            this.start()
        }


    }

    override fun onDestroy() {
        KeyboardUtils.unregisterSoftInputChangedListener(this)
        super.onDestroy()
    }

}
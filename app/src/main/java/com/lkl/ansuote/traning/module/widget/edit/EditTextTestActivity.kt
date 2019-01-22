package com.lkl.ansuote.traning.module.widget.edit

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import com.blankj.utilcode.util.ToastUtils
import com.lkl.ansuote.hdqlibrary.base.BaseActivity
import com.lkl.ansuote.hdqlibrary.util.view.EditTextUtil.setCanCopy
import com.lkl.ansuote.hdqlibrary.util.view.EditTextUtil.setInputExceptBlank
import com.lkl.ansuote.hdqlibrary.util.view.EditTextUtil.setMaxSize
import com.lkl.ansuote.hdqlibrary.util.view.EditTextUtil.setOnlyRead
import com.lkl.ansuote.hdqlibrary.util.view.EditTextUtil.setPwdMethod
import com.lkl.ansuote.hdqlibrary.util.view.EditTextUtil.setSelectionEnd
import com.lkl.ansuote.traning.R
import kotlinx.android.synthetic.main.edit_text_test_activity.*



/**
 *
 * EditText 相关属性
 *
 * https://blog.csdn.net/zhaokaiqiang1992/article/details/39761461#commentBox    (【Android开发经验】android:windowSoftInputMode属性详解 )
 * https://developer.android.com/guide/topics/manifest/activity-element
 * https://www.jianshu.com/p/dddcaac97cdc   (Android 软键盘之 windowSoftInputMode 分析)
 *
 * @author huangdongqiang
 * @date 15/01/2019
 */
class EditTextTestActivity : BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.edit_text_test_activity)

        //当设置属性为stateUnspecified的时候，系统是默认不弹出软键盘的，但是当有获得焦点的输入框的界面有滚动的需求的时候，会自动弹出软键盘

        //hideSoftInputWhenIncoming()

        initEditSearch()

        setOnlyRead(edit_other, true)
        setCanCopy(edit_other, false)


        setMaxSize(edit_other, 10)

        setInputExceptBlank(edit_other)

        setPwdMethod(edit_other, true)

        setSelectionEnd(edit_other)
    }


    /**
     * EditText 搜索模式，并且点击搜索按钮，回调
     */
    private fun initEditSearch() {
        edit_search?.setOnEditorActionListener { v, actionId, event ->
            //当actionId == XX_SEND 或者 XX_DONE时都触发
            //或者event.getKeyCode == ENTER 且 event.getAction == ACTION_DOWN时也触发
            //注意，这是一定要判断event != null。因为在某些输入法上会返回null。
            if (actionId == EditorInfo.IME_ACTION_SEND
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || (event != null && KeyEvent.KEYCODE_ENTER == event.keyCode && KeyEvent.ACTION_DOWN == event.action)) {
                //处理事件
                ToastUtils.showShort(v.text)
            }
            false
        }
    }

    /**
     * 默认不弹出输入法
     */
    private fun hideSoftInputWhenIncoming() {
        /**
         *
         * 方法一
         *  android:windowSoftInputMode="stateHidden"  stateAlwaysHidden
         *
         *
         *
         * 方式二：在 EditText 前面的控件，或者父控件获取焦点。可以用 xml 或者 java 的方式
         *
         *  xml
         *  LinearLayout
         *  android:focusable="true"
         *  android:focusableInTouchMode="true"
         *
         *  java
         */
        layout_edit?.isFocusable = true
        layout_edit?.isFocusableInTouchMode = true


        /**
         * 方式三：设置EditText属性，不过会导致点击的时候，也不能弹出输入法
         */
        //edit_soft?.inputType = InputType.TYPE_NULL
    }
}
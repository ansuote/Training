package com.lkl.ansuote.traning.module.widget

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.ToastUtils
import com.lkl.ansuote.hdqlibrary.base.BaseActivity
import com.lkl.ansuote.hdqlibrary.util.Utils
import com.lkl.ansuote.traning.R
import kotlinx.android.synthetic.main.widget_test_activity.*

/**
 *
 * widget 的测试界面  textView ,  button , theme
 * @author huangdongqiang
 * @date 17/01/2019
 */
class WidgetTestActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.widget_test_activity)
        initView()
    }

    private fun initView() {
        tv_wildcard?.text = getString(R.string.widget_text_wildcard, "这个第一个参数", 80, 66.666f)

        tv_color?.text = Utils.fromHtml(getString(R.string.widget_text_color))

        tv_line?.apply {
            this.paint.flags = Paint.UNDERLINE_TEXT_FLAG
            this.paint.isAntiAlias = true
        }

        tv_line_click?.apply {
            this.text = "后面的文本可以点击："
            val clickText = "快点点击我呀～"
            this.append(SpannableString(clickText).apply {
                this.setSpan(object : ClickableSpan() {
                    override fun onClick(widget: View?) {
                        ToastUtils.showShort("点击成功")
                    }

                    override fun updateDrawState(ds: TextPaint?) {
                        super.updateDrawState(ds)
                        ds?.color = Color.GREEN
                        ds?.isUnderlineText = true
                    }

                }, 0, clickText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            })
            this.highlightColor = Color.TRANSPARENT //设置点击后的颜色为透明，否则会一直出现高亮
            this.movementMethod = LinkMovementMethod.getInstance()//开始响应点击事件
        }

        initCheckbox()

        Log.i("lkl", "获取控件高度 = " + SizeUtils.getMeasuredHeight(tv_wildcard))

    }

    private fun initCheckbox() {

//        Checkbox 在 RecyclerView onBindViewHolder 的时候，需要防止复用view的时候，进入状态变化回调
//          //1.先注销变化监听
//        holder.checkbox.setOnCheckedChangeListener(null);
//          //2.在设置数据
//        holder.checkbox.setChecked(imageEntity.isChecked());
//          //3.在重新注册变化监听
//        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//            }
//        });
    }
}
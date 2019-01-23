package com.lkl.ansuote.traning.module.kotlintest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.lkl.ansuote.traning.R
import kotlinx.android.synthetic.main.kotlin_test_activity.*

/**
 *
 *
 * @author huangdongqiang
 * @date 12/07/2018
 */
class KotlinTestActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.kotlin_test_activity)

        tv_click.setOnClickListener(object: View.OnClickListener {
            override fun onClick(p0: View?) {
                Toast.makeText(this@KotlinTestActivity, "第 1 种监听方式", Toast.LENGTH_SHORT).show()
            }
        })


        tv_click.setOnClickListener { Toast.makeText(this@KotlinTestActivity, "第 2 种监听方式", Toast.LENGTH_SHORT).show() }

    }



}
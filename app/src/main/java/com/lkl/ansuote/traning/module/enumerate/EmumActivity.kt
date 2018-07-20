package com.lkl.ansuote.traning.module.enumerate

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.lkl.ansuote.traning.core.base.Constants

/**
 * 注解 代替 枚举 测试
 *
 * @author huangdongqiang
 * @date 11/07/2018
 */
class EmumActivity : AppCompatActivity(){
    @Constants.Gender.GenderType
    var genderType : Int = Constants.Gender.UNKNOWN

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        //不会有类型限制
        //genderType = ContextCompat.getColor(this, R.color.colorPrimary)
        //编译时限制类型
        //setGender(123)

        when (genderType) {
            Constants.Gender.UNKNOWN -> {

            }
            Constants.Gender.FEMALE -> {

            }
            Constants.Gender.MALE -> {

            }
            else -> {}
        }


    }

    /**
     * 方法使用是会检查类型
     */
    private fun setGender(@Constants.Gender.GenderType gender: Int) {

    }
}
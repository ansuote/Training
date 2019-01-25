package com.lkl.ansuote.traning.core.bean

import android.databinding.ObservableField
import android.databinding.ObservableInt

/**
 *
 * 单向绑定 - Observable fields 方式
 * @author huangdongqiang
 * @date 24/01/2019
 */
class UserDataFileds {
    var userName = ObservableField<String>()
    var age = ObservableInt()
}
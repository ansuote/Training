package com.lkl.ansuote.traning.core.bean

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.lkl.ansuote.traning.BR

/**
 *
 * 单向绑定 - Observable 方式
 * @author huangdongqiang
 * @date 24/01/2019
 */
class UserDataObservable:BaseObservable() {

    @get:Bindable
    var userName: String ?= null
    set(value) {
        field = value
        //刷新单个属性
        notifyPropertyChanged(BR.userName)

        //刷新全部数据
        //notifyChange()
    }


}
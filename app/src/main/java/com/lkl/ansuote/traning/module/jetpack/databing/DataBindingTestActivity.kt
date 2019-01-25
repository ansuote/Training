package com.lkl.ansuote.traning.module.jetpack.databing

import android.databinding.DataBindingUtil
import android.databinding.ObservableArrayMap
import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.lkl.ansuote.hdqlibrary.base.BaseActivity
import com.lkl.ansuote.traning.R
import com.lkl.ansuote.traning.core.bean.UserDataBean
import com.lkl.ansuote.traning.core.bean.UserDataFileds
import com.lkl.ansuote.traning.core.bean.UserDataObservable
import com.lkl.ansuote.traning.databinding.DataBindingTestActivityBinding

/**
 *
 *
 * @author huangdongqiang
 * @date 24/01/2019
 */
const val USER_NAME_DEFAULT = "原始姓名：lkl"
const val USER_NAME_CHANGED = "修改姓名 ansuote"

class DataBindingTestActivity: BaseActivity() {
    private val userdataV1 = UserDataBean(USER_NAME_DEFAULT, 18)
    private val userDataV2 = UserDataObservable().apply {
        userName = USER_NAME_DEFAULT
    }
    private val userDataV3 = UserDataFileds().apply {
        userName.set(USER_NAME_DEFAULT)
        age.set(18)
    }
    private val userDataV4 = ObservableArrayMap<String, Any>().apply {
        put("userName", USER_NAME_DEFAULT)
        put("age", 18)
    }


    private lateinit var binding: DataBindingTestActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<DataBindingTestActivityBinding>(this, R.layout.data_binding_test_activity)

        binding.userdatav1 = userdataV1
        binding.activity = this
        binding.callback = object : TestClickCallback{
            override fun onClick(view: View, bean: UserDataBean) {

                ToastUtils.showShort(bean.name + " -- view.toString() = " + view.toString())
            }
        }

        binding.userdatav2 = userDataV2
        binding.userdatav3 = userDataV3
        binding.userdatav4 = userDataV4
    }

    fun onClickBtn(view: View) {
        userdataV1.name = USER_NAME_CHANGED

        //没有自动刷新，需要手动刷新
        binding.userdatav1 = userdataV1
    }

    /**
     * 单向绑定（ BaseObservable 方式）可以刷新单个属性 ／ 全部属性
     * @param view View
     */
    fun onClickBaseObservableBtn(view: View) {
        userDataV2.userName = USER_NAME_CHANGED

    }

    /**
     * 单向绑定（ ObservableField 方式）
     * @param view View
     */
    fun onClickObservableFieldBtn(view: View) {
        userDataV3.age.set((Math.random() * 100).toInt())
        userDataV3.userName.set(USER_NAME_CHANGED)
    }

    /**
     * 单向绑定（ Collections 方式）
     * @param view View
     */
    fun onClickObservableCollectionsBtn(view: View) {
        userDataV4["userName"] = USER_NAME_CHANGED
        userDataV4["age"] = (Math.random() * 100).toInt()
    }
}
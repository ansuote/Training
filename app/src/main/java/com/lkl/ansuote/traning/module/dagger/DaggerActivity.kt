package com.lkl.ansuote.traning.module.dagger

import android.os.Bundle
import com.lkl.ansuote.hdqlibrary.base.BaseActivity
import com.lkl.ansuote.traning.R
import com.lkl.ansuote.traning.module.dagger.scope.Normal
import com.lkl.ansuote.traning.module.dagger.scope.Special
import com.orhanobut.logger.Logger
import javax.inject.Inject
import javax.inject.Named

/**
 *
 * Dagger 测试
 * @author huangdongqiang
 * @date 08/08/2018
 */


const val NAME_1 = "name_1"
const val NAME_2 = "name_2"


const val NAME_MANAGER_1 = "name_manager_1"
const val NAME_MANAGER_2 = "name_manager_2"

class DaggerActivity:BaseActivity() {

    @Inject
    @field:Named(NAME_MANAGER_1)
    lateinit var daggerManager: DaggerManager

    @Inject
    @field:Named(NAME_MANAGER_1)
    lateinit var daggerManager00: DaggerManager


    @Inject
    //区分：方法1
    //@field:Named(NAME_1)
    //区分：方法2
    @field:Normal
    lateinit var name1String: String

    @Inject
    //区分：方法1
    //@field:Named(NAME_2)
    //区分：方法2
    @field:Special
    lateinit var name2String: String

    @Inject
    @field:Named(NAME_MANAGER_2)
    lateinit var daggerManager3: DaggerManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dagger_activity)

        //DaggerDaggerComponent.create().inject(this)
        DaggerDaggerComponent.builder().daggerModule(DaggerModule(this)).build().inject(this)

        daggerManager.doWork()
        Logger.i("默认注入多次，生成多个对象 -- " + daggerManager.toString() + "; " + daggerManager.localApi.toString())
        Logger.i("默认注入多次，生成多个对象 -- " + daggerManager00.toString() + "; " + daggerManager.localApi.toString())


        Logger.i(" 使用 @field:Named 区分不通的提供者 --  name1String = $name1String + ; name2String = $name2String")

        //daggerManager2.doTestNamed()
        daggerManager3.doTestNamed()
    }
}
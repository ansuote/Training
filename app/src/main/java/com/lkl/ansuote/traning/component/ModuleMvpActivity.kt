package com.lkl.ansuote.traning.component

import com.lkl.ansuote.hdqlibrary.mvp.BasePresenter
import com.lkl.ansuote.modulebussiness.base.BusinessMvpActivity
import com.lkl.ansuote.modulemodel.base.ModuleModel
import com.lkl.ansuote.modulemodel.di.module.ActivityModule

/**
 *
 * 模块的 activity 基类
 * @author huangdongqiang
 * @date 15/12/2018
 */
abstract class BaseMainMvpActivity<V, P : BasePresenter<V>> : BusinessMvpActivity<V, P, MainComponent>() {

    override fun getActivityComponent(): MainComponent {
        return DaggerMainComponent
                .builder()
                .appComponent(ModuleModel.getAppComponent())
                .activityModule(ActivityModule(this))
                .build()
    }
}
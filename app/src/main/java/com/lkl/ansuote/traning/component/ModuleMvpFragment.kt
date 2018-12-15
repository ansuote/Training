package com.lkl.ansuote.traning.component

import com.lkl.ansuote.hdqlibrary.mvp.fragment.BaseFragmentPresenter
import com.lkl.ansuote.modulebussiness.base.BusinessMvpFragment
import com.lkl.ansuote.modulemodel.base.ModuleModel
import com.lkl.ansuote.modulemodel.di.module.FragmentModule

/**
 *
 * 模块 fragment 基类
 * @author huangdongqiang
 * @date 15/12/2018
 */
abstract class BaseMvpFragment<P : BaseFragmentPresenter<*>?> : BusinessMvpFragment<P, MainFComponent>(){
    override fun getFragmentComponent(): MainFComponent {
        return DaggerMainFComponent
                .builder()
                .appComponent(ModuleModel.getAppComponent())
                .fragmentModule(FragmentModule(this))
                .build()
    }

}
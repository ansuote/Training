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
abstract class ModuleMvpFragment<P : BaseFragmentPresenter<*>?> : BusinessMvpFragment<P, ModuleFComponent>(){
    override fun getFragmentComponent(): ModuleFComponent {
        return DaggerModuleFComponent
                .builder()
                .appComponent(ModuleModel.getAppComponent())
                .fragmentModule(FragmentModule(this))
                .build()
    }

}
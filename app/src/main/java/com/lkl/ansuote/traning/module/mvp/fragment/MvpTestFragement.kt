package com.lkl.ansuote.traning.module.mvp.fragment

import com.lkl.ansuote.traning.component.ModuleMvpFragment

/**
 *
 *
 * @author huangdongqiang
 * @date 15/12/2018
 */
class MvpTestFragement : ModuleMvpFragment<MvpTestFPresenter>(), MvpTestFContract.View{
    override fun initInject() {
        fragmentComponent.inject(this)
    }

    override fun getLayoutId(): Int {
        return 0
    }

}
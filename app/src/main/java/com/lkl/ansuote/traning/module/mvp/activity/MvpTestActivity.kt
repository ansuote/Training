package com.lkl.ansuote.traning.module.mvp.activity

import com.lkl.ansuote.traning.R
import com.lkl.ansuote.traning.component.ModuleMvpActivity

/**
 *
 *
 * @author huangdongqiang
 * @date 15/12/2018
 */
class MvpTestActivity : ModuleMvpActivity<MvpTestContract.View, MvpTestPresenter>(), MvpTestContract.View {

    override fun getContentLayoutId(): Int {
        return R.layout.mvp_test_activity
    }

    override fun initInject() {
        activityComponent.inject(this)
    }
}
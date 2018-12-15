package com.lkl.ansuote.traning.module.mvp

import com.lkl.ansuote.traning.R
import com.lkl.ansuote.traning.component.BaseAppMvpActivity

/**
 *
 *
 * @author huangdongqiang
 * @date 15/12/2018
 */
class MvpTestActivity : BaseAppMvpActivity<MvpTestContract.View, MvpTestPresenter>(), MvpTestContract.View {

    override fun getContentLayoutId(): Int {
        return R.layout.mvp_test_activity
    }

    override fun initInject() {
        activityComponent.inject(this)
    }
}
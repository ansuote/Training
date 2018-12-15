package com.lkl.ansuote.module.arouter

import com.alibaba.android.arouter.facade.annotation.Route
import com.lkl.ansuote.module.R
import com.lkl.ansuote.module.arouter.component.ModuleMvpActivity

/**
 * Created by huangdongqiang on 2018/10/5.
 */
@Route(path = "/com/lkl/ansuote/training/ARouterJumpActivity")
class ARouterJumpActivity : ModuleMvpActivity<ARouterJumpContract.View, ARouterJumpPresenter>(), ARouterJumpContract.View {
    override fun initInject() {
        activityComponent.inject(this)
    }

    override fun getContentLayoutId(): Int {
        return R.layout.arouter_jump_activity
    }

}
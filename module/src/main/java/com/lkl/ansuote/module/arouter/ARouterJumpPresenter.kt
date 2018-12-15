package com.lkl.ansuote.module.arouter

import android.content.Intent
import android.os.Bundle
import com.lkl.ansuote.hdqlibrary.mvp.BasePresenter
import com.lkl.ansuote.modulemodel.base.ModuleModel
import javax.inject.Inject

/**
 *
 *
 * @author huangdongqiang
 * @date 15/12/2018
 */
class ARouterJumpPresenter @Inject constructor():BasePresenter<ARouterJumpContract.View>(), ARouterJumpContract.Presenter {
    override fun initVariables(savedInstanceState: Bundle?, intent: Intent?) {
    }

    override fun onCreate() {

    }
}
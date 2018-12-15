package com.lkl.ansuote.traning.module.mvp

import android.content.Intent
import android.os.Bundle
import com.lkl.ansuote.hdqlibrary.mvp.BasePresenter
import javax.inject.Inject

/**
 *
 *
 * @author huangdongqiang
 * @date 15/12/2018
 */
class MvpTestPresenter @Inject constructor() : BasePresenter<MvpTestContract.View>(), MvpTestContract.Presenter {
    override fun initVariables(savedInstanceState: Bundle?, intent: Intent?) {
    }

    override fun onCreate() {
    }
}
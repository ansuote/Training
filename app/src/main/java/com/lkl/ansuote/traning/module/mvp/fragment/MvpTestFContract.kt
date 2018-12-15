package com.lkl.ansuote.traning.module.mvp.fragment

import com.lkl.ansuote.hdqlibrary.mvp.IBaseFragmentView
import com.lkl.ansuote.hdqlibrary.mvp.IBasePresenter

/**
 *
 *
 * @author huangdongqiang
 * @date 15/12/2018
 */
interface MvpTestFContract {
    interface View : IBaseFragmentView {

    }

    interface Presenter : IBasePresenter<View> {

    }
}
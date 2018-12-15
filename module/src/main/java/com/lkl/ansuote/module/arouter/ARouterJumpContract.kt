package com.lkl.ansuote.module.arouter

import com.lkl.ansuote.hdqlibrary.mvp.IBaseActivityView
import com.lkl.ansuote.hdqlibrary.mvp.IBasePresenter

/**
 *
 *
 * @author huangdongqiang
 * @date 15/12/2018
 */
interface ARouterJumpContract {
    interface View : IBaseActivityView{

    }
    interface Presenter: IBasePresenter<View> {
        
    }
}
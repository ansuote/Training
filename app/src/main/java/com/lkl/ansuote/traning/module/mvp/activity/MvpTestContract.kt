package com.lkl.ansuote.traning.module.mvp

import com.lkl.ansuote.hdqlibrary.mvp.IBaseActivityView
import com.lkl.ansuote.hdqlibrary.mvp.IBasePresenter

/**
 *
 *
 * @author huangdongqiang
 * @date 15/12/2018
 */
interface MvpTestContract {
    interface View : IBaseActivityView{

    }

    interface Presenter: IBasePresenter<View>{
        
    }

}
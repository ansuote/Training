package com.lkl.ansuote.traning.component

import com.lkl.ansuote.modulemodel.di.component.AppComponent
import com.lkl.ansuote.modulemodel.di.module.ActivityModule
import com.lkl.ansuote.modulemodel.di.scope.ActivityScope
import com.lkl.ansuote.traning.module.mvp.activity.MvpTestActivity
import dagger.Component

/**
 *
 * 模块inject自己 activity
 * @author huangdongqiang
 * @date 15/12/2018
 */
@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [ActivityModule::class])
interface MainComponent {
    fun inject(activity: MvpTestActivity)
}
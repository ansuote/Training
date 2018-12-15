package com.lkl.ansuote.module.arouter.component

import com.lkl.ansuote.module.arouter.ARouterJumpActivity
import com.lkl.ansuote.modulemodel.di.component.AppComponent
import com.lkl.ansuote.modulemodel.di.module.ActivityModule
import com.lkl.ansuote.modulemodel.di.scope.ActivityScope
import dagger.Component

/**
 *
 * 模块inject自己 activity
 * @author huangdongqiang
 * @date 15/12/2018
 */
@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [ActivityModule::class])
interface ModuleComponent {
    fun inject(activity: ARouterJumpActivity)
}
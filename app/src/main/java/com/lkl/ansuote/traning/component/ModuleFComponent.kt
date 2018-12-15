package com.lkl.ansuote.traning.component

import com.lkl.ansuote.modulemodel.di.component.AppComponent
import com.lkl.ansuote.modulemodel.di.module.FragmentModule
import com.lkl.ansuote.modulemodel.di.scope.FragmentScope
import com.lkl.ansuote.traning.module.mvp.fragment.MvpTestFragement
import dagger.Component

/**
 *
 *  模块inject自己 fragment
 * @author huangdongqiang
 * @date 15/12/2018
 */
@FragmentScope
@Component(dependencies = [AppComponent::class], modules = [FragmentModule::class])
interface ModuleFComponent {
    fun inject(fragment: MvpTestFragement)

}
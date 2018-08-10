package com.lkl.ansuote.traning.module.dagger

import dagger.Component
import javax.inject.Singleton

/**
 *
 *
 * @author huangdongqiang
 * @date 08/08/2018
 */
@Singleton
@Component(modules = arrayOf(DaggerModule::class))
interface DaggerComponent {

    fun inject(activity: DaggerActivity)
}
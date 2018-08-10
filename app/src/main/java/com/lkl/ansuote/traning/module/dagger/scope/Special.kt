package com.lkl.ansuote.traning.module.dagger.scope

import javax.inject.Qualifier

/**
 *
 *
 * @author huangdongqiang
 * @date 09/08/2018
 */

@Qualifier
//@Documented
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class Special
//可以增加值区分
//annotation class Special(val value: String = "")
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
annotation class Normal
//可以增加值区分
//annotation class Normal(val value: String = "")

package com.lkl.ansuote.traning.module.dagger

import com.orhanobut.logger.Logger

/**
 *
 * 第一种：Inject 构造函数注入
 * @author huangdongqiang
 * @date 08/08/2018
 */
//class DaggerManager @Inject constructor() {
//
//    @Inject
//    lateinit var localApi: LocalApi
//
//    @Inject
//    lateinit var serviceApi: ServiceApi
//
//
//    fun doWork(){
//        Logger.i("doWork")
//
//        localApi.doLocalWork()
//
//        serviceApi.doServiceWork()
//
//    }
//
//}

/**
 * 第二种：@module @Provides 生成构造函数
 */
class DaggerManager constructor(){

    constructor(localApi: LocalApi, serviceApi: ServiceApi) :this(){
        this.localApi = localApi
        this.serviceApi = serviceApi
    }

    /**
     * 多个构造函数，@Named 区分
     */
    constructor(nameTest: String) :this(){
        this.nameTest = nameTest
    }

    lateinit var localApi: LocalApi
    lateinit var serviceApi : ServiceApi
    lateinit var nameTest: String

    fun doWork(){
        Logger.i("doWork")

        localApi.doLocalWork()

        serviceApi.doServiceWork()

    }

    fun doTestNamed() {
        Logger.i(nameTest)
    }
}
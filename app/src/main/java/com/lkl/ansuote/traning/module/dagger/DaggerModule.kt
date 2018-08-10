package com.lkl.ansuote.traning.module.dagger

import android.content.Context
import com.lkl.ansuote.traning.module.dagger.scope.Normal
import com.lkl.ansuote.traning.module.dagger.scope.Special
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

/**
 *
 *
 * @author huangdongqiang
 * @date 08/08/2018
 */
@Module
class DaggerModule(context: Context) {
    private val context = context

    @Provides
    fun provideLocalApi():LocalApi{
        return LocalApi(context)
    }

    @Provides
    fun provideServiceApi():ServiceApi{
        return ServiceApi(context, "www.google.com")
    }


    /**
     * 第二种：@module @Provides 生成构造函数
     */
    @Singleton
    @Provides @Named(NAME_MANAGER_1)
    fun provideDaggerManager1(localApi: LocalApi, serviceApi: ServiceApi):DaggerManager {
        return DaggerManager(localApi, serviceApi)
    }

    @Singleton
    @Provides @Named(NAME_MANAGER_2)
    fun provideDaggerManager2(): DaggerManager {
        return DaggerManager("www.ansuote.lkl")
    }

//    @Provides
//    fun provideNameTest(): String{
//        return "我在测试 @Named 哈"
//    }

    @Provides
    //区分：方法1
    //@Named(NAME_1)
    //区分：方法2
    @Normal
    fun provideNormalString(): String{
        return "Name1 测试"
    }

    @Provides
    //区分：方法1
    //@Named(NAME_2)
    //区分：方法2
    @Special
    fun provideSpecialString(): String{
        return "Name2 测试"
    }
}
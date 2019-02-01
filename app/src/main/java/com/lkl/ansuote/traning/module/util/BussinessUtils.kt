package com.lkl.ansuote.traning.module.util

import com.tencent.bugly.beta.Beta

/**
 *
 *
 * @author huangdongqiang
 * @date 31/01/2019
 */
object BussinessUtils {

    /**
     * bugly 检查升级
     * @param isManual  用户手动点击检查，非用户点击操作请传false
     * @param isSilence 是否显示弹窗等交互，[true:没有弹窗和toast] [false:有弹窗或toast]
     */
    fun checkUpgrade(isManual: Boolean, isSilence: Boolean) {
        Beta.checkUpgrade(isManual, isSilence)
    }
}
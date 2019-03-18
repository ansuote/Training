package com.lkl.ansuote.traning.module.socket.callback

/**
 *
 * socket 写入后的回调
 * @author huangdongqiang
 * @date 14/03/2019
 */
interface HSocketWriteCallback {
    fun onSuccess()

    fun onError(ex: Exception)
}
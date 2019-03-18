package com.lkl.ansuote.traning.module.socket.callback

/**
 *
 * socket 读取数据回调
 * @author huangdongqiang
 * @date 14/03/2019
 */
interface HSocketReadCallback {

    /**
     * 后台线程回调
     * @param buffer ByteArray
     */
    fun onSuccess(buffer: ByteArray, offset: Int, len: Int, type: String? = null)

    fun onError(ex: Exception)

    fun onClose() {

    }
}
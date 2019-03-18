package com.lkl.ansuote.traning.module.socket

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.blankj.utilcode.util.ImageUtils
import com.lkl.ansuote.traning.module.socket.callback.HSocketReadCallback
import com.lkl.ansuote.traning.module.socket.callback.HSocketWriteCallback

/**
 *
 *
 * @author huangdongqiang
 * @date 14/03/2019
 */
class HSocketClient(host: String, port: Int, readCallback: HSocketReadCallback? = null) {



    private var mSocket = HLongLiveSocket(host, port, readCallback)


    /**
     * 发送数据
     * @param data String
     */
    fun send(data: ByteArray, callback: HSocketWriteCallback) {
        mSocket.send(data, callback)
    }

    /**
     * 发送文本
     * @param string String
     * @param callback HSocketWriteCallback
     */
    fun send(string: String, callback: HSocketWriteCallback) {
        mSocket.send(TYPE_TEXT.toByteArray(), null)
        mSocket.send(string.toByteArray(), callback)
    }

    fun send(drawable: Drawable?, callback: HSocketWriteCallback) {
        if (null != drawable) {
            val data = ImageUtils.drawable2Bytes(drawable, Bitmap.CompressFormat.JPEG)
            mSocket.send(TYPE_JPG.toByteArray(), null)
            mSocket.send(data, callback)
        }
    }

    fun close() {
        mSocket.close()
    }

    companion object {

        val TYPE_TEXT = "String"
        val TYPE_JPG = "jpg"

    }
}
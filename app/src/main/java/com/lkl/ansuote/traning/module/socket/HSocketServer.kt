package com.lkl.ansuote.traning.module.socket

import android.util.Log
import com.blankj.utilcode.util.ThreadUtils
import com.lkl.ansuote.traning.module.socket.HLongLiveSocket.Companion.readToBuffer
import com.lkl.ansuote.traning.module.socket.callback.HSocketReadCallback
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.Socket
import java.util.concurrent.ExecutorService

/**
 *
 * 封装的 socket 服务器程序
 * @author huangdongqiang
 * @date 14/03/2019
 */
class HSocketServer(private val port: Int, var mReadCallback: HSocketReadCallback? = null) {

    private var mCachedPool : ExecutorService = ThreadUtils.getCachedPool()
    private var mSocket: Socket? = null
    private var mServerSocket :ServerSocket? = null
    @Volatile
    private var mClosed: Boolean = false


    /**
     * 发送数据 往客户端
     * @param input String
     */
    fun send(data: ByteArray) {
        val length = data.size
        mCachedPool.submit {
            if (mSocket?.isConnected == true && mSocket?.isClosed == false) {
                mSocket?.getOutputStream()?.let {
                    DataOutputStream(it).apply {
                        this.writeInt(length)
                        this.write(data, 0, length)
                        this.flush()
                    }
                }
            }
        }
    }


    /**
     * 启动服务
     */
    fun run() {
        mCachedPool.submit {
            try {
                //java.net.BindException: Address already in use
                //val serverSocket = ServerSocket (port)

                mServerSocket = ServerSocket().apply {
                    this.reuseAddress = true
                    this.bind(InetSocketAddress(port))
                }

                while (true) {
                    val socket = mServerSocket?.accept()
                    mSocket = socket
                    handleSocket(socket)
                }
            } catch (ex: Exception) {
                if (mClosed) {
                    mReadCallback?.onClose()
                } else {
                    Log.e("lkl", "获取 serverSocket 报错 -- ${ex}")
                    mReadCallback?.onError(ex)
                }
            }
        }
    }

    /**
     * 处理用于服务端和客户端监听连接的 socket
     * @param socket Socket?
     */
    private fun handleSocket(socket: Socket?) {
        if (null == socket) {
            return
        }

        try {
            mCachedPool.submit {
                doHandleSocket(socket)
            }
        } catch (ex: Exception) {
            Log.e("lkl", "处理 serverSocket 报错 -- $ex")
        }
    }

    /**
     * 处理服务端收到的消息
     * @param socket Socket
     */
    private fun doHandleSocket(socket: Socket) {
        var typeString: String? = null
        while (true) {

            //规定数据类型 类型字节数 + 类型文本 + 具体数据字节数 + 具体数据

            val input = socket.getInputStream()
            val dataInput = DataInputStream(input)

            //读取类型
            val typeSize = dataInput.readInt()
            val typeBuffer = ByteArray(typeSize)

            readToBuffer(input, typeBuffer, typeSize)
            typeString = String(typeBuffer, 0, typeSize)

            //获取数据的大小
            val size = dataInput.readInt()
            val buffer = ByteArray(size)
            readToBuffer(input, buffer, size)

            mReadCallback?.onSuccess(buffer, 0, size, typeString)

        }
    }

    fun close() {
        try {
            mServerSocket?.close()
            mServerSocket = null
            mSocket?.close()
            mSocket = null
            mClosed = true
        } catch (ex: Exception) {

        }

    }
}
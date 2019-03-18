package com.lkl.ansuote.traning.module.socket

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import com.blankj.utilcode.util.ThreadUtils
import com.lkl.ansuote.traning.module.socket.callback.HSocketReadCallback
import com.lkl.ansuote.traning.module.socket.callback.HSocketWriteCallback
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.InputStream
import java.net.Socket
import java.util.concurrent.ExecutorService

/**
 *
 * 长连接 socket
 * @author huangdongqiang
 * @date 14/03/2019
 */
class HLongLiveSocket(var host: String, var port: Int, var readCallback: HSocketReadCallback? = null) {
    /**
     * 用于回调接口到主线程
     */
    private var mUIHandler: Handler

    /**
     * 写入队列
     */
    private var mWriteHandler: Handler
    /**
     * 指明写入队列所在线程
     */
    private var mWriteThread: HandlerThread

    @Volatile
    private var mClosed: Boolean = false

    @Volatile
    private var mReconnected: Boolean = false

    private val mLock = Any()

    private var mSocket: Socket? = null

    private var mCachePool : ExecutorService = ThreadUtils.getCachedPool()

    init {

        mWriteThread = HandlerThread("socket-writer").apply {
            this.start()
        }

        mWriteHandler = Handler(mWriteThread.looper).apply {
            this.post {
                initSocket()
            }
        }

        mUIHandler = Handler(Looper.getMainLooper())
    }

    private fun initSocket() {
        while (true) {
            if (mClosed) {
                return
            }

            try {

                synchronized(mLock) {
                    val socket = Socket(host, port)
                    if (mClosed && !mReconnected) {
                        return
                    }

                    mSocket = socket
                    handleSocket(socket)
                }

                return

            } catch (ex: Exception) {
                if (mClosed && !mReconnected) {
                    return
                }

            }

        }
    }

    private fun handleSocket(socket: Socket) {
        //新开线程处理
        mCachePool.submit {
            try {
                doHandleSocket(socket)
            } catch (ex: Exception) {
                mUIHandler.post {
                    if (mClosed) {
                        readCallback?.onClose()
                    } else {
                        readCallback?.onError(ex)
                    }
                }
            }
        }
    }

    /**
     * 读取服务端发送给客户端的消息
     * @param socket Socket
     */
    private fun doHandleSocket(socket: Socket) {
        val input = DataInputStream(socket.getInputStream())



        while (true) {
            //这个方法会阻塞，等待新数据
            val n = input.readInt()
            val buffer = ByteArray(n)

            //心跳包
            if (n == 0) {

                continue
            }

            readToBuffer(input, buffer, n)

            mUIHandler.post {
                readCallback?.onSuccess(buffer, 0, n)
            }
        }
    }


    companion object {
        /**
         * 将数据读入 buffer 缓冲区
         * @param input InputStream
         * @param buffer ByteArray
         * @param size Int
         */
        fun readToBuffer(input: InputStream, buffer: ByteArray, size: Int){
/*            var result = size
            var offset = 0
            while (result > 0) {
                val readByte = input.read(buffer, offset, size)
                if (readByte < 0) {
                    break
                }

                result -= readByte
                offset += readByte
            }
            return result*/

            var offset = 0

            while (offset < size) {
                val readSize = input.read(buffer, offset, size - offset)
                offset += readSize
            }
        }
    }

    fun send(data: ByteArray, callback: HSocketWriteCallback?) {
        mWriteHandler.post {
            if (mSocket?.isConnected == true && mSocket?.isClosed == false) {
                mSocket?.getOutputStream()?.let {
                    if (null == mSocket) {
                        initSocket()

                        //初始化失败
                        if (null == mSocket) {
                            callback?.onError(NullPointerException("socket is not allowed for null"))
                            return@post
                        }
                    }

                    try {
                        val outputStream = mSocket?.getOutputStream()
                        DataOutputStream(outputStream).let {
                            it.writeInt(data.size)
                            it.write(data, 0, data.size)
                            it.flush()
                            mUIHandler.post{
                                callback?.onSuccess()
                            }
                        }

                    }catch (ex: Exception) {
                        mUIHandler.post{
                            callback?.onError(ex)
                        }
                    }

                }
            } else {
                initSocket()
            }
        }
    }


    /**
     * 外部调用，关闭 socket
     */
    fun close() {
        if (ThreadUtils.isMainThread()) {
            Thread {
                doClose()
            }.start()
        } else {
            doClose()
        }
    }

    private fun doClose() {
        try {
            synchronized(mLock) {
                closeSocket()
                //mCachePool.shutdown()
            }
        } catch (ex: Exception) {

        }

    }


    private fun closeSocket() {
        try {
            mSocket?.close()
            mSocket = null
            mClosed = true
        } catch (ex: Exception) {

        }
    }
}
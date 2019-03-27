package com.lkl.ansuote.traning.module.socket

import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.blankj.utilcode.util.ThreadUtils
import com.blankj.utilcode.util.ToastUtils
import com.lkl.ansuote.hdqlibrary.base.BaseActivity
import com.lkl.ansuote.traning.R
import com.lkl.ansuote.traning.module.socket.callback.HSocketReadCallback
import com.lkl.ansuote.traning.module.socket.callback.HSocketWriteCallback
import kotlinx.android.synthetic.main.socket_test_activity.*

/**
 *
 * 利用 socket 实现前后端图文消息发送
 * @author huangdongqiang
 * @date 14/03/2019
 */
class SocketTestActivity : BaseActivity() {
    private var mHSocketServer: HSocketServer? = null
    private var mHSocketClient: HSocketClient? = null
    private var mHost : String = "192.168.31.154"  //localhost  192.168.31.154
    private var mPort: Int = 9038


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.socket_test_activity)

        initView()

        initSocket()
    }

    private fun initSocket() {
        /*mHSocketServer = HSocketServer(mPort, object : HSocketReadCallback {
            override fun onSuccess(buffer: ByteArray, offset: Int, len: Int, type: String?) {
                when (type) {
                    TYPE_JPG -> {
                        this@SocketTestActivity.runOnUiThread {
                            image_pic?.background = ImageUtils.bytes2Drawable(buffer)
                        }

                    }
                    else -> {
                        ToastUtils.showShort("服务端收到数据" + String(buffer, offset, len))
                    }
                }
            }

            override fun onError(ex: Exception) {
                ToastUtils.showShort("服务端接收报错 $ex")
            }

        }).apply {
            this.run()
        }*/

        mHSocketClient = HSocketClient(mHost,mPort, object : HSocketReadCallback {
            override fun onSuccess(buffer: ByteArray, offset: Int, len: Int, type: String?) {
                ToastUtils.showShort("接收到服务端换来的数据 buffer = ${String(buffer, offset, len)}, 当前在主线程 = ${ThreadUtils.isMainThread()}")
            }

            override fun onError(ex: Exception) {
                ToastUtils.showShort( "接收到服务端换来的数据 onError = ${ex.toString()}, 当前在主线程 = ${ThreadUtils.isMainThread()}")
            }

        })
    }

    private fun initView() {
        btn_send_to_service?.setOnClickListener {
            val text = edit_input_to_service?.text?.toString()
            text?.let {
                mHSocketClient?.send(it, object : HSocketWriteCallback {
                    override fun onSuccess() {
                        //ToastUtils.showShort("发送到服务端成功")
                    }

                    override fun onError(ex: Exception) {
                        ToastUtils.showShort("发送到服务端失败 $ex")
                    }

                })
                edit_input_to_service?.setText("")
            }
        }

        btn_send_to_service_pic?.setOnClickListener {
            mHSocketClient?.send(ContextCompat.getDrawable(this, R.mipmap.socket_test_big_pic), object : HSocketWriteCallback {
                override fun onSuccess() {
                    ToastUtils.showShort("发送图片成功")
                }

                override fun onError(ex: Exception) {
                    ToastUtils.showShort("发送图片失败 ${ex}")
                }

            })
        }

        btn_send_to_client?.setOnClickListener {
            val text = edit_input_to_client?.text?.toString()
            text?.let {
                mHSocketServer?.send(it.toByteArray())
            }
            edit_input_to_client?.setText("")
        }
    }

    override fun onDestroy() {
        mHSocketClient?.close()
        mHSocketServer?.close()
        super.onDestroy()
    }

}
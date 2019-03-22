package com.lkl.ansuote.traning.module.share

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.lkl.ansuote.traning.R
import kotlinx.android.synthetic.main.share_activity.*
import java.io.File

/**
 *
 * 原生分享测试
 * @author huangdongqiang
 * @date 19/07/2018
 */
class ShareActivity : AppCompatActivity(), View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.share_activity)
        btn_picture_share.setOnClickListener(this)
        btn_wechat_share?.setOnClickListener(this)
    }


    override fun onClick(view: View?) {
        val imagePath = "/storage/emulated/0/Download/ic_launcher.png"
        //由文件得到uri
        val imageUri =  Uri.fromFile(File(imagePath))
        when(view) {
            btn_picture_share -> {
                //测试写死地址
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri)
                shareIntent.type = "image/*"
                startActivity(Intent.createChooser(shareIntent, "分享到"))
            }
            btn_wechat_share -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.setType("image/*");
                //发送图片到朋友圈
                /*val comp = ComponentName("com.tencent.mm",
                        "com.tencent.mm.ui.tools.ShareToTimeLineUI")*/
                //发送图片给好友。
                val comp = ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI")
                intent.setComponent(comp)
                //intent.setAction("android.intent.action.SEND_MULTIPLE") //该属性会导致分享失败
                intent.setType("image/*")
                //这个就是标题了
                intent.putExtra("Kdescription" + System.currentTimeMillis(), "多图分享的标题")
                intent.putExtra(Intent.EXTRA_STREAM, imageUri)
                startActivity(intent)
            }
        }
    }
}

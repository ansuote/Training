package com.lkl.ansuote.traning.module.share

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
    }


    override fun onClick(view: View?) {
        when(view) {
            btn_picture_share -> {
                //测试写死地址
                val imagePath = "/storage/emulated/0/Download/001.png"
                //由文件得到uri
                val imageUri = Uri.fromFile(File(imagePath))

                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri)
                shareIntent.type = "image/*"
                startActivity(Intent.createChooser(shareIntent, "分享到"))
            }
        }
    }

}

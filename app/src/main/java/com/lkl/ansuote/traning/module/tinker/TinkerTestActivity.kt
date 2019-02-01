package com.lkl.ansuote.traning.module.tinker

import android.content.Intent
import android.os.Bundle
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.ToastUtils
import com.lkl.ansuote.hdqlibrary.base.BaseActivity
import com.lkl.ansuote.traning.R
import com.lkl.ansuote.traning.module.util.BussinessUtils
import com.meituan.android.walle.WalleChannelReader
import com.tencent.bugly.beta.Beta
import kotlinx.android.synthetic.main.tinker_test_activity.*

/**
 *
 * bugly tinker API
 * https://bugly.qq.com/docs/user-guide/api-hotfix/?v=20170504092424
 *
 * @author huangdongqiang
 * @date 30/01/2019
 */
class TinkerTestActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tinker_test_activity)


        tv_tinker_test?.text = "tinker测试 " + AppUtils.getAppVersionName()
        btn_tinker_test?.setOnClickListener {

            //打补丁包修复空指针
            //throw NullPointerException("这是一个空指针")
            ToastUtils.showShort("修复了空指针")
        }

        btn_tinker_channel?.setOnClickListener {
            ToastUtils.showShort("多渠道更新成功，当前渠道是 = " + WalleChannelReader.getChannel(application))
        }

        btn_tinker_protected_app?.setOnClickListener {
            ToastUtils.showShort("加固后更新成功 -- patch --" + AppUtils.getAppVersionName())
        }

        btn_tinker_add_activity?.setOnClickListener {
            /**
             * what：使用 ActivityUtils.startActivity 会导致无法启动 Activity ，提示报错 intent is unavailable
             * when：ActivityUtils.startActivity
             * why： activity是动态增加的，工具类里面方法 isIntentAvailable，找不到当前的 intent，所以提示报错
             * how： 使用原生的方式打卡界面
             */
            //ActivityUtils.startActivity(TinkerJumpActivity::class.java)

            // 需要指定 android:exported="false"
            startActivity(Intent(this, TinkerJumpActivity::class.java))
        }

        btn_tinker_update?.setOnClickListener {
            /**
             * 同一个基线版本，检测到 patch 升级完，更新新的 patch ，可以检测升级（不过 bugly 的检测有延迟，10分钟至少）。
             */
            // 设置是否显示弹窗提示用户重启。默认为false，如果想弹窗提示用户重启，设置为true即可。
            Beta.canNotifyUserRestart = true
//            Beta.checkUpgrade()
            BussinessUtils.checkUpgrade(true, false)
        }

        btn_tinker_clear?.setOnClickListener {
            //清除补丁之后，就会回退基线版本状态
            //经测试，回退后，重启APP检测到之前的插件版本（不过 bugly 的检测有延迟，10分钟至少）。
            Beta.cleanTinkerPatch(true)
        }
    }
}
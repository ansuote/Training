package com.lkl.ansuote.traning.module.addressbook

import android.os.Bundle
import android.util.Log
import android.view.View
import com.lkl.ansuote.hdqlibrary.base.BaseActivity
import com.lkl.ansuote.hdqlibrary.util.helper.PermissionHelper
import com.lkl.ansuote.traning.R
import com.lkl.ansuote.traning.core.base.PhoneContact
import com.lkl.ansuote.traning.module.addressbook.AddressBookUtil.getGroups
import com.lkl.ansuote.traning.module.addressbook.AddressBookUtil.isPhoneExist
import com.lkl.ansuote.traning.module.addressbook.AddressBookUtil.syncContacts
import kotlinx.android.synthetic.main.address_book_activity.*


/**
 *
 * 系统通讯录测试界面
 * @author huangdongqiang
 * @date 23/07/2018
 *
 * 参考文章
 * https://blog.csdn.net/xiabing082/article/details/44084511
 * https://my.oschina.net/moziqi/blog/367025
 */
class AddressBookActivity : BaseActivity(), View.OnClickListener{
    var hasPermission = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.address_book_activity)
        btn_query_group_data.setOnClickListener(this)
        btn_query_phone.setOnClickListener(this)
        btn_insert_phone.setOnClickListener(this)
        btn_query_by_group_id.setOnClickListener(this)
        PermissionHelper.requestContacts {
            hasPermission = true
        }
    }

    override fun onClick(v: View?) {
        if (!hasPermission) {
            return
        }

        when (v) {
            btn_query_group_data -> {
                val groups = getGroups(this)
                Log.i("lkl", "groups = $groups")

            }
            btn_query_phone -> {
                var found = isPhoneExist(this, "13580129339")
                Log.i("lkl", "found = $found")
            }
            btn_insert_phone -> {
                syncContacts(this, arrayListOf<PhoneContact>().apply {
//                    add(PhoneContact().apply { phoneNumber = "556677";  grounpTitle = "ok" })
//                    add(PhoneContact().apply { phoneNumber = "667788";  grounpTitle = "ok" })
//                    add(PhoneContact().apply { phoneNumber = "778899";  grounpTitle = "ok" })
                    add(PhoneContact().apply { phoneNumber = "889900";  grounpTitle = "ok" })


                })
            }
            btn_query_by_group_id -> {
                AddressBookUtil.getMemberForGroupId(this, "8")

            }
        }
    }






}
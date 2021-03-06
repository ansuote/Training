package com.lkl.ansuote.traning.module.addressbook

import android.os.Bundle
import android.util.Log
import android.view.View
import com.lkl.ansuote.hdqlibrary.base.BaseActivity
import com.lkl.ansuote.hdqlibrary.util.helper.PermissionHelper
import com.lkl.ansuote.traning.R
import com.lkl.ansuote.traning.core.base.PhoneContact
import com.lkl.ansuote.traning.module.addressbook.AddressBookUtil.getContractId
import com.lkl.ansuote.traning.module.addressbook.AddressBookUtil.getGroupByTitle
import com.lkl.ansuote.traning.module.addressbook.AddressBookUtil.getGroups
import com.lkl.ansuote.traning.module.addressbook.AddressBookUtil.isPhoneExist
import kotlinx.android.synthetic.main.address_book_activity.*


/**
 *
 * 系统通讯录测试界面
 * @author huangdongqiang
 * @date 23/07/2018
 *
 * 参考文章
 * http://www.djcxy.com/p/28873.html
 * https://blog.csdn.net/xiabing082/article/details/44084511
 * https://my.oschina.net/moziqi/blog/367025
 */
class AddressBookActivity : BaseActivity(), View.OnClickListener{
    var hasPermission = false
    val GROUP_TITLE_TEST = "Training分组"

    var contactDisplayName: String = "测试111"
    var contactPhone : String = "13506128133"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.address_book_activity)
        PermissionHelper.requestContacts {
            hasPermission = true
        }
    }

    override fun onClick(v: View?) {
        if (!hasPermission) {
            return
        }

        when (v) {
            btn_create_group ->{
                val groupId = AddressBookUtil.createGroup(this, GROUP_TITLE_TEST)
                Log.i("lkl", "创建分组 groupId = $groupId")
            }
            btn_insert_phone -> {
                AddressBookUtil.addContacts(this, arrayListOf<PhoneContact>().apply {
                    add(PhoneContact().apply {displayName = "测试号码2"; note = "note1233"; phoneNumber = "138000002"; groupId = getGroupByTitle(this@AddressBookActivity, GROUP_TITLE_TEST)})
                })
            }
            btn_query_group_data -> {
                val groups = getGroups(this)
                Log.i("lkl", "groups = $groups")

            }
            btn_query_phone -> {
                var found = isPhoneExist(this, "13580129339")
                Log.i("lkl", "found = $found")
            }

            btn_query_phone_or_name -> {
                var id = AddressBookUtil.findFirstContactByPhoneOrName(this, "13580129339", "Apple2")
                Log.i("lkl", "id = $id")
            }

            btn_query_by_group_id -> {
                AddressBookUtil.getMemberForGroupId(this, getGroupByTitle(this, GROUP_TITLE_TEST))

            }

            btn_save_to_group->{
                AddressBookUtil.saveToGroup(this,
                        AddressBookUtil.createGroup(this, GROUP_TITLE_TEST),
                        getContractId(this, "13580129339"))
            }

            btn_add_or_update -> {
                Thread {
                    //TODO LKL 超过600会有问题，待优化
                    AddressBookUtil.syncContacts(this, mutableListOf<PhoneContact>().apply {
                        val groupId = AddressBookUtil.createGroup(this@AddressBookActivity, GROUP_TITLE_TEST)
                        for (i in 0..100) {
                            add(PhoneContact().apply {
                                this.displayName = "ee大量测试用户--$i"
                                this.phoneNumber = "2313123$i"
                                this.note = "note$i"
                                this.groupId = groupId
                            })
                        }
                    })
                }.start()
            }
            btn_delete_all -> {
                Thread {
                    AddressBookUtil.deleteAll(this)
                }.start()
            }

            btn_add_contacts -> {
                AddressBookUtil.addContactPhoneNumber(this, contactDisplayName, contactPhone)
            }

            btn_remove_contacts -> {
                AddressBookUtil.deleteContactPhoneNumber(this, contactDisplayName, contactPhone)
            }

            btn_add_contacts_special -> {
                //判断是在原来通讯录里面
                if (AddressBookUtil.isPhoneExist(this, contactPhone)) {
                    //查到名字，保存起来
                    val displayName = AddressBookUtil.getDisplayNameForPhone(this, contactPhone)
                    Log.i("lkl", "displayName = $displayName")

                } else {
                    AddressBookUtil.addContactPhoneNumber(this, contactDisplayName, contactPhone)

                }


            }
        }
    }






}
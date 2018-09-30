package com.lkl.ansuote.traning.module.addressbook

import android.content.*
import android.net.Uri
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.provider.ContactsContract.CommonDataKinds.StructuredName
import android.text.TextUtils
import android.util.Log
import com.lkl.ansuote.traning.core.base.PhoneContact






















/**
 *
 * 通讯录相关工具类，之前该接口之前，外面必须先获取通讯录权限
 * @author huangdongqiang
 * @date 24/07/2018
 */
object AddressBookUtil {
    /**
     * 获取所有的群组
     *
     * @return
     */
    fun getGroups(context: Context): List<HashMap<String, String>> {

        val groups = ArrayList<HashMap<String, String>>()

        val cursor = context?.contentResolver?.query(ContactsContract.Groups.CONTENT_URI, null, null, null, null)
        if (null != cursor) {
            while (cursor.moveToNext()) {
                val group = HashMap<String, String>()
                val id = cursor.getLong(cursor.getColumnIndex(ContactsContract.Groups._ID))
                val title = cursor.getString(cursor.getColumnIndex(ContactsContract.Groups.TITLE))
                group["groupId"] = id.toString()
                group["groupTitle"] = title
                groups.add(group)
            }
            cursor.close()
        }

        return groups
    }


    /**
     * 判断手机号码是否存在通讯录里面
     */
    fun isPhoneExist(context: Context, phone: String): Boolean {
        return getContractId(context,phone) != -1L
    }

    /**
     * 从手机号获取联系人id
     */
    fun getContractId(context: Context, phone: String): Long {
        val cursor = context?.contentResolver?.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.NUMBER + " = ?",
                arrayOf(phone),
                null)
        var contactId = -1L
        cursor?.let {
            while (it.moveToNext()) {
                contactId = it.getLong(it.getColumnIndex(ContactsContract.Contacts._ID))
                break
            }
            it.close()
        }

        return contactId
    }

    /**
     * 从手机号获取联系人id
     * @return Contacts._ID
     */
    fun findFirstContactByPhoneOrName(context: Context, phone: String, displayName: String): Long {
        val cursor = context?.contentResolver?.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.NUMBER + " = ? or " +
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " = ?",
                arrayOf(phone, displayName), null)
        var contactId = -1L
        cursor?.let {
            while (it.moveToNext()) {
                contactId = it.getLong(it.getColumnIndex(ContactsContract.Contacts._ID))
                break
            }
            it.close()
        }

        return contactId
    }


    /**
     * 修改联系人信息
     */
    fun update(context: Context?, rawContactId: Long, phoneContact: PhoneContact): Boolean {
        if (null == context) {
            return false
        }

        val values = ContentValues()
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneContact.phoneNumber)
        //values.put(Phone.TYPE, Phone.TYPE_MOBILE)
        //values.put(Phone.DISPLAY_NAME, phoneContact.displayName)
        //values.put(Phone.)
        val where = (ContactsContract.Data.RAW_CONTACT_ID + "=? AND " + ContactsContract.Data.MIMETYPE + "=?")
        val selectionArgs = arrayOf(rawContactId.toString(), Phone.CONTENT_ITEM_TYPE)
        val result = context.contentResolver.update(ContactsContract.Data.CONTENT_URI, values,
                where, selectionArgs)
        return result != -1
    }


    /**
     * 获取 RawContactId （匹配姓名或者号码）
     */
    fun getRawContactId(context: Context, phone: String, displayName: String): Long {
        val cursor = context?.contentResolver?.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.NUMBER + " = ? or " +
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " = ?",
                arrayOf(phone, displayName), null)
        if (null != cursor && cursor.moveToFirst()) {
            val rawContactId = cursor.getLong(cursor.getColumnIndex(ContactsContract.Data.RAW_CONTACT_ID))
            cursor.close()
            return rawContactId
        } else {
            return 0L
        }
    }

    /**
     * 从数据库获取号码对应的信息
     */
//    private fun getContractBeanForDb(phone: String): EcallContacts? {
//        var bean: EcallContacts? = null
//        val listLazy = App.daoSession?.ecallContactsDao?.queryBuilder()?.listLazy()
//        bean = listLazy?.find { it.contactNumber == phone }
//        if (listLazy?.isClosed == false) {
//            listLazy.close()
//        }
//
//        return bean
//    }

    /**
     * 同步联系人（之前有则更新，没有则新增）
     */
    fun syncContacts(context: Context?, contactsList: List<PhoneContact>) {
        if (null == context) return

        val contactsListSize = contactsList.size
        val unitLength = 400 //large insert will cause binder data overflow.
        var syncedCount = 0
        while (syncedCount < contactsListSize) {
            val syncLength = if (contactsListSize - syncedCount < unitLength) contactsListSize - syncedCount else unitLength
            val ops = arrayListOf<ContentProviderOperation>()
            for (index in contactsList.indices) {
                val contact = contactsList[index]
                val rawContactInsertIndex = ops.size

                var rawContactId = getRawContactId(context, contact.phoneNumber, contact.displayName)

                //var dbBean = getContractBeanForDb(contact.contactNumber)
                if (0L == rawContactId) {
                    //说明之前没有该联系人，必须批量插入

                    ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                            .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                            .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                            .withYieldAllowed(true).build())

                    ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                            .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, contact.displayName)
                            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, contact.lastName)
                            .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, contact.firstName)
                            .withValue(ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME, contact.middlename)
                            .withYieldAllowed(true).build())

                    ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contact.phoneNumber)
                            .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, contact.phoneType)
                            .withValue(ContactsContract.CommonDataKinds.Phone.LABEL, "")
                            .withYieldAllowed(true).build())

                    //关联群组和成员，外面必须定义 groupId
                    if (-1L != contact.groupId) {
                        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                                .withValue(ContactsContract.CommonDataKinds.GroupMembership.MIMETYPE, ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE)
                                .withValue(ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID, contact.groupId)
                                .withYieldAllowed(true).build())
                    }

                    //备注
                    ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                            .withValue(ContactsContract.CommonDataKinds.Note.NOTE, contact.note)
                            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE)
                            //.withYieldAllowed(true)
                            .build())

                } else {
                    //之前存在，则批量更新
                    val where = ContactsContract.Data.RAW_CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?"

                    val phoneParams = arrayOf(rawContactId.toString(), Phone.CONTENT_ITEM_TYPE)
                    val displayNameParams = arrayOf(rawContactId.toString(), ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    val noteParams = arrayOf(rawContactId.toString(), ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE)

                    // 更新电话号码
                    ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                            //.withSelection(ContactsContract.Data.RAW_CONTACT_ID + "=?" + " AND " + ContactsContract.Data.MIMETYPE + " = ?" + " AND " + Phone.TYPE + "=?", arrayOf(id.toString(), null, null))
                            .withSelection(where, phoneParams)
                            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contact.phoneNumber)
                            .withYieldAllowed(true).build())

                    // 更新姓名
                    ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                            .withSelection(where, displayNameParams)
                            .withValue(StructuredName.DISPLAY_NAME, contact.displayName)
                            .withYieldAllowed(true).build())

                    // 更新note (如果是手机新增联系人，并且该联系人没有填备注，这种情况下更新note接口为无效)
                    ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                            .withSelection(where, noteParams)
                            .withValue(ContactsContract.CommonDataKinds.Note.NOTE, contact.note)
                            //.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE)
                            //.withYieldAllowed(true)
                            .build())

                    //关联群组和成员，外面必须定义 groupId
                    /*if (-1L != contact.groupId)  {
                        if (null != dbBean && !dbBean.contactGroupId.isNullOrBlank()) {
                            ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                                    .withSelection(where, groupParams)
                                    .withValue(ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID, contact.contactGroupId)
                                    .withYieldAllowed(true).build())
                        } else {
                            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                                    .withValue(ContactsContract.CommonDataKinds.GroupMembership.MIMETYPE, ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE)
                                    .withValue(ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID, contact.contactGroupId)
                                    .withValue(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
                                    .withYieldAllowed(true).build())
                        }
                    }*/

                    // 更新email
                    /*ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                            .withSelection(ContactsContract.Data.RAW_CONTACT_ID + "=?" + " AND " + ContactsContract.Data.MIMETYPE + " = ?" + " AND " + ContactsContract.CommonDataKinds.Email.TYPE + "=?", arrayOf(String.valueOf(id), ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE, (ContactsContract.CommonDataKinds.Email.TYPE_HOME).toString()))
                            .withValue(ContactsContract.CommonDataKinds.Email.DATA, email).build())*/

                    // 更新网站
                    /*ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                            .withSelection(ContactsContract.Data.RAW_CONTACT_ID + "=?" + " AND " + ContactsContract.Data.MIMETYPE + " = ?", arrayOf(String.valueOf(id), ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE))
                            .withValue(ContactsContract.CommonDataKinds.Website.URL, website).build())*/

                    // 更新公司
                    /*ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                            .withSelection(ContactsContract.Data.RAW_CONTACT_ID + "=?" + " AND " + ContactsContract.Data.MIMETYPE + " = ?", arrayOf(String.valueOf(id), ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE))
                            .withValue(ContactsContract.CommonDataKinds.Organization.COMPANY, organization).build())*/
                }

            }

            try {
                context.contentResolver?.applyBatch(ContactsContract.AUTHORITY, ops)
                ops.clear()

                syncedCount = syncLength
            } catch (e: Exception) {
                //Log.i("lkl", "updateContacts -- 报错 -- " + e.toString())
                e.printStackTrace()
                return
            }
        }
    }


    /**
     * 批量同步数据到通讯录(新增数据)
     */
    fun addContacts(context: Context?, contactsList: List<PhoneContact>) {
        if (null == context) return

        val contactsListSize = contactsList.size
        val unitLength = 400 //large insert will cause binder data overflow.
        var syncedCount = 0
        while (syncedCount < contactsListSize) {
            val syncLength = if (contactsListSize - syncedCount < unitLength) contactsListSize - syncedCount else unitLength
            val ops = arrayListOf<ContentProviderOperation>()
            for (index in contactsList.indices) {
                val contact = contactsList[index]
                val rawContactInsertIndex = ops.size

                ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                        .withYieldAllowed(true).build())

                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                        .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, contact.displayName)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, contact.lastName)
                        .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, contact.firstName)
                        .withValue(ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME, contact.middlename)
                        .withYieldAllowed(true).build())

                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contact.phoneNumber)
                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, contact.phoneType)
                        .withValue(ContactsContract.CommonDataKinds.Phone.LABEL, "")
                        .withYieldAllowed(true).build())

                //关联群组和成员，外面必须定义 groupId
                if (-1L != contact.groupId) {
                    ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                            .withValue(ContactsContract.CommonDataKinds.GroupMembership.MIMETYPE,
                                    ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID, contact.groupId)
                            .withYieldAllowed(true).build())
                }

                //备注
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                        .withValue(ContactsContract.CommonDataKinds.Note.NOTE, contact.note)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE)
                        .withYieldAllowed(true).build())
            }
            try {
                context.contentResolver?.applyBatch(ContactsContract.AUTHORITY, ops)
                ops.clear()

                syncedCount = syncLength
            } catch (e: Exception) {
                //Log.i("lkl", "addContacts -- 报错 -- " + e.toString())
                e.printStackTrace()
                return
            }
        }
    }


    private fun getDefaultAccountNameAndType(context: Context): Array<String> {
        var accountType = ""
        var accountName = ""

        var rawContactId: Long = 0
        var rawContactUri: Uri? = null
        var results: Array<ContentProviderResult>? = null

        val ops = ArrayList<ContentProviderOperation>()

        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI).withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null).withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null).build())

        try {
            results = context?.contentResolver?.applyBatch(ContactsContract.AUTHORITY, ops)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            ops.clear()
        }

        for (result in results!!) {
            rawContactUri = result.uri
            rawContactId = ContentUris.parseId(rawContactUri)
        }

        val c = context?.contentResolver?.query(
                ContactsContract.RawContacts.CONTENT_URI, arrayOf(ContactsContract.RawContacts.ACCOUNT_TYPE, ContactsContract.RawContacts.ACCOUNT_NAME), ContactsContract.RawContacts._ID + "=?", arrayOf(rawContactId.toString()), null)

        if (null != c) {
            if (c.moveToFirst()) {
                if (!c.isAfterLast) {
                    val type = c.getString(c.getColumnIndex(ContactsContract.RawContacts.ACCOUNT_TYPE))
                    val name = c.getString(c.getColumnIndex(ContactsContract.RawContacts.ACCOUNT_NAME))
                    accountType = type ?: ""
                    accountName = name ?: ""
                }
            }
            context.contentResolver.delete(rawContactUri!!, null, null)
            c.close()
        }


        return arrayOf(accountName, accountType)
    }


    /**
     * 查询 groupId 对应的所有成员
     */
    fun getMemberForGroupId(context: Context, groupId: Long){
        // To query all contacts in a group

        // First, query the raw_contact_ids of all the contacts in the group
        val groupContactCursor = context.contentResolver.query(ContactsContract.Data.CONTENT_URI,
                arrayOf<String>(ContactsContract.Data.RAW_CONTACT_ID),
                ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE + "' AND " + ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID + " = " + groupId,
                null, null)

        // Second, query the corresponding name of the raw_contact_id
        while (groupContactCursor.moveToNext()) {
            val contactCursor = context.contentResolver.query(ContactsContract.Data.CONTENT_URI,
                    //arrayOf(ContactsContract.Data.RAW_CONTACT_ID, StructuredName.FAMILY_NAME, StructuredName.GIVEN_NAME),
                    arrayOf(ContactsContract.Data.RAW_CONTACT_ID,
                            StructuredName.DISPLAY_NAME,
                            StructuredName.GIVEN_NAME,
                            StructuredName.FAMILY_NAME,
                            StructuredName.PREFIX,
                            StructuredName.MIDDLE_NAME,
                            StructuredName.SUFFIX,
                            StructuredName.PHONETIC_GIVEN_NAME,
                            StructuredName.PHONETIC_MIDDLE_NAME,
                            StructuredName.PHONETIC_FAMILY_NAME,
                            StructuredName.FULL_NAME_STYLE,
                            StructuredName.PHONETIC_NAME_STYLE),
                    ContactsContract.Data.MIMETYPE + "='" + StructuredName.CONTENT_ITEM_TYPE + "' AND " + ContactsContract.Data.RAW_CONTACT_ID + "=" + groupContactCursor.getInt(0), null, null)
            contactCursor.moveToNext()
            //Log.e("lkl", "Member name is: " + contactCursor.getString(1) + " " + contactCursor.getString(2))
            Log.i("lkl", "Member name is: "
                    + contactCursor.getString(0)
                    + "; " + contactCursor.getString(1)
                    + "; " + contactCursor.getString(2)
                    + "; " + contactCursor.getString(3)
                    + "; " + contactCursor.getString(4)
                    + "; " + contactCursor.getString(5)
                    + "; " + contactCursor.getString(6)
                    + "; " + contactCursor.getString(7)
                    + "; " + contactCursor.getString(8)
                    + "; " + contactCursor.getString(9)
                    + "; " + contactCursor.getString(10)
                    + "; " + contactCursor.getString(11)
                    + "; con")
            contactCursor.close()
        }
        groupContactCursor.close()
    }

    /**
     * 创建群组
     * 如果之前没有创建，则创建群组
     *
     * @return int
     */
    fun createGroup(context: Context, title: String): Long {
        if (TextUtils.isEmpty(title)) {
            return -1L
        }
        var gId = getGroupByTitle(context, title)
        if (gId == -1L) {
            val values = ContentValues()
            values.put(ContactsContract.Groups.TITLE, title)
            //必须进行账户同步
            values.put(ContactsContract.Groups.SHOULD_SYNC, true)
            val uri = context.contentResolver.insert(ContactsContract.Groups.CONTENT_URI, values)
            gId = ContentUris.parseId(uri)
        }
        return gId
    }


    /**
     * 根据组的名称查询组
     *
     * @return int
     */
    fun getGroupByTitle(context: Context, title: String): Long {
        var id = -1L
        val cursor = context.contentResolver.query(
                ContactsContract.Groups.CONTENT_URI,
                arrayOf(ContactsContract.Groups._ID),
                ContactsContract.Groups.TITLE + "='" + title + "'", null, null)
        if (cursor.moveToNext()) {
            id = cursor.getLong(cursor.getColumnIndex(ContactsContract.Groups._ID))
        }
        cursor.close()
        return id
    }

    /**
     * 保存联系人到群组
     * @Deprecated@param groupId
     * @param contactId
     */
    @Deprecated(message = "没有效果，不能加入到群组，必须采用批量插入的方式")
    fun saveToGroup(context: Context, groupId: Long?, contactId: Long?) {
//        context.contentResolver.insert(ContactsContract.Data.CONTENT_URI, ContentValues().apply {
//            put(ContactsContract.CommonDataKinds.GroupMembership.RAW_CONTACT_ID, contactId)
//            put(ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID, groupId)
//            put(ContactsContract.CommonDataKinds.GroupMembership.MIMETYPE, ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE)
//        })

        val values = ContentValues()
        values.put(ContactsContract.CommonDataKinds.GroupMembership.RAW_CONTACT_ID, contactId)
        values.put(ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID, groupId)
        values.put(ContactsContract.CommonDataKinds.GroupMembership.MIMETYPE, ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE)
        context.contentResolver.insert(ContactsContract.Data.CONTENT_URI, values)
    }

    /**
     * 从群组里面删除联系人
     */
    fun deleteFromGroup(context: Context, groupId: Long?, contactId: Long?) {
        context.contentResolver.delete(ContactsContract.Data.CONTENT_URI,
                ContactsContract.CommonDataKinds.GroupMembership.RAW_CONTACT_ID + "=? and " + ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID + "=? and " + ContactsContract.CommonDataKinds.GroupMembership.MIMETYPE + "=?",
                arrayOf(contactId.toString(),
                        groupId.toString(),
                        ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE))
    }
}
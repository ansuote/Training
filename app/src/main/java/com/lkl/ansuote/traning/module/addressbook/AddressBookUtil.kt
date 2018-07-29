package com.lkl.ansuote.traning.module.addressbook

import android.content.ContentProviderOperation
import android.content.ContentProviderResult
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.StructuredName
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
        val cursor = context?.contentResolver?.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.NUMBER + " = ?",
                arrayOf(phone), null)
        cursor.let {
            return it?.count!! > 0
        }
    }

    /**
     * 批量同步数据到通讯录
     */
    fun syncContacts(context: Context, contactsList: List<PhoneContact>) {
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

                //TODO 批量同步，新增分组
//                ops.add(ContentProviderOperation.newInsert(ContactsContract.Groups.CONTENT_URI)
//                        //.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
////                        .withValue(ContactsContract.Groups._ID, 143)
//                        .withValue(ContactsContract.Groups.TITLE, contact.contactGroupTitle)
//                        .withValue(ContactsContract.Groups.GROUP_VISIBLE, true)
//                        .withValue(ContactsContract.Groups.ACCOUNT_NAME, getDefaultAccountNameAndType(context)[0])
//                        .withValue(ContactsContract.Groups.ACCOUNT_TYPE, getDefaultAccountNameAndType(context)[1])
//                        .withYieldAllowed(true).build())

            }
            try {
                context.contentResolver?.applyBatch(ContactsContract.AUTHORITY, ops)
                ops.clear()

                syncedCount = syncLength
            } catch (e: Exception) {
                Log.i("lkl", "syncContacts -- 报错 -- " + e.toString())
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
    fun getMemberForGroupId(context: Context, groupId: String){
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
                    + contactCursor.getString(1)
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

}
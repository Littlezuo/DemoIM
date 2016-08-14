package com.atguigu.demoim.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.atguigu.demoim.model.bean.UserInfo;
import com.atguigu.demoim.model.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/5 0005.
 */
public class ContactDao {
    private DBHelper mHelper;

    public ContactDao(DBHelper helper) {
        mHelper = helper;
    }
    //获取所有联系人
    public List<UserInfo> getContacts() {
        //获取数据库链接
        SQLiteDatabase db = mHelper.getReadableDatabase();
        //执行数据库语句
        String sql = "select * from " + ContactTable.TAB_NAME +" where "+ ContactTable.COL_IS_CONTACT + " = 1";
        Cursor cursor = db.rawQuery(sql, null);
        //创建返回数据的集合
        ArrayList<UserInfo> contacts = new ArrayList<>();
        //遍历cursor
        while(cursor.moveToNext()) {
            UserInfo userInfo = new UserInfo();
            userInfo.setHxId(cursor.getString(cursor.getColumnIndex(ContactTable.COL_HXID)));
            userInfo.setName(cursor.getString(cursor.getColumnIndex(ContactTable.COL_NAME)));
            userInfo.setNick(cursor.getString(cursor.getColumnIndex(ContactTable.COL_NICK)));
            userInfo.setPhoto(cursor.getString(cursor.getColumnIndex(ContactTable.COL_PHOTO)));
            contacts.add(userInfo);
        }

        //关闭资源
        cursor.close();

        //返回获取的数据
        return contacts;
    }

    //通过环信id获取用户信息
    public List<UserInfo> getContactsByHx(List<String> hxIds) {
        //效验
        if(hxIds == null || hxIds.size() <= 0) {
            return null;
        }
        //获取数据库链接
        SQLiteDatabase db = mHelper.getReadableDatabase();
        //创建返回数据的集合
        List<UserInfo> contacts = new ArrayList<>();
        //遍历hxIds获取相应的用户信息
        Cursor cursor = null;
        for (String hxid:hxIds) {
            String sql = "select * from " + ContactTable.TAB_NAME + " where " + ContactTable.COL_HXID +"=?";
            cursor = db.rawQuery(sql, new String[]{hxid});

            if(cursor.moveToNext()) {
                UserInfo userInfo = new UserInfo();
                userInfo.setHxId(cursor.getString(cursor.getColumnIndex(ContactTable.COL_HXID)));
                userInfo.setName(cursor.getString(cursor.getColumnIndex(ContactTable.COL_NAME)));
                userInfo.setNick(cursor.getString(cursor.getColumnIndex(ContactTable.COL_NICK)));
                userInfo.setPhoto(cursor.getString(cursor.getColumnIndex(ContactTable.COL_PHOTO)));
                contacts.add(userInfo);
            }
        }

        //关闭资源
        cursor.close();
        //返回获取的数据
        return contacts;
    }
    //通过环信id获取联系人单个信息
    public UserInfo getContactByHx(String hxId){
        //效验
        if(hxId==null) {
            return null;
        }
        //获取数据库链接
        SQLiteDatabase db = mHelper.getReadableDatabase();
        //执行查询语句
        String sql = "select * from " +ContactTable.TAB_NAME + " where " + ContactTable.COL_HXID + "=?";
        Cursor cursor = db.rawQuery(sql, new String[]{hxId});
        UserInfo userInfo = null;
        if(cursor.moveToNext()) {
            userInfo = new UserInfo();
            userInfo.setHxId(cursor.getString(cursor.getColumnIndex(ContactTable.COL_HXID)));
            userInfo.setName(cursor.getString(cursor.getColumnIndex(ContactTable.COL_NAME)));
            userInfo.setNick(cursor.getString(cursor.getColumnIndex(ContactTable.COL_NICK)));
            userInfo.setPhoto(cursor.getString(cursor.getColumnIndex(ContactTable.COL_PHOTO)));
        }
        cursor.close();
        return userInfo;
    }

    //保存联系人信息
    public void saveContacts(List<UserInfo> contacts,boolean isMyContact) {
        if(contacts == null || contacts.size() <= 0) {
            return;
        }

        for (UserInfo contact : contacts) {
            saveContact(contact,isMyContact);
        }

    }

    public void saveContact(UserInfo contact, boolean isMyContact) {
        if(contact == null) {
            return;
        }

        SQLiteDatabase db = mHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(ContactTable.COL_HXID,contact.getHxId());
        values.put(ContactTable.COL_IS_CONTACT,isMyContact?1 : 0);
        values.put(ContactTable.COL_NAME,contact.getName());
        values.put(ContactTable.COL_NICK,contact.getNick());
        values.put(ContactTable.COL_PHOTO,contact.getPhoto());
        db.replace(ContactTable.TAB_NAME,null,values);
    }

    //删除联系人信息
    public void deleteContactById(String hxId) {
        if(hxId == null) {
            return;
        }
        SQLiteDatabase db = mHelper.getReadableDatabase();
        db.delete(ContactTable.TAB_NAME,ContactTable.COL_HXID + "=?",new String[]{hxId});


    }

}
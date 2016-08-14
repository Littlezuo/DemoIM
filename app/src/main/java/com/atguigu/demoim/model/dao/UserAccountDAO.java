package com.atguigu.demoim.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.atguigu.demoim.model.bean.UserInfo;
import com.atguigu.demoim.model.db.UserAccountDB;

/**
 * Created by Administrator on 2016/8/4 0004.
 */
public class UserAccountDAO {

    private final UserAccountDB mHelper;

    public UserAccountDAO(Context context){
        mHelper = new UserAccountDB(context);
    }
    //添加到数据库
    public void addAccount(UserInfo user) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserAccountTable.COL_NAME,user.getName());
        values.put(UserAccountTable.COL_HXID,user.getHxId());
        values.put(UserAccountTable.COL_NICK,user.getNick());
        values.put(UserAccountTable.COL_PHOTO, user.getPhoto());
        db.replace(UserAccountTable.TAB_NAME,null,values);

    }
    //获取用户
    public UserInfo getAccount(String name) {
        //获取数据库链接
        SQLiteDatabase db = mHelper.getReadableDatabase();
        String sql = "select * from "+ UserAccountTable.TAB_NAME + " where "+ UserAccountTable.COL_NAME+" =?";
        Cursor cursor = db.rawQuery(sql, new String[]{name});
        UserInfo userinfo = null;
        if(cursor!=null) {
            while(cursor.moveToNext()) {
                //封装对象
                userinfo = new UserInfo();
                //Log.i("kkkk", cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_NAME)));
                userinfo.setName(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_NAME)));
                userinfo.setHxId(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_HXID)));
                userinfo.setNick(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_NICK)));
                userinfo.setPhoto(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_PHOTO)));
                //关闭cursor
                cursor.close();
            }
        }
        //返回资源
        return userinfo;
    }
    //根据环信id获取所有用户信息
    public UserInfo getAccountByHxId(String hxId) {
        //获取数据库链接
        SQLiteDatabase db = mHelper.getReadableDatabase();
        String sql = "select * from "+ UserAccountTable.TAB_NAME + " where "+ UserAccountTable.COL_NAME+" + ?";
        Cursor cursor = db.rawQuery(sql, new String[]{hxId});
        UserInfo userinfo = null;
        if(cursor!=null) {
            while(cursor.moveToNext()) {
                //封装对象
                userinfo = new UserInfo();
                userinfo.setName(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_NAME)));
                userinfo.setHxId(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_HXID)));
                userinfo.setNick(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_NICK)));
                userinfo.setPhoto(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_PHOTO)));
                //关闭cursor
                cursor.close();

            }
        }
        //返回资源
        return userinfo;
    }


}

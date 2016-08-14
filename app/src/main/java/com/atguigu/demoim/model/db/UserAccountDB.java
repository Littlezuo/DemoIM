package com.atguigu.demoim.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.atguigu.demoim.model.dao.UserAccountTable;

/**
 * Created by Administrator on 2016/8/7 0007.
 */
public class UserAccountDB extends SQLiteOpenHelper{
    private static final int DB_VERSION = 1;

    public UserAccountDB(Context context) {
        super(context, "account.ab", null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserAccountTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

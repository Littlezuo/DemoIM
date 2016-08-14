package com.atguigu.demoim.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.atguigu.demoim.model.dao.ContactTable;
import com.atguigu.demoim.model.dao.InvitationTable;

/**
 * Created by Administrator on 2016/8/5 0005.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final int DE_VERSION = 1;

    public DBHelper(Context context, String name) {
        super(context, name, null, DE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ContactTable.CREATE_TABLE);
        db.execSQL(InvitationTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

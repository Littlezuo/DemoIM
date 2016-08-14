package com.atguigu.demoim.model.db;

import android.content.Context;

import com.atguigu.demoim.model.dao.ContactDao;
import com.atguigu.demoim.model.dao.InvitationDao;

/**
 * Created by Administrator on 2016/8/11 0011.
 */
public class DBManager {

    private final DBHelper mDbHelper;
    private final ContactDao mContactDao;
    private final InvitationDao mInvitationDao;

    public DBManager(Context context,String name) {
        mDbHelper = new DBHelper(context, name);
        mContactDao = new ContactDao(mDbHelper);
        mInvitationDao = new InvitationDao(mDbHelper);
    }

    public ContactDao getContactDao() {
        return mContactDao;
    }

    public InvitationDao getInvitationDao() {
        return mInvitationDao;
    }

    public void close() {
        mDbHelper.close();
    }
}

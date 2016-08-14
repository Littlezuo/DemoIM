package com.atguigu.demoim.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.atguigu.demoim.model.bean.EMGroup;
import com.atguigu.demoim.model.bean.InvitationInfo;
import com.atguigu.demoim.model.bean.UserInfo;
import com.atguigu.demoim.model.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/5 0005.
 */
public class InvitationDao {
    private DBHelper mDbHelper;

    public InvitationDao(DBHelper dbHelper) {
        mDbHelper = dbHelper;
    }

    //添加邀请
    public void addInvitation(InvitationInfo invitationInfo) {
        //获取数据库的链接
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        //原因和状态
        values.put(InvitationTable.COL_REASON,invitationInfo.getReason());
        values.put(InvitationTable.COL_STATUS,invitationInfo.getStatus().ordinal());
        UserInfo user = invitationInfo.getUser();
        if(user != null) {//个人
            values.put(InvitationTable.COL_USER_HXID,invitationInfo.getUser().getHxId());
            values.put(InvitationTable.COL_USER_NAME,invitationInfo.getUser().getName());
        }else {  //群组
            values.put(InvitationTable.COL_GROUP_ID,invitationInfo.getGroup().getGroupId());
            values.put(InvitationTable.COL_GROUP_NAME,invitationInfo.getGroup().getGroupName());
            values.put(InvitationTable.COL_USER_HXID,invitationInfo.getGroup().getInvitePerson());
        }
        db.replace(InvitationTable.TAB_NAME,null,values);

    }

    //获取所有邀请信息
    public List<InvitationInfo> getInvitations() {
        ArrayList<InvitationInfo> invitationInfos = new ArrayList<>();
        //获取数据库链接
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String sql = "select * from " + InvitationTable.TAB_NAME;
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            InvitationInfo invitationInfo = new InvitationInfo();
            //获取原因和状态
            invitationInfo.setReason(cursor.getString(cursor.getColumnIndex(InvitationTable.COL_REASON)));
            invitationInfo.setStatus(int2InviteStatus(cursor.getInt(cursor.getColumnIndex(InvitationTable.COL_STATUS))));
            //获取群组id
            String groupId = cursor.getString(cursor.getColumnIndex(InvitationTable.COL_GROUP_ID));

            if (groupId == null) {// 个人
                UserInfo user = new UserInfo();

                user.setName(cursor.getString(cursor.getColumnIndex(InvitationTable.COL_USER_NAME)));
                user.setHxId(cursor.getString(cursor.getColumnIndex(InvitationTable.COL_USER_HXID)));

                invitationInfo.setUser(user);
            } else {     // 群

                EMGroup group = new EMGroup();

                group.setGroupId(cursor.getString(cursor.getColumnIndex(InvitationTable.COL_GROUP_ID)));
                group.setGroupName(cursor.getString(cursor.getColumnIndex(InvitationTable.COL_GROUP_NAME)));
                group.setInvitePerson(cursor.getString(cursor.getColumnIndex(InvitationTable.COL_USER_HXID)));

                invitationInfo.setGroup(group);
            }

            invitationInfos.add(invitationInfo);

        }
        cursor.close();
        return invitationInfos;
    }
    public void removeInvitation(String hxId) {
        if (hxId == null) {
            return;
        }

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        db.delete(InvitationTable.TAB_NAME, InvitationTable.COL_USER_HXID + "=?", new String[]{hxId});
    }

    // 更新邀请状态
    public void updateInvitationStatus(InvitationInfo.InvitationStatus invitationStatus, String hxId) {

        if (hxId == null) {
            return;
        }

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(InvitationTable.COL_STATUS, invitationStatus.ordinal());

        db.update(InvitationTable.TAB_NAME, values, InvitationTable.COL_USER_HXID + "=?", new String[]{hxId});
    }


    // 将int类型状态转换为邀请的状态
    private InvitationInfo.InvitationStatus int2InviteStatus(int intStatus) {

        if (intStatus == InvitationInfo.InvitationStatus.NEW_INVITE.ordinal()) {
            return InvitationInfo.InvitationStatus.NEW_INVITE;
        }

        if (intStatus == InvitationInfo.InvitationStatus.INVITE_ACCEPT.ordinal()) {
            return InvitationInfo.InvitationStatus.INVITE_ACCEPT;
        }

        if (intStatus == InvitationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER.ordinal()) {
            return InvitationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER;
        }

        if (intStatus == InvitationInfo.InvitationStatus.NEW_GROUP_INVITE.ordinal()) {
            return InvitationInfo.InvitationStatus.NEW_GROUP_INVITE;
        }

        if (intStatus == InvitationInfo.InvitationStatus.NEW_GROUP_APPLICATION.ordinal()) {
            return InvitationInfo.InvitationStatus.NEW_GROUP_APPLICATION;
        }

        if (intStatus == InvitationInfo.InvitationStatus.GROUP_INVITE_ACCEPTED.ordinal()) {
            return InvitationInfo.InvitationStatus.GROUP_INVITE_ACCEPTED;
        }

        if (intStatus == InvitationInfo.InvitationStatus.GROUP_APPLICATION_ACCEPTED.ordinal()) {
            return InvitationInfo.InvitationStatus.GROUP_APPLICATION_ACCEPTED;
        }

        if (intStatus == InvitationInfo.InvitationStatus.GROUP_INVITE_DECLINED.ordinal()) {
            return InvitationInfo.InvitationStatus.GROUP_INVITE_DECLINED;
        }

        if (intStatus == InvitationInfo.InvitationStatus.GROUP_APPLICATION_DECLINED.ordinal()) {
            return InvitationInfo.InvitationStatus.GROUP_APPLICATION_DECLINED;
        }

        if (intStatus == InvitationInfo.InvitationStatus.GROUP_ACCEPT_INVITE.ordinal()) {
            return InvitationInfo.InvitationStatus.GROUP_ACCEPT_INVITE;
        }

        if (intStatus == InvitationInfo.InvitationStatus.GROUPO_ACCEPT_APPLICATION.ordinal()) {
            return InvitationInfo.InvitationStatus.GROUPO_ACCEPT_APPLICATION;
        }

        if (intStatus == InvitationInfo.InvitationStatus.GROUP_REJECT_APPLICATION.ordinal()) {
            return InvitationInfo.InvitationStatus.GROUP_REJECT_APPLICATION;
        }

        if (intStatus == InvitationInfo.InvitationStatus.GROUP_REJECT_INVITE.ordinal()) {
            return InvitationInfo.InvitationStatus.GROUP_REJECT_INVITE;
        }

        return null;
    }
}

package com.atguigu.demoim.model;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.atguigu.demoim.model.bean.InvitationInfo;
import com.atguigu.demoim.model.bean.UserInfo;
import com.atguigu.demoim.utils.Constant;
import com.atguigu.demoim.utils.SpUtil;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;

/**
 * Created by Administrator on 2016/8/11 0011.
 */
public class EventListener {
    private final EMContactListener emContactListener = new EMContactListener() {
        @Override
        public void onContactAdded(String hxid) {
            //添加联系人到本地数据库
            Model.getInstance().getDBManager().getContactDao().saveContact(new UserInfo(hxid),true);
            mLbm.sendBroadcast(new Intent(Constant.CONTACT_CHANGED));
        }

        @Override
        public void onContactDeleted(String hxid) {
            Model.getInstance().getDBManager().getContactDao().deleteContactById(hxid);
            Model.getInstance().getDBManager().getInvitationDao().removeInvitation(hxid);
            //发送联系人变化的广播
            mLbm.sendBroadcast(new Intent(Constant.CONTACT_CHANGED));
        }
        //收到 联系人的邀请
        @Override
        public void onContactInvited(String hxid, String reason) {
            Log.e("kkkk", "onContactInvited(): " + "hxid = [" + hxid + "], reason = [" + reason + "]");
            //添加邀请信息到本地数据库
            InvitationInfo invitation = new InvitationInfo();
            invitation.setReason(reason);
            invitation.setStatus(InvitationInfo.InvitationStatus.NEW_INVITE);
            UserInfo user = new UserInfo(hxid);
            invitation.setUser(user);
            Model.getInstance().getDBManager().getInvitationDao().addInvitation(invitation);
            //存储状态设置为红点
            SpUtil.getInstance(mContext).save(SpUtil.IS_NEW_INVITE,true);
            //发送邀请变化的广播
            mLbm.sendBroadcast(new Intent(Constant.CONTACT_INVITE_CHANGED));
            Log.e("kkkk", "11111");
        }
        //收到 联系人同意邀请
        @Override
        public void onContactAgreed(String hxid) {
            InvitationInfo invitation = new InvitationInfo();
            invitation.setUser(new UserInfo(hxid));
            invitation.setStatus(InvitationInfo.InvitationStatus.INVITE_ACCEPT);
            Model.getInstance().getDBManager().getInvitationDao().addInvitation(invitation);
            //储存状态为红点
            SpUtil.getInstance(mContext).save(SpUtil.IS_NEW_INVITE,true);
            //发送广播
            mLbm.sendBroadcast(new Intent(Constant.CONTACT_INVITE_CHANGED));
        }

        @Override
        public void onContactRefused(String s) {
            // 存储状态为有红点
            SpUtil.getInstance(mContext).save(SpUtil.IS_NEW_INVITE, true);

            // 发送邀请信息变化的广播
            mLbm.sendBroadcast(new Intent(Constant.CONTACT_INVITE_CHANGED));
        }
    };
    private Context mContext;
    private final LocalBroadcastManager mLbm;

    public EventListener(Context context) {
        mContext = context;
        mLbm = LocalBroadcastManager.getInstance(context);
        EMClient.getInstance().contactManager().setContactListener(emContactListener);
    }
}

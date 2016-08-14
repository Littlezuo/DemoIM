package com.atguigu.demoim.controller.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.atguigu.demoim.R;
import com.atguigu.demoim.controller.adapter.InviteAdapter;
import com.atguigu.demoim.model.Model;
import com.atguigu.demoim.model.bean.InvitationInfo;
import com.atguigu.demoim.utils.Constant;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;

public class InviteActivity extends Activity {

    private ListView mLv_invite_list;
    private InviteAdapter.OnInviteListener onInviteListener = new InviteAdapter.OnInviteListener() {
        @Override
        public void onReccept(final InvitationInfo invitationInfo) {
            //联网
            Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        EMClient.getInstance().contactManager().acceptInvitation(invitationInfo.getUser().getHxId());
                        //更新数据库
                        Model.getInstance().getDBManager().getInvitationDao().updateInvitationStatus(InvitationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER,invitationInfo.getUser().getHxId());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InviteActivity.this, "您接受了" + invitationInfo.getUser().getName() + "邀请", Toast.LENGTH_SHORT).show();

                                refresh();
                                //initData();
                                Log.e("kkkk", "onRecept");
                            }
                        });
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public void onReject(InvitationInfo invitationInfo) {

        }
    };
    private InviteAdapter mInviteAdapter;

    private LocalBroadcastManager mLbm;

    private List<InvitationInfo> mInvitations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        initView();
        initData();
    }
    private BroadcastReceiver InviteBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("kkkk", "onreceive");
            if(intent.getAction() == Constant.CONTACT_INVITE_CHANGED) {
                refresh();
                Log.e("kkkk", "refresh");
            }
        }
    };

    private void initData() {
        refresh();
        mInviteAdapter = new InviteAdapter(this,mInvitations,onInviteListener);
        mLv_invite_list.setAdapter(mInviteAdapter);
        mLbm = LocalBroadcastManager.getInstance(this);
        mLbm.registerReceiver(InviteBroadcastReceiver,new IntentFilter(Constant.CONTACT_INVITE_CHANGED));

    }


    private void refresh() {

        List<InvitationInfo> invitations = Model.getInstance().getDBManager().getInvitationDao().getInvitations();
        if(invitations==null) {
            return;
        }
        Log.e("kkkk",invitations.toString() );
        mInvitations.clear();
        mInvitations.addAll(invitations);
        if(mInviteAdapter!= null) {
            mInviteAdapter.notifyDataSetChanged();
        }
    }

    private void initView() {
        mLv_invite_list = (ListView) findViewById(R.id.lv_invite_list);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

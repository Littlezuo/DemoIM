package com.atguigu.demoim.controller.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.atguigu.demoim.IMapplication;
import com.atguigu.demoim.R;
import com.atguigu.demoim.controller.activity.AddContactActivity;
import com.atguigu.demoim.controller.activity.ChatActivity;
import com.atguigu.demoim.controller.activity.InviteActivity;
import com.atguigu.demoim.model.Model;
import com.atguigu.demoim.model.bean.UserInfo;
import com.atguigu.demoim.utils.Constant;
import com.atguigu.demoim.utils.SpUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/8 0008.
 */
public class ContactsListFragment extends EaseContactListFragment {

    private LinearLayout mLl_contact_header_invite;
    private ImageView mIv_contact_invite_header_dot;
    private BroadcastReceiver mInviteReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction()==Constant.CONTACT_INVITE_CHANGED) {
                Log.e("kkkk", "onReceive(): " + "context = [" + context + "], intent = [" + intent + "]");
                mIv_contact_invite_header_dot.setVisibility(View.VISIBLE);
                Log.e("kkkk", "onReceivemm");
            }
        }
    };;
    private LocalBroadcastManager mLbm;
    private BroadcastReceiver mContactChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction() == Constant.CONTACT_CHANGED) {
                refreshContact();
            }
        }
    };
    private String mHxid;
    private LinearLayout mLl_contact_header_groud;

    private void refreshContact() {
        //从本地数据库中获取数据
        List<UserInfo> contacts = Model.getInstance().getDBManager().getContactDao().getContacts();
        //设置数据
        if(contacts!= null && contacts.size()>=0) {
            Map<String, EaseUser> contactsMap = new HashMap<>();
            for (UserInfo userInfo: contacts) {
                EaseUser easeUser = new EaseUser(userInfo.getHxId());
                contactsMap.put(userInfo.getHxId(),easeUser);
            }
            setContactsMap(contactsMap);
        }
        //通知刷新
        refresh();
    }

    @Override
    protected void initView() {
        super.initView();
        //添加头布局
        View headerView = View.inflate(getActivity(), R.layout.fragment_contact_header, null);
        listView.addHeaderView(headerView);
        //添加加号
        titleBar.setRightImageResource(R.drawable.em_add);

        //红点的处理
        mIv_contact_invite_header_dot = (ImageView) headerView.findViewById(R.id.iv_contact_header_invite_dot);
        boolean isNewInvite = SpUtil.getInstance(IMapplication.getApplication()).getBoolean(SpUtil.IS_NEW_INVITE, false);
        //设置红点是否显示
        mIv_contact_invite_header_dot.setVisibility(isNewInvite ? View.VISIBLE : View.GONE);

        mLl_contact_header_invite = (LinearLayout) headerView.findViewById(R.id.ll_contact_header_invite);
        mLl_contact_header_groud = (LinearLayout) headerView.findViewById(R.id.ll_contact_header_groud);

        //初始化监听事件
        initListener();
        initBroadcastReceiver();


    }

    private void initBroadcastReceiver() {
        mLbm = LocalBroadcastManager.getInstance(getActivity());
        Log.e("kkkk", "initBroadcastReceiver1");
        mLbm.registerReceiver(mInviteReceiver, new IntentFilter(Constant.CONTACT_INVITE_CHANGED));
        mLbm.registerReceiver(mContactChangedReceiver, new IntentFilter(Constant.CONTACT_CHANGED));
        Log.e("kkkk", "initBroadcastReceiver2");
    }

    private void initListener() {
        //设置加号的点击事件
        titleBar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddContactActivity.class));
            }
        });
        //设置头布局邀请条目的点击事件
        mLl_contact_header_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏红点
                mIv_contact_invite_header_dot.setVisibility(View.GONE);
                SpUtil.getInstance(IMapplication.getApplication()).save(SpUtil.IS_NEW_INVITE, false);
                //跳转到邀请页面
                Intent intent = new Intent(getActivity(), InviteActivity.class);
                startActivity(intent);
            }
        });

        //设置头布局群组条目的点击事件
        mLl_contact_header_groud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GroupListActivity.class);
                startActivity(intent);
            }
        });


        //设置联系人条目的点击事件
        setContactListItemClickListener(new EaseContactListItemClickListener() {
            @Override
            public void onListItemClicked(EaseUser user) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID, user.getUsername());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        getContactsFromServer();
        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //getActivity().getMenuInflater().inflate(R.menu.delete_contact, menu);
        int position = ((AdapterView.AdapterContextMenuInfo) menuInfo).position;
        EaseUser easeUser = (EaseUser) listView.getItemAtPosition(position);
        mHxid = easeUser.getUsername();

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.e("kkkk", "onContextItemSelected1");
        if(item.getItemId()==R.id.contact_delete) {
            deleteContact();
            return true;
        }
        Log.e("kkkk", "onContextItemSelected2");
        return super.onContextItemSelected(item);
    }

    private void deleteContact() {
        Log.e("kkkk", "deleteContact(): " + "");
        //联网删除联系人
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.e("kkkk", "run(): " + "");
                    //服务器上删除数据
                    EMClient.getInstance().contactManager().deleteContact(mHxid);
                    Log.e("kkkk", "run(): " + "1");
                    //删除本地数据
                    Model.getInstance().getDBManager().getContactDao().deleteContactById(mHxid);
                    Model.getInstance().getDBManager().getInvitationDao().removeInvitation(mHxid);
                    Log.e("kkkk", "run(): " + "2");
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            refreshContact();
                            Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (HyphenateException e) {
                    Log.e("kkkk", "HyphenateException: " + "");
                    e.printStackTrace();
                }

            }
        });
    }

    private void getContactsFromServer() {
        //联网
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    List<String> allHxidFromServer = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    List<UserInfo> contacts = new ArrayList<UserInfo>();
                    if(allHxidFromServer != null && allHxidFromServer.size() >= 0) {
                        for (String hxid: allHxidFromServer) {
                            contacts.add(new UserInfo(hxid));
                        }
                        Model.getInstance().getDBManager().getContactDao().saveContacts(contacts, true);
                        if(getActivity()==null) {
                            return;
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refreshContact();
                                Toast.makeText(getActivity(), "获取联系人成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

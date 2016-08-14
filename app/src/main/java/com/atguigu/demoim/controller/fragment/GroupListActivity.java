package com.atguigu.demoim.controller.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.atguigu.demoim.R;
import com.atguigu.demoim.controller.activity.ChatActivity;
import com.atguigu.demoim.controller.adapter.GroupListAdapter;
import com.atguigu.demoim.model.Model;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

public class GroupListActivity extends Activity {

    private ListView mLv_group_list;
    private List<EMGroup> mAllGroups;
    private GroupListAdapter mGroupListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

        initView();
        initData();
        initListener();

    }

    private void initListener() {
        mLv_group_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    Intent intent = new Intent(getApplicationContext(), NewGroupActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                    intent.putExtra(EaseConstant.EXTRA_USER_ID,mAllGroups.get(position).getGroupId());
                    intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE,EaseConstant.CHATTYPE_GROUP);
                    startActivity(intent);
                }
            }
        });
    }

    private void initData() {
        mGroupListAdapter = new GroupListAdapter(getApplicationContext());
        mLv_group_list.setAdapter(mGroupListAdapter);
        GroupListRefresh();
    }

    private void GroupListRefresh() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                    mAllGroups = EMClient.getInstance().groupManager().getAllGroups();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mGroupListAdapter.refresh(mAllGroups);
                            Toast.makeText(GroupListActivity.this, "加载群信息成功", Toast.LENGTH_SHORT).show();

                        }
                    });


                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initView() {
        mLv_group_list = (ListView) findViewById(R.id.lv_group_list);

        //设置头布局
        View view = View.inflate(getApplicationContext(),R.layout.layout_group_create,null);
        mLv_group_list.addHeaderView(view);
    }
}

package com.atguigu.demoim.controller.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.atguigu.demoim.R;
import com.hyphenate.easeui.ui.EaseChatFragment;

public class ChatActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initData();
    }

    private void initData() {
        //创建会话的fragment
        EaseChatFragment easeChatFragment = new EaseChatFragment();
        //设置参数
        easeChatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content,easeChatFragment).commit();
    }
}

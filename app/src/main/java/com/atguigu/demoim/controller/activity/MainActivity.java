package com.atguigu.demoim.controller.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.atguigu.demoim.R;
import com.atguigu.demoim.controller.fragment.ChatListFragment;
import com.atguigu.demoim.controller.fragment.ContactsListFragment;
import com.atguigu.demoim.controller.fragment.SettingFragment;

public class MainActivity extends FragmentActivity  {

    private FrameLayout mFl_main;
    private RadioButton mRb_main_chat;
    private RadioButton mRb_main_contact;
    private RadioButton mRb_main_setting;
    private ChatListFragment mChatListFragment;
    private ContactsListFragment mContactsListFragment;
    private SettingFragment mSettingFragment;
    private FragmentManager mManager;
    private RadioGroup mRg_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initListener();
    }

    private void initData() {
        mChatListFragment = new ChatListFragment();
        mContactsListFragment = new ContactsListFragment();
        mSettingFragment = new SettingFragment();
        mManager = getSupportFragmentManager();
        //默认选择哪个RadioButton
        mRg_main.check(R.id.rb_main_contact);

        mManager.beginTransaction().replace(R.id.fl_main, mContactsListFragment).commit();
    }

    private void initListener() {
        /*mRb_main_chat.setOnClickListener(this);
        mRb_main_contact.setOnClickListener(this);
        mRb_main_setting.setOnClickListener(this);*/
        mRg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Fragment fragment = null;
                switch (checkedId) {
                    case R.id.rb_main_chat:
                        fragment = new ChatListFragment();
                        break;
                    case R.id.rb_main_contact:
                        fragment = new ContactsListFragment();
                        break;
                    case R.id.rb_main_setting:
                        fragment = new SettingFragment();
                        break;

                }

                switchFragment(fragment);
            }
        });


    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_main,fragment).commit();

    }

    private void initView() {
        mFl_main = (FrameLayout) findViewById(R.id.fl_main);
        mRb_main_chat = (RadioButton) findViewById(R.id.rb_main_chat);
        mRb_main_contact = (RadioButton) findViewById(R.id.rb_main_contact);
        mRb_main_setting = (RadioButton) findViewById(R.id.rb_main_setting);
        mRg_main = (RadioGroup) findViewById(R.id.rg_main);

    }

   /* @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_main_chat :
                mManager.beginTransaction().replace(R.id.fl_main, mChatListFragment).commit();
                break;
        case R.id.rb_main_contact :
            mManager.beginTransaction().replace(R.id.fl_main, mContactsListFragment).commit();
                break;
        case R.id.rb_main_setting :
            mManager.beginTransaction().replace(R.id.fl_main,mSettingFragment).commit();
                break;
        }
    }*/
}

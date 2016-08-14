package com.atguigu.demoim.controller.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.demoim.R;
import com.atguigu.demoim.model.Model;
import com.atguigu.demoim.model.bean.UserInfo;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

public class AddContactActivity extends Activity {

    private EditText mEt_add_contact_input;
    private Button mBt_add_contact;
    private LinearLayout mLl_add_contact;
    private TextView mTv_add_contact_find;
    private TextView mTv_add_contact_name;
    private UserInfo mUserInfo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        initView();
        initData();
        initListener();

    }

    private void initListener() {
        //设置查找
        mTv_add_contact_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findContact();
            }
        });


        mBt_add_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEt_add_contact_input.getText().toString().trim();
                addContact();
            }
        });
    }

    private void addContact() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().contactManager().addContact(mUserInfo.getHxId(),"keep me?");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AddContactActivity.this, "发送添加好友的邀请成功", Toast.LENGTH_SHORT).show();
                            mLl_add_contact.setVisibility(View.GONE);
                            mEt_add_contact_input.setText("");
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AddContactActivity.this, "发送添加好友邀请失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void findContact() {
        String name = mEt_add_contact_input.getText().toString().trim();
        if(TextUtils.isEmpty(name)) {
            Toast.makeText(AddContactActivity.this, "输入的名称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        mUserInfo = new UserInfo(name);
        mLl_add_contact.setVisibility(View.VISIBLE);
        mTv_add_contact_name.setText(mUserInfo.getName());
    }

    private void initData() {

    }

    private void initView() {
        mEt_add_contact_input = (EditText) findViewById(R.id.et_add_contact_input);
        mBt_add_contact = (Button) findViewById(R.id.bt_add_contact);
        mLl_add_contact = (LinearLayout) findViewById(R.id.ll_add_contact);
        mTv_add_contact_find = (TextView) findViewById(R.id.tv_add_contact_find);
        mTv_add_contact_name = (TextView) findViewById(R.id.tv_add_contact_name);
    }
}

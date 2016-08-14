package com.atguigu.demoim.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.atguigu.demoim.R;
import com.atguigu.demoim.model.Model;
import com.atguigu.demoim.model.bean.UserInfo;
import com.hyphenate.chat.EMClient;

public class SplashActivity extends Activity {
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            toMainOrLogin();
        }
    };

    private void toMainOrLogin() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                if(EMClient.getInstance().isLoggedInBefore()) {//登陆过
                    //获取用户信息
                    String currentUser = EMClient.getInstance().getCurrentUser();
                    UserInfo account = Model.getInstance().getUserAccountDAO().getAccount(currentUser);
                    //登录成功后的处理(登录成功后要创建该用户的数据库)
                    Model.getInstance().loginSuccess(account);
                    //跳到主页面
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                }else {//没有登陆过
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                }
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler.sendMessageDelayed(Message.obtain(), 1500);
    }
}

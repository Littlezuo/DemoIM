package com.atguigu.demoim.controller.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.atguigu.demoim.R;
import com.atguigu.demoim.model.Model;
import com.atguigu.demoim.model.bean.UserInfo;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

public class LoginActivity extends Activity {

    private EditText mEt_splash_user;
    private EditText mEt_splash_pwd;
    private Button mBt_splash_login;
    private Button mBt_splash_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initListener();

    }

    private void initListener() {

        //登录按钮的点击事件
        mBt_splash_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        //注册按钮的点击事件
        mBt_splash_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();

            }
        });
    }

    private void register() {
        final String registerPwd = mEt_splash_pwd.getText().toString().trim();
        final String registerUser = mEt_splash_user.getText().toString().trim();
        //效验
        if(TextUtils.isEmpty(registerPwd)||TextUtils.isEmpty(registerUser)) {
            Toast.makeText(LoginActivity.this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        //去环信服务器注册
        //显示加载的进度条
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("正在注册中");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        //访问网络
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(registerUser,registerPwd);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }
                    });
                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "注册失败 "+ e.toString(), Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }
                    });
                }
            }
        });
    }

    private void login() {
        final String loginPwd = mEt_splash_pwd.getText().toString().trim();
        final String loginUser = mEt_splash_user.getText().toString().trim();
        //效验
        if(TextUtils.isEmpty(loginPwd)||TextUtils.isEmpty(loginUser)) {
            Toast.makeText(LoginActivity.this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        //去环信服务器登录
        //显示加载的进度条
        final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("正在登录中");
        pd.cancel();

        //访问网络
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                EMClient.getInstance().login(loginUser, loginPwd, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        pd.cancel();
                        //保存到本地数据库
                        Model.getInstance().getUserAccountDAO().addAccount(new UserInfo(loginUser));
                        //登录成功的初始化处理
                        Model.getInstance().loginSuccess(new UserInfo(loginUser));

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                        //跳转到主页面
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        finish();


                    }

                    @Override
                    public void onError(int i, final String s) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "登录失败 "+ s, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
            }
        });

    }

    private void initView() {
        mEt_splash_user = (EditText) findViewById(R.id.et_splash_user);
        mEt_splash_pwd = (EditText) findViewById(R.id.et_splash_pwd);
        mBt_splash_login = (Button) findViewById(R.id.bt_splash_login);
        mBt_splash_register = (Button) findViewById(R.id.bt_splash_register);
    }
}

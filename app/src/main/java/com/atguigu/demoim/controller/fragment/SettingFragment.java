package com.atguigu.demoim.controller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.atguigu.demoim.R;
import com.atguigu.demoim.controller.activity.LoginActivity;
import com.atguigu.demoim.model.Model;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

/**
 * Created by Administrator on 2016/8/8 0008.
 */
public class SettingFragment extends Fragment {

    private Button mBt_setting_logout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, null);
        mBt_setting_logout = (Button) view.findViewById(R.id.bt_setting_logout);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                String currentUser = EMClient.getInstance().getCurrentUser();
                mBt_setting_logout.setText("退出登录(" + currentUser + ")");
            }
        });

        mBt_setting_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void logout() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                EMClient.getInstance().logout(false, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                        Model.getInstance().getDBManager().close();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "成功退出", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onError(int i, final String s) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "退出失败 :"+s, Toast.LENGTH_SHORT).show();
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
}

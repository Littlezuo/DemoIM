package com.atguigu.demoim;

import android.app.Application;
import android.content.Context;

import com.atguigu.demoim.model.Model;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;

/**
 * Created by Administrator on 2016/8/7 0007.
 */
public class IMapplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化EaseUI
        initEaseUI();
        //初始化模型数据层数据
        Model.getInstance().init(this);
        this.mContext = this;

    }
    //获取全局的上下文
    public static Context getApplication() {
        return mContext;
    }

    private void initEaseUI() {
        EMOptions emOptions = new EMOptions();
        emOptions.setAutoAcceptGroupInvitation(false);
        emOptions.setAcceptInvitationAlways(false);
        EaseUI.getInstance().init(this, emOptions);
    }
}

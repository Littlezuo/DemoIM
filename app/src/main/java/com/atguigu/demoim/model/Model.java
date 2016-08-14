package com.atguigu.demoim.model;

import android.content.Context;

import com.atguigu.demoim.model.bean.UserInfo;
import com.atguigu.demoim.model.dao.UserAccountDAO;
import com.atguigu.demoim.model.db.DBManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/8/7 0007.
 */
public class Model {
    private Context context;

    private Model() {

    }
    private static Model instance = new Model();

    public static Model getInstance() {
        return instance;
    }

    //创建全局线程池对象
    private ExecutorService mExecutorService = Executors.newCachedThreadPool();

    public ExecutorService getGlobalThreadPool() {
        return mExecutorService;
    }

    public void init(Context context) {
        this.context = context;
        mUserAccountDAO = new UserAccountDAO(context);
        EventListener eventListener = new EventListener(context);
    }
    private UserAccountDAO mUserAccountDAO;

    public UserAccountDAO getUserAccountDAO() {
        return mUserAccountDAO;
    }

    public void loginSuccess(UserInfo accout) {
        if(accout == null) {
            return;
        }
        if(mDBManager!= null) {
            mDBManager.close();
        }
        mDBManager = new DBManager(context,accout.getName());
    }

    private DBManager mDBManager;

    public DBManager getDBManager() {
        return mDBManager;
    }
}

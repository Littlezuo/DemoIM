package com.atguigu.demoim.model.bean;

/**
 * Created by Administrator on 2016/8/8 0008.
 */
public class PickContactInfo {
    private UserInfo user;
    private boolean isCheck;

    public PickContactInfo() {
    }

    public PickContactInfo(UserInfo user, boolean isCheck) {
        this.user = user;
        this.isCheck = isCheck;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }
}

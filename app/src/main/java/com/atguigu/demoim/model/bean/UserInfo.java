package com.atguigu.demoim.model.bean;

/**
 * Created by Administrator on 2016/8/7 0007.
 */
public class UserInfo {
    private String hxId;
    private String name;
    private String nick;
    private String photo;

    public UserInfo() {
    }

    public UserInfo(String hxId) {
        this.hxId = hxId;
        this.name = hxId;
        this.nick = hxId;
    }

    public String getHxId() {
        return hxId;
    }

    public void setHxId(String hxId) {
        this.hxId = hxId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}

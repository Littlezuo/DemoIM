package com.atguigu.demoim.model.bean;

/**
 * Created by Administrator on 2016/8/5 0005.
 */
public class InvitationInfo {
    private UserInfo user; //个人
    private EMGroup Group;//群
    private String reason;  //加入群的原因
    private InvitationStatus status;//邀请的状态

    public InvitationInfo() {
    }

    public InvitationInfo(UserInfo user, EMGroup group, String reason, InvitationStatus status) {
        this.user = user;
        Group = group;
        this.reason = reason;
        this.status = status;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public EMGroup getGroup() {
        return Group;
    }

    public void setGroup(EMGroup group) {
        Group = group;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public InvitationStatus getStatus() {
        return status;
    }

    public void setStatus(InvitationStatus status) {
        this.status = status;
    }

    public enum InvitationStatus{
        // contact invite status
        NEW_INVITE,// 新邀请
        INVITE_ACCEPT,//接受邀请
        INVITE_ACCEPT_BY_PEER,// 邀请被接受

        //收到邀请去加入群
        NEW_GROUP_INVITE,

        //收到申请群加入
        NEW_GROUP_APPLICATION,

        //群邀请已经被对方接受
        GROUP_INVITE_ACCEPTED,

        //群申请已经被批准
        GROUP_APPLICATION_ACCEPTED,

        //接受了群邀请
        GROUP_ACCEPT_INVITE,

        //批准的群加入申请
        GROUPO_ACCEPT_APPLICATION,

        //拒绝了群邀请
        GROUP_REJECT_INVITE,

        //拒绝了群申请加入
        GROUP_REJECT_APPLICATION,

        //群邀请被对方拒绝
        GROUP_INVITE_DECLINED,

        //群申请被拒绝
        GROUP_APPLICATION_DECLINED

    }

    @Override
    public String toString() {
        return "InvitationInfo{" +
                "user=" + user +
                ", Group=" + Group +
                ", reason='" + reason + '\'' +
                ", status=" + status +
                '}';
    }
}

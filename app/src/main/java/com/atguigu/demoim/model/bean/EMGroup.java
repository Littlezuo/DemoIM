package com.atguigu.demoim.model.bean;

/**
 * Created by Administrator on 2016/8/5 0005.
 */
public class EMGroup {
    private String GroupName; //群名称
    private String groupId;  //群的环信id
    private String invitePerson; //邀请人的信息

    public EMGroup() {
    }

    public EMGroup(String groupName, String groupId, String invitePerson) {
        GroupName = groupName;
        this.groupId = groupId;
        this.invitePerson = invitePerson;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getInvitePerson() {
        return invitePerson;
    }

    public void setInvitePerson(String invitePerson) {
        this.invitePerson = invitePerson;
    }
}

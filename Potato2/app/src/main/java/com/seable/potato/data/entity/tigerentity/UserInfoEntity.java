package com.seable.potato.data.entity.tigerentity;

import java.io.Serializable;

/**
 * Created by yaohu on 15/5/11.
 * <p/>
 * ‘id’:用户ID,userName:用户名,realName:真实姓名,position:真实姓名，avatar:头像地址(需要单独发请求获取)
 */
public class UserInfoEntity implements Serializable {

    private String id;
    private String userName;
    private String realName;
    private String position;
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}

package com.seable.potato.data.entity;


/**
 * 登录信息
 */
public class Entity_UserPass {
    private boolean isPass;//保存密码
    private boolean isLogin;//自动登录
    private String username;//发布留言的用户
    private String password;//发布留言的密码

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPass() {
        return isPass;
    }

    public void setPass(boolean isPass) {
        this.isPass = isPass;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }


}

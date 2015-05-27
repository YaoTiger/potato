package com.seable.potato.data.entity;

/**
 * @author 王维玉
 * @ClassName: Entity_Sql_User
 * @Description:用户登陆信息
 * @date 2015-03-12 11:58
 */
public class Entity_Sql_User {

    //扫码信息ID
    private int id;
    //扫码信息 条码号
    private String username;
    //扫码信息 条码内容
    private String password;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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


}

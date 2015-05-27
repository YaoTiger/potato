package com.seable.potato.data.entity;


/**
 * @author 王维玉
 * @ClassName: Entity_User
 * @Description: 用户信息
 * @date 2015-01-26 17:07
 */
public class Entity_User {

    private int id;
    private String phone;
    private int duty;        //职务
    private int organizeId;        //部门

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDuty() {
        return duty;
    }

    public void setDuty(int duty) {
        this.duty = duty;
    }

    public int getOrganizeId() {
        return organizeId;
    }

    public void setOrganizeId(int organizeId) {
        this.organizeId = organizeId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}

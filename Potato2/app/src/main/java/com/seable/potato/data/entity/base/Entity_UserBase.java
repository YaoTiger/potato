package com.seable.potato.data.entity.base;

import com.seable.potato.data.entity.Entity_User;

import java.io.Serializable;


/**
 * @author 王维玉
 * @ClassName: Entity_UserList
 * @Description: 用户信息外层
 * @date 2015-01-26 17:07
 */
public class Entity_UserBase extends Entity_Base implements Serializable {

    public static final long serialVersionUID = 1L;
    private Entity_User datas;

    public Entity_User getDatas() {
        return datas;
    }

    public void setDatas(Entity_User datas) {
        this.datas = datas;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}

package com.seable.potato.data.entity.base;

import com.seable.potato.data.entity.Entity_SubdistrictList;

import java.io.Serializable;


/**
 * @author 王维玉
 * @ClassName: Entity_RepairBase
 * @Description: 街道办服务外层
 * @date 2015-01-29 10:14
 */
public class Entity_SubdistrictListBase extends Entity_Base implements Serializable {

    public static final long serialVersionUID = 1L;
    private Entity_SubdistrictList datas;

    public Entity_SubdistrictList getDatas() {
        return datas;
    }

    public void setDatas(Entity_SubdistrictList datas) {
        this.datas = datas;
    }

}

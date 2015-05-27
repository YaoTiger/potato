package com.seable.potato.data.entity.base;

import com.seable.potato.data.entity.Entity_Location;

import java.io.Serializable;
import java.util.List;


/**
 * @author 王维玉
 * @ClassName: Entity_LocationBase
 * @Description: 紧急救援发出人信息列表外层
 * @date 2015-03-02 10:14
 */
public class Entity_LocationBase extends Entity_Base implements Serializable {

    public static final long serialVersionUID = 1L;
    private List<Entity_Location> datas;

    public List<Entity_Location> getDatas() {
        return datas;
    }

    public void setDatas(List<Entity_Location> datas) {
        this.datas = datas;
    }

}

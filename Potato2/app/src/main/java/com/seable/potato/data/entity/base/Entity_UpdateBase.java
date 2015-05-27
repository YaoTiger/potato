package com.seable.potato.data.entity.base;

import com.seable.potato.data.entity.Entity_Update;

import java.io.Serializable;


/**
 * @author 王维玉
 * @ClassName: Entity_UpdateBase
 * @Description: 版本更新外层
 * @date 2015-02-02 16:38
 */
public class Entity_UpdateBase extends Entity_Base implements Serializable {

    public static final long serialVersionUID = 1L;
    private Entity_Update datas;

    public Entity_Update getDatas() {
        return datas;
    }

    public void setDatas(Entity_Update datas) {
        this.datas = datas;
    }

}

package com.seable.potato.data.entity.base;

import com.seable.potato.data.entity.Entity_ProviderCompany;

import java.io.Serializable;


/**
 * @author 王维玉
 * @ClassName: Entity_ProviderCompanyBase
 * @Description: 关于我们信息外层
 * @date 2015-01-26 17:07
 */
public class Entity_ProviderCompanyBase extends Entity_Base implements Serializable {

    public static final long serialVersionUID = 1L;
    private Entity_ProviderCompany datas;

    public Entity_ProviderCompany getDatas() {
        return datas;
    }

    public void setDatas(Entity_ProviderCompany datas) {
        this.datas = datas;
    }

}

package com.seable.potato.data.entity.base;

import java.io.Serializable;

public class Entity_DataBase extends Entity_Base implements Serializable {

    public static final long serialVersionUID = 1L;
    private String datas;

    public String getDatas() {
        return datas;
    }

    public void setDatas(String datas) {
        this.datas = datas;
    }

}

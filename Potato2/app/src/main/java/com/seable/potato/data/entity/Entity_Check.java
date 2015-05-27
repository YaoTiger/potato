package com.seable.potato.data.entity;

import java.io.Serializable;

/**
 * @author 王维玉
 * @ClassName: Entity_Check
 * @Description: 檢測檢查項
 * @date 2015年01月24日 08:36
 */
public class Entity_Check implements Serializable{
    private int num;

    private String name;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Entity_Check{" +
                "num=" + num +
                ", name='" + name + '\'' +
                '}';
    }
}

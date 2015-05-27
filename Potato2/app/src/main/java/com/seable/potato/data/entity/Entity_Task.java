package com.seable.potato.data.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author 王维玉
 * @ClassName: Entity_Task
 * @Description: 任务详情
 * @date 2015年03月30日   13:47
 */
public class Entity_Task implements Serializable {
    private List<DetailDisplayItem> list;
    private Entity_Task_Item item;
    private String id;
    private int type;
    private int status;

    public Entity_Task_Item getItem() {
        return item;
    }

    public void setItem(Entity_Task_Item item) {
        this.item = item;
    }

        public List<DetailDisplayItem> getList() {
        return list;
    }

    public void setList(List<DetailDisplayItem> list) {
        this.list = list;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


}

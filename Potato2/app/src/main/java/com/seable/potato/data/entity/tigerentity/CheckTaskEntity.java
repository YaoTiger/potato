package com.seable.potato.data.entity.tigerentity;

import com.seable.potato.data.entity.Entity_Check;
import com.seable.potato.data.entity.Entity_Task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckTaskEntity implements Serializable{

	public String id;
	public String taskId;
	public String userId;
	public int recordType;
	public String picId;
	public String remark;
	public int totalNum;
	public List<Entity_Check> entities=new ArrayList<Entity_Check>();

    @Override
    public boolean equals(Object o) {
        CheckTaskEntity entity=(CheckTaskEntity)o;
        return id.equals(entity.id);
    }


    public CheckTaskEntity clone(CheckTaskEntity entity){
        id=entity.id;
        taskId=entity.taskId;
        userId=entity.userId;
        recordType=entity.recordType;
        picId=entity.picId;
        remark=entity.remark;
        totalNum=entity.totalNum;
        entities=entity.entities;
        return this;
    }
}

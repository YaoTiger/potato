package com.seable.potato.data.entity.tigerentity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;
import com.seable.potato.data.entity.Entity_Task;

import java.io.Serializable;

/**
 * Created by yaohu on 15/5/11.
 * <p/>
 * {‘id’:用户ID,
 * status:状态信息
 * taskType:任务描述,
 * planDate:应完成日期,
 * batchNo:种子批编码，
 * company:供应商名称,
 * address:供应商地址
 * }
 */
@Table(name = "taskInfo")
public class TaskInfoEntity implements Serializable {

    @NoAutoIncrement
    private String id;
    @Column(column="status")
    private int status;
    @Column(column="taskType")
    private int taskType;
    @Column(column="planDate")
    private String planDate;
    @Column(column="batchNo")
    private String batchNo;
    @Column(column="company")
    private String company;
    @Column(column="address")
    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

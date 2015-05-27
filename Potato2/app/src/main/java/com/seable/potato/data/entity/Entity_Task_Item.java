package com.seable.potato.data.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;
import com.seable.potato.data.db.UseCase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author 王维玉
 * @ClassName: Entity_Task
 * @Description: 任务详情子项
 * @date 2015年03月30日   10:39
 */
@Table(name = "entity_task_item")
public class Entity_Task_Item {


    @NoAutoIncrement
    private String id;

    public String taskId;

    @Column(column = "planDate")
    private String planDate;


    @Column(column = "status")
    private int status;

    @Column(column = "taskType")
    private String taskType;

    @Column(column = "taskTypeName")
    private String taskTypeName;

    @Column(column = "batchNo")
    private String batchNo;

    @Column(column = "breed")
    private String breed;

    @Column(column = "befFram")
    private String befFram;

    @Column(column = "bbefFram")
    private String bbefFram;

    @Column(column = "farmDate")
    private String farmDate;

    @Column(column = "shield")
    private String shield;

    @Column(column = "chemicals")
    private String chemicals;


    @Column(column = "manName")
    private String manName;

    @Column(column = "manPhone")
    private String manPhone;

    @Column(column = "areaNum")
    private String areaNum;

    @Column(column = "grade")
    private String grade;

    @Column(column = "company")
    private String company;

    @Column(column = "address")
    private String address;


    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setPlanDate(String planDate){
        this.planDate = planDate;
    }
    public String getPlanDate(){
        return this.planDate;
    }
    public void setStatus(int status){
        this.status = status;
    }
    public int getStatus(){
        return this.status;
    }
    public void setTaskType(String taskType){
        this.taskType = taskType;
    }
    public String getTaskType(){
        return this.taskType;
    }
    public void setTaskTypeName(String taskTypeName){
        this.taskTypeName = taskTypeName;
    }
    public String getTaskTypeName(){
        return this.taskTypeName;
    }
    public void setBatchNo(String batchNo){
        this.batchNo = batchNo;
    }
    public String getBatchNo(){
        return this.batchNo;
    }
    public void setBreed(String breed){
        this.breed = breed;
    }
    public String getBreed(){
        return this.breed;
    }
    public void setBefFram(String befFram){
        this.befFram = befFram;
    }
    public String getBefFram(){
        return this.befFram;
    }
    public void setBbefFram(String bbefFram){
        this.bbefFram = bbefFram;
    }
    public String getBbefFram(){
        return this.bbefFram;
    }
    public void setFarmDate(String farmDate){
        this.farmDate = farmDate;
    }
    public String getFarmDate(){
        return this.farmDate;
    }
    public void setShield(String shield){
        this.shield = shield;
    }
    public String getShield(){
        return this.shield;
    }
    public void setChemicals(String chemicals){
        this.chemicals = chemicals;
    }
    public String getChemicals(){
        return this.chemicals;
    }
    public void setManName(String manName){
        this.manName = manName;
    }
    public String getManName(){
        return this.manName;
    }
    public void setManPhone(String manPhone){
        this.manPhone = manPhone;
    }
    public String getManPhone(){
        return this.manPhone;
    }
    public void setAreaNum(String areaNum){
        this.areaNum = areaNum;
    }
    public String getAreaNum(){
        return this.areaNum;
    }
    public void setGrade(String grade){
        this.grade = grade;
    }
    public String getGrade(){
        return this.grade;
    }
    public void setCompany(String company){
        this.company = company;
    }
    public String getCompany(){
        return this.company;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public String getAddress(){
        return this.address;
    }

}

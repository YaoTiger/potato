package com.seable.potato.data.entity.tigerentity;

import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by yaohu on 15/5/11.
 * ‘manName’:种植人或代理人姓名
 * ,manPhone:电话,
 * breed:品种,
 * areaNum:面积，
 * grade:种薯级别，
 * batchNo:种子批编号
 * farmDate:播种日期
 * befFram:前茬去年
 * bbefFram:前茬前年
 * shield:隔离,
 * chemicals:前茬及本茬农药使用情况，
 * company:生产厂家
 * address:地址位置
 */
@Table(name = "taskdetail")
public class TaskDetailEntity {


    private String manName;
    private String manPhone;
    private String breed;
    private String areaNum;
    private String grade;
    private String batchNo;
    private String farmDate;
    private String befFram;
    private String bbefFram;
    private String shield;
    private String chemicals;
    private String company;
    private String address;

    public String getManName() {
        return manName;
    }

    public void setManName(String manName) {
        this.manName = manName;
    }

    public String getManPhone() {
        return manPhone;
    }

    public void setManPhone(String manPhone) {
        this.manPhone = manPhone;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getAreaNum() {
        return areaNum;
    }

    public void setAreaNum(String areaNum) {
        this.areaNum = areaNum;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getFarmDate() {
        return farmDate;
    }

    public void setFarmDate(String farmDate) {
        this.farmDate = farmDate;
    }

    public String getBefFram() {
        return befFram;
    }

    public void setBefFram(String befFram) {
        this.befFram = befFram;
    }

    public String getBbefFram() {
        return bbefFram;
    }

    public void setBbefFram(String bbefFram) {
        this.bbefFram = bbefFram;
    }

    public String getShield() {
        return shield;
    }

    public void setShield(String shield) {
        this.shield = shield;
    }

    public String getChemicals() {
        return chemicals;
    }

    public void setChemicals(String chemicals) {
        this.chemicals = chemicals;
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

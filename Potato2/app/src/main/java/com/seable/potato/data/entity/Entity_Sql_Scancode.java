package com.seable.potato.data.entity;

/**
 * @author 王维玉
 * @ClassName: Entity_SQL_SCANCODE
 * @Description:离线扫码信息
 * @date 2015-03-03 13:12
 */
public class Entity_Sql_Scancode {

    //扫码信息ID
    private int id;
    //扫码信息 条码号
    private String mScancodeNum;
    //扫码信息 条码内容
    private String mScancodeContent;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getmScancodeNum() {
        return mScancodeNum;
    }

    public void setmScancodeNum(String mScancodeNum) {
        this.mScancodeNum = mScancodeNum;
    }

    public String getmScancodeContent() {
        return mScancodeContent;
    }

    public void setmScancodeContent(String mScancodeContent) {
        this.mScancodeContent = mScancodeContent;
    }


}

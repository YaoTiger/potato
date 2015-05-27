package com.seable.potato.data.entity;


/**
 * @author 王维玉
 * @ClassName: Entity_Location
 * @Description: 紧急救援发出人信息
 * @date 2015-03-02 10:12
 */
public class Entity_Location {
    /**
     * 发出救援用户ID
     */
    private int id;
    /**
     * 该条救援信息是否处理 1已处理2未处理
     */
    private int isDeal;
    /**
     * 发出救援用户姓名
     */
    private String name;
    /**
     * 救援信息发出时间
     */
    private String time;
    /**
     * 发出救援用户经度
     */
    private double lon;
    /**
     * 发出救援用户纬度
     */
    private double lat;
    /**
     * 发出救援用户住址
     */
    private String add;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLon() {
        return lon;
    }

    public int getIsDeal() {
        return isDeal;
    }

    public void setIsDeal(int isDeal) {
        this.isDeal = isDeal;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}

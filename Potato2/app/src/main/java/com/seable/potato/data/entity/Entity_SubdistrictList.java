package com.seable.potato.data.entity;

import java.util.List;

/**
 * @author 王维玉
 * @ClassName: SubdistrictInfo
 * @Description: 街道办服务三个列表model
 * @date 2015年01月29日 10:15
 */
public class Entity_SubdistrictList {

    private List<Entity_Subdistrict> civil;
    private List<Entity_Subdistrict> population;
    private List<Entity_Subdistrict> other;

    public List<Entity_Subdistrict> getCivil() {
        return civil;
    }

    public void setCivil(List<Entity_Subdistrict> civil) {
        this.civil = civil;
    }

    public List<Entity_Subdistrict> getPopulation() {
        return population;
    }

    public void setPopulation(List<Entity_Subdistrict> population) {
        this.population = population;
    }

    public List<Entity_Subdistrict> getOther() {
        return other;
    }

    public void setOther(List<Entity_Subdistrict> other) {
        this.other = other;
    }


}

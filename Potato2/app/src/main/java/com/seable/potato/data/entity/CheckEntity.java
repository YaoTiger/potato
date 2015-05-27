package com.seable.potato.data.entity;

import com.lidroid.xutils.db.annotation.Check;

/**
 * Created by yaohu on 15/5/23.
 */
public class CheckEntity {
  public String fileName;
    public String filePath;
    public int id;

    public  CheckEntity(String filePath){
        this.filePath=filePath;
    }
   public  CheckEntity(){}

}

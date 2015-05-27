package com.seable.potato.util;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 王维玉
 * @ClassName: GsonUtils
 * @Description: Gson解析工具类
 * @date 2015-01-19 15:44
 */
public class GsonUtils {

    /**
     * 解析单独的json数据
     *
     * @param String   json 需要解析的数据
     * @param Object   obj 数据model
     * @param Class<T> cls 数据model的class
     * @return Object 解析后的数据model
     */
    public static <T> Object resolveGsonData(String json, Object obj, Class<T> cls) {
        Gson gson = new Gson();
        obj = gson.fromJson(json, cls);
        return obj;

    }

    /**
     * 解析list的json数据
     *
     * @param String json 需要解析的数据
     * @param Object obj 数据model
     * @return List<Object> 存放Object的list
     */
    public static <T> List<Object> resolveGsonListData(String json, Object obj) {
        Type listType = new TypeToken<LinkedList<Object>>() {
        }.getType();
        Gson gson = new Gson();
        LinkedList<Object> list = gson.fromJson(json, listType);
        return list;

    }


}

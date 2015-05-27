package com.seable.potato.util.image.service.impl;

import com.seable.potato.util.image.entity.CacheObject;
import com.seable.potato.util.image.service.CacheFullRemoveType;
import com.seable.potato.util.image.util.ObjectUtils;


/**
 * Remove type when cache is full.<br/>
 * when cache is full, compare data of object in cache, if data is bigger remove it first.<br/>
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2011-12-26
 */
public class RemoveTypeDataBig<T> implements CacheFullRemoveType<T> {

    private static final long serialVersionUID = 1L;

    @Override
    public int compare(CacheObject<T> obj1, CacheObject<T> obj2) {
        return ObjectUtils.compare(obj2.getData(), obj1.getData());
    }
}

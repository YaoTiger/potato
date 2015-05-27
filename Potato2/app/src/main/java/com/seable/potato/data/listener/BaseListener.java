package com.seable.potato.data.listener;

/**
 * @author 王维玉
 * @ClassName: BaseListener
 * @Description: 通用回调函数
 * @date 2015年01月19日 13:05
 */
public interface BaseListener {

    /**
     * 回调函数
     *
     * @param l
     */
    public void onResult(int n, Object obj1, Object obj2);
}

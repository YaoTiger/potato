package com.seable.potato.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @author 王维玉
 * @ClassName: PreferenceUtil
 * @Description: 调用SharedPreferences工具类
 * @date 2015-01-17 10:35
 */
public class PreferenceUtil {


    private static PreferenceUtil preferenceUtil;

    private SharedPreferences sp;

    private Editor ed;

    private PreferenceUtil(Context context, String Preferences_name) {
        init(context, Preferences_name);
    }

    /**
     * 初始化SharedPreferences
     *
     * @param context          Context
     * @param Preferences_name SharedPreferences名称
     */
    public void init(Context context, String Preferences_name) {
        if (sp == null || ed == null) {
            try {
                sp = context.getSharedPreferences(Preferences_name, 0);
                ed = sp.edit();
            } catch (Exception e) {
            }
        }
    }

    public static PreferenceUtil getInstance(Context context, String Preferences_name) {
        if (preferenceUtil == null) {
            preferenceUtil = new PreferenceUtil(context, Preferences_name);
        }
        return preferenceUtil;
    }

    /**
     * SharedPreferences储存Long型数据
     *
     * @param key 保存名称
     * @param l   保存类型
     */
    public void saveLong(String key, long l) {
        ed.putLong(key, l);
        ed.commit();
    }

    /**
     * SharedPreferences读取Long型数据
     *
     * @param key 保存名称
     * @param l   保存类型
     */
    public long getLong(String key, long defaultlong) {
        return sp.getLong(key, defaultlong);
    }

    /**
     * SharedPreferences储存boolean型数据
     *
     * @param key 保存名称
     * @param l   保存类型
     */
    public void saveBoolean(String key, boolean value) {

        ed.putBoolean(key, value);
        ed.commit();
    }

    /**
     * SharedPreferences读取boolean型数据
     *
     * @param key 保存名称
     * @param l   保存类型
     */
    public boolean getBoolean(String key, boolean defaultboolean) {
        return sp.getBoolean(key, defaultboolean);
    }

    /**
     * SharedPreferences储存int型数据
     *
     * @param key 保存名称
     * @param l   保存类型
     */
    public void saveInt(String key, int value) {
        if (ed != null) {
            ed.putInt(key, value);
            ed.commit();
        }
    }

    /**
     * SharedPreferences读取int型数据
     *
     * @param key 保存名称
     * @param l   保存类型
     */
    public int getInt(String key, int defaultInt) {
        return sp.getInt(key, defaultInt);
    }

    /**
     * SharedPreferences读取String型数据
     *
     * @param key 保存名称
     * @param l   保存类型
     */
    public String getString(String key, String defaultInt) {
        return sp.getString(key, defaultInt);
    }


    /**
     * SharedPreferences储存String型数据
     *
     * @param key 保存名称
     * @param l   保存类型
     */
    public void saveString(String key, String value) {
        ed.putString(key, value);
        ed.commit();
    }

    /**
     * 删除SharedPreferences文件制定关键字
     *
     * @param key 指定的关键字
     */
    public void remove(String key) {
        ed.remove(key);
        ed.commit();
    }

    /**
     * 删除SharedPreferences文件
     */
    public void clear() {
        ed.clear();
        ed.commit();
    }

    public void destroy() {
        sp = null;
        ed = null;
        preferenceUtil = null;
    }
}

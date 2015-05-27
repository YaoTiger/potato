package com.seable.potato.util;


import android.content.Context;

import com.seable.potato.data.entity.Entity_UserPass;

/**
 * @author 王维玉
 * @ClassName: PreferencesService
 * @Description: 可以直接调用的偏好设置工具类
 * @date 2015-01-17 10:50
 */
public class PreferencesService {
    /**
     * Preferences_name *
     */
    public static final String PREFERENCE_NAME_APK_SETTING = "property_apk_setting";
    /**
     * Preferences Key *
     */
    public static final String PREFERENCE_KEY_APK_SETTING_SOUND = "property_apk_push_sound";
    /**
     * Preferences Key *
     */
    public static final String PREFERENCE_KEY_APK_SETTING_SHAKE = "property_apk_push_shake";
    /**
     * Preferences Key *
     */
    public static final String PREFERENCE_KEY_APK_SETTING_NUM = "property_apk_push_num";
    /**
     * Preferences Key *
     */
    public static final String PREFERENCE_KEY_APK_SETTING_PUSH = "property_apk_push";
    /**
     * Preferences Key *
     */
    public static final String PREFERENCE_KEY_APK_SETTING_NOTIFICATION_FROM = "property_apk_notification_from";
    /**
     * Preferences_name *
     */
    public static final String PREFERENCE_NAME_SOS_SETTING = "property_sos_setting";
    /**
     * Preferences Key *
     */
    public static final String PREFERENCE_KEY_SOS_SETTING_OPEN = "property_sos_open";

    /**
     * 会员信息Preferences_name *
     */
    public static final String PREFERENCE_NAME_USER = "property_user_info";
    /**
     * 会员信息用户名 Key *
     */
    public static final String PREFERENCE_KEY_USER_NAME = "property_name";
    /**
     * 会员信息密码 Key *
     */
    public static final String PREFERENCE_KEY_USER_PASS = "property_pass";
    /**
     * 是否记住密码 Key *
     */
    public static final String PREFERENCE_KEY_USER_IS_PASS = "property_isPass";
    /**
     * 是否自动登录 Key *
     */
    public static final String PREFERENCE_KEY_USER_IS_LOGIN = "property_isLogin";
    private static PreferencesService mInstantce;

    public PreferencesService() {
    }

    private static Context mContext;

    public PreferencesService(Context context) {
        mContext = context;
        if (mInstantce == null) {
            mInstantce = new PreferencesService();
        }
    }


    /**
     * 保存用户紧急救援关联电话信息
     */
    public void saveSosSetting(int info) {
        PreferenceUtil.getInstance(mContext, PREFERENCE_NAME_SOS_SETTING)
                .saveInt(PREFERENCE_KEY_SOS_SETTING_OPEN, info);
    }

    /**
     * 获取用户紧急救援关联电话信息
     */
    public int getSosSetting() {
        int info = PreferenceUtil.getInstance(mContext, PREFERENCE_NAME_SOS_SETTING)
                .getInt(PREFERENCE_KEY_SOS_SETTING_OPEN, -1);
        return info;
    }

    /**
     * 保存用户是否开启推送信息标志
     */
    public void saveApkPushSetting(boolean info) {
        PreferenceUtil.getInstance(mContext, PREFERENCE_NAME_APK_SETTING)
                .saveBoolean(PREFERENCE_KEY_APK_SETTING_PUSH, info);
    }

    /**
     * 获取用户是否开启推送信息标志
     */
    public boolean getApkPushSetting() {
        boolean info = PreferenceUtil.getInstance(mContext, PREFERENCE_NAME_APK_SETTING)
                .getBoolean(PREFERENCE_KEY_APK_SETTING_PUSH, true);
        return info;
    }

    /**
     * 保存用户是否打开声音信息
     */
    public void saveSoundState(boolean isSound) {
        PreferenceUtil.getInstance(mContext, PREFERENCE_NAME_APK_SETTING)
                .saveBoolean(PREFERENCE_KEY_APK_SETTING_SOUND, isSound);
    }

    /**
     * 获取用户是否打开声音信息
     */
    public boolean getSoundState() {
        boolean info = PreferenceUtil.getInstance(mContext, PREFERENCE_NAME_APK_SETTING)
                .getBoolean(PREFERENCE_KEY_APK_SETTING_SOUND, true);
        return info;
    }

    /**
     * 保存用户是否打开震动信息
     */
    public void saveShakeState(boolean isShake) {
        PreferenceUtil.getInstance(mContext, PREFERENCE_NAME_APK_SETTING)
                .saveBoolean(PREFERENCE_KEY_APK_SETTING_SHAKE, isShake);
    }

    /**
     * 获取用户是否打开震动信息
     */
    public boolean getShakeState() {
        boolean info = PreferenceUtil.getInstance(mContext, PREFERENCE_NAME_APK_SETTING)
                .getBoolean(PREFERENCE_KEY_APK_SETTING_SHAKE, false);
        return info;
    }

    /**
     * 保存未读通知数量
     */
    public void saveNoticeNum(int isShake) {
        PreferenceUtil.getInstance(mContext, PREFERENCE_NAME_APK_SETTING)
                .saveInt(PREFERENCE_KEY_APK_SETTING_NUM, isShake);
    }

    /**
     * 获取未读通知数量
     */
    public int getNoticeNum() {
        int info = PreferenceUtil.getInstance(mContext, PREFERENCE_NAME_APK_SETTING)
                .getInt(PREFERENCE_KEY_APK_SETTING_NUM, 0);
        System.out.println("unread " + info);
        return info;
    }


    /**
     * 保存跳转到我的消息页的来源界面标志
     */
    public void saveApkNotificationFrom(boolean info) {
        PreferenceUtil.getInstance(mContext, PREFERENCE_NAME_APK_SETTING)
                .saveBoolean(PREFERENCE_KEY_APK_SETTING_PUSH, info);
    }

    /**
     * 获取跳转到我的消息页的来源界面标志
     */
    public boolean getApkNotificationFrom() {
        boolean info = PreferenceUtil.getInstance(mContext, PREFERENCE_NAME_APK_SETTING)
                .getBoolean(PREFERENCE_KEY_APK_SETTING_PUSH, false);
        return info;
    }

    /**
     * 清除所有SharedPreference文件
     */
    public int clearAll() {
        PreferenceUtil.getInstance(mContext, PREFERENCE_NAME_APK_SETTING)
                .clear();
        PreferenceUtil.getInstance(mContext, PREFERENCE_NAME_SOS_SETTING)
                .clear();
        PreferenceUtil.getInstance(mContext, PREFERENCE_NAME_USER)
                .clear();
        return 3;
    }

    /**
     * 清除登陆文件
     */
    public void clearLoginInfo() {
        PreferenceUtil.getInstance(mContext, PREFERENCE_NAME_USER)
                .clear();
    }

    /**
     * 获取用户登陆状态 是否记住密码
     */
    public boolean isRememberPassword(Context mContext) {

        boolean flagStatus = PreferenceUtil.getInstance(mContext,
                PREFERENCE_NAME_USER).getBoolean(
                PREFERENCE_KEY_USER_IS_PASS, true);
        return flagStatus;
    }

    /**
     * 获取用户登陆状态 是否自动登录
     */
    public boolean isAutomaticallyLogin(Context mContext) {

        boolean flagStatus = PreferenceUtil.getInstance(mContext,
                PREFERENCE_NAME_USER).getBoolean(
                PREFERENCE_KEY_USER_IS_LOGIN, true);
        return flagStatus;
    }

    /**
     * 存储用户登陆状态 是否记住密码
     */
    public void saveRememberPassword(Context mContext, boolean flagStatus) {

        PreferenceUtil
                .getInstance(mContext, PREFERENCE_NAME_USER)
                .saveBoolean(PREFERENCE_KEY_USER_IS_PASS, flagStatus);
    }

    /**
     * 存储用户登陆状态 是否自动登录
     */
    public void saveAutomaticallyLogin(Context mContext,
                                       boolean flagStatus) {

        PreferenceUtil
                .getInstance(mContext, PREFERENCE_NAME_USER)
                .saveBoolean(PREFERENCE_KEY_USER_IS_LOGIN, flagStatus);
    }

    /**
     * 获取用户登陆各种信息
     */
    public Entity_UserPass getUserInfo(Context mContext) {
        Entity_UserPass info = new Entity_UserPass();
        info.setPass(isRememberPassword(mContext));
        info.setLogin(isAutomaticallyLogin(mContext));
        info.setUsername(PreferenceUtil.getInstance(mContext,
                PREFERENCE_NAME_USER).getString(
                PREFERENCE_KEY_USER_NAME, ""));
        info.setPassword(PreferenceUtil.getInstance(mContext,
                PREFERENCE_NAME_USER).getString(
                PREFERENCE_KEY_USER_PASS, ""));
        return info;
    }

    /**
     * 存储用户信息
     */
    public void saveUserInfo(Context mContext, Entity_UserPass info) {
        PreferenceUtil
                .getInstance(mContext, PREFERENCE_NAME_USER)
                .saveString(PREFERENCE_KEY_USER_NAME, info.getUsername());
        PreferenceUtil
                .getInstance(mContext, PREFERENCE_NAME_USER)
                .saveString(PREFERENCE_KEY_USER_PASS, info.getPassword());
    }


}

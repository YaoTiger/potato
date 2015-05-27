package com.seable.potato.util;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;


/**
 * @author 王维玉
 * @ClassName: DeviceUtils
 * @Description: 获取设备信息工具类
 * @date 2015-01-19 15:44
 */
public class DeviceUtils {

    /**
     * 獲取mac地址
     *
     * @return mac地址
     */
    public static String getMac(Context context) {
        try {
            android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            String mac = wifi.getConnectionInfo().getMacAddress();
            return mac;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取版本号
     *
     * @return 当前应用的版本code
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "1.0";
        }
    }

    /**
     * 获取应用名称
     *
     * @return 当前应用的名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.applicationInfo.name;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "智慧家园物业管理";
        }
    }

    /**
     * 獲取设备ID
     *
     * @return 设备ID
     */
    public static String getDeviceID(Context context) {
        try {
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);

            String device_id = tm.getDeviceId();
            if (TextUtils.isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(
                        context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
            }
//				   L.e(device_id);
//				   return "862823020304202";
            return device_id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}

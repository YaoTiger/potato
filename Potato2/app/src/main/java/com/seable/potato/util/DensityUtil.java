/**
 * @Title: DensityUtil.java
 * @Package com.qykj.ett315.util
 * @Description: (用一句话描述该文件做什么)
 * @author A18ccms A18ccms_gmail_com
 * @date 2013-10-25 下午4:26:13
 * @version V1.0
 */
package com.seable.potato.util;

import android.content.Context;
import android.util.Log;

/**
 * @author 王国平
 * @ClassName: DensityUtil
 * @Description: dp和px转换(这里用一句话描述这个类的作用)
 * @date 2013-10-25 下午4:26:13
 */
public class DensityUtil {
    private final static String TAG = "DensityUtil";
    private static float density = 0f;
    private static float defaultDensity = 1.5f;// 高分辨率的手机density普遍接近1.5

    public DensityUtil() {
    }

    public static void setDensity(float density) {
        DensityUtil.density = density;
    }

    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 根据手机的分辨率 dp 转成px(像素)
     */
    public static int dip2px(float dpValue) {
        int px;
        if (density == 0) {
            Log.e(TAG,
                    "density is invalid, you should execute DensityUtil.getDensity(Context context) first");
            px = (int) (dpValue * defaultDensity + 0.5f);
        } else {
            px = (int) (dpValue * density + 0.5f);
        }
        Log.i(TAG, "px = " + px);
        return px;
    }

    /**
     * 根据手机的分辨率px(像素) 转成dp
     */
    public static int px2dip(float pxValue) {
        int dp;
        if (density == 0) {
            Log.e(TAG,
                    "density is invalid, you should execute DensityUtil.getDensity(Context context) first");
            dp = (int) (pxValue / defaultDensity + 0.5f);
        } else {
            dp = (int) (pxValue / density + 0.5f);
        }
        Log.i(TAG, "dp = " + dp);
        return dp;
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @param fontScale （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param fontScale （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}

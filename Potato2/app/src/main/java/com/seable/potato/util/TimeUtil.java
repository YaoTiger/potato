package com.seable.potato.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 王维玉
 * @ClassName: TimeUtil
 * @Description: 时间戳时间转换工具类
 * @date 2015-03-03 15:33
 */
@SuppressLint("SimpleDateFormat")
public class TimeUtil {
    public static final String DATA_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final long TIMECODE_DAY = 86400;

    /**
     * 将时间戳转为字符串
     *
     * @param cc_time
     * @return
     */
    public static String getTimeStrFromCode(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat(DATA_FORMAT);
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

    /**
     * 将字符串转为时间戳
     *
     * @param user_time
     * @return
     */
    public static String getTimeCodeFromStr(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat(DATA_FORMAT);
        Date d;
        try {

            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);

        } catch (ParseException e) {
            e.printStackTrace();
            re_time = null;
        }
        return re_time;
    }

    /**
     * 获取将当前时间戳
     *
     * @param user_time
     * @return
     */
    public static String getNowTimeCode() {
        return getTimeCodeFromStr(getNowTime());
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getNowTime() {
        SimpleDateFormat formatter = new SimpleDateFormat(DATA_FORMAT);
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String re_time = formatter.format(curDate);
        return re_time;
    }
}

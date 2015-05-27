package com.seable.potato.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * @author 王维玉
 * @ClassName: PhoneUtil
 * @Description: 调用电话功能工具类，拨打电话发送短信等
 * @date 2015-01-17 11:40
 */
public class PhoneUtil {

    /**
     * makeCall 拨打电话
     *
     * @param mContext String phoneNumber need permission <uses-permission
     *                 android:name="android.permission.CALL_PHONE" />
     */
    public static void makeCall(Context mContext, String phoneNumber) {
        try {
            phoneNumber = phoneNumber.replace("-", "");
            Intent i = new Intent();
            i.setAction(Intent.ACTION_CALL);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            i.setData(Uri.parse("tel:" + phoneNumber));
            mContext.startActivity(i);

        } catch (Exception e) {
            return;
        }
    }

    /**
     * sendSMS 发送短信
     *
     * @param mContext String phoneNumber need permission <uses-permission
     *                 android:name="android.permission.SEND_SMS" />
     */
    public static void sendSMS(Context mContext, String phoneNumber,
                               String smsContent) {
        try {

            Intent i = new Intent();
            i.setAction(Intent.ACTION_SENDTO);
            i.setData(Uri.parse("smsto:" + phoneNumber));
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            i.putExtra("sms_body", smsContent);
            mContext.startActivity(i);
        } catch (Exception e) {
            return;
        }
    }

    /**
     * sendEmail 发送邮件
     *
     * @param mContext String 邮件地址，标题，内容
     */
    public static void sendEmail(Context mContext, String emailAdd,
                                 String emailTitle, String emailContent) {
        try {

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("plain/text");
            // 设置邮件默认地址
            intent.putExtra(Intent.EXTRA_EMAIL, emailAdd);
            // 设置邮件默认标题
            intent.putExtra(Intent.EXTRA_SUBJECT, emailTitle);
            // 设置要默认发送的内容
            intent.putExtra(Intent.EXTRA_TEXT, emailContent);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            // 调用系统的邮件系统
            mContext.startActivity(Intent.createChooser(intent, "请选择邮件发送软件"));
        } catch (Exception e) {
            return;
        }

    }
}

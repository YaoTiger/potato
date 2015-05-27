package com.seable.potato.common;

import android.content.Context;
import android.os.Looper;

import com.seable.potato.biz.L;
import com.seable.potato.biz.T;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * 全局未捕获异常 进行捕获提示信息并关闭
 *
 * @author yufeng
 */
public class CrashHandler implements UncaughtExceptionHandler {

    private UncaughtExceptionHandler mDefaultHandler;
    private static CrashHandler INSTANCE = new CrashHandler();
    private Context mContext;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    public void init(Context context) {
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    //发生uncaughtException时  会调用该函数进行处理
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                L.e("error", "" + e);
            }
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }


    //开启新线程处理未捕获异常
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                T.s(mContext, "很抱歉，程序出现异常 即将推出！\n 异常信息 请您及时反馈管理员", 4000);
                Looper.loop();
            }
        }.start();

        // TODO 此处可以搜集信息，保存日志，然后上传服务器

        return true;
    }

}

package com.seable.potato.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

/**
 * @author 王维玉
 * @ClassName: ConnectionDetector
 * @Description: 检测网络连接
 * @date 2015-01-19 13:07
 */
public class ConnectionNetWorkUtil implements NetWorkCallBack {


    private static ConnectionNetWorkUtil connectionUtil = null;

    //网络成功状态
    public static final int NETWORK_STATE_SUCCESS = 1;
    //网络失败状态
    public static final int NETWORK_STATE_FAILURE = 2;

    //当前网络状态
    public static boolean NETWORK_STATE_CURRENT = false;

    private Context context;

    private static Handler handler = new Handler() {

        public void handleMessage(Message msg) {

            switch (msg.what) {
                case NETWORK_STATE_FAILURE:
                    Log.i("aaaaaaaaaaaaaaaa", "网络失败");
                    NETWORK_STATE_CURRENT = false;

                    break;
                case NETWORK_STATE_SUCCESS:
                    Log.i("aaaaaaaaaaaaaaaa", "网络成功");
                    NETWORK_STATE_CURRENT = true;
                    break;
                default:
                    break;
            }

        }

        ;

    };


    public ConnectionNetWorkUtil() {
        super();
        // TODO 自动生成的构造函数存根
    }

    public ConnectionNetWorkUtil(Context context) {
        this.context = context;
    }

    /**
     * @return boolean
     * @throws
     * @Title: isConnectionToInternet
     * @Description: ConnectivityManager主要管理和网络连接相关的操作
     * @author 王国平
     */
    public void isConnectionToInternet() {
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获取代表联网状态的NetWorkInfo对象
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            Log.i("通知", "当前连接不可用");
        } else {
            boolean available = networkInfo.isAvailable();
            if (available) {
                Log.i("通知", "当前连接可用");
            } else {
                Log.i("通知", "当前连接不可用");
            }
        }
        State state = connManager.getNetworkInfo(
                ConnectivityManager.TYPE_MOBILE).getState();

        if (State.CONNECTED == state) {
            Log.i("通知", "gprs已连接");
        }
        state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState();
        if (State.CONNECTED == state) {
            Log.i("通知", "wifi已连接");
            Toast.makeText(context, "WIFI网络已连接!", Toast.LENGTH_SHORT).show();
        }

        // return false;
    }

    /**
     * 检查网络是否可用
     *
     * @param pContext 应用程序的上下文
     * @return
     */
    public static boolean isConnectInternet(final Context pContext) {
        if (pContext != null) {
            final ConnectivityManager conManager = (ConnectivityManager) pContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);// 获取系统网络连接管理器
            // 获取代表联网状态的NetWorkInfo对象
            final NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 网络是否可用
     *
     * @param activity
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param context
     * @return boolean
     * @throws
     * @Title: isNetworkConnected
     * @Description: 验证wifi是否连接(这里用一句话描述这个方法的作用)
     * @author 王国平
     */
    public static boolean isNetworkConnected(Context context) {
        Log.i("tag", context + "");
        try {
            if (context != null) {
                ConnectivityManager connManager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mWiFiNetworkInfo = connManager
                        .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                Log.i("tag", mWiFiNetworkInfo + "  :mWiFiNetworkInfo");
                if (mWiFiNetworkInfo != null) {
                    return mWiFiNetworkInfo.isAvailable();
                }
            }
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param context
     * @return boolean
     * @throws
     * @Title: isMobileConnected
     * @Description: 判断Mobile网络是否可用(这里用一句话描述这个方法的作用)
     * @author 王国平
     */
    public static boolean isMobileConnected(Context context) {
        Log.i("tag", context + "");
        try {
            if (context != null) {
                ConnectivityManager connManager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mMobileNetworkInfo = connManager
                        .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                Log.i("tag", mMobileNetworkInfo + "  :mMobileNetworkInfo");
                if (mMobileNetworkInfo != null) {
                    return mMobileNetworkInfo.isAvailable();
                }
            }
        } catch (Exception e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param context
     * @return int
     * @throws
     * @Title: getConnectedType
     * @Description: 检测当前网络连接类型(这里用一句话描述这个方法的作用)
     * @author 王国平
     */
    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager connManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = connManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }


    /**
     * 通知系统更新 (此通知执行在子线程中)
     */
    @Override
    public void accepMsg(boolean networkState) {
        // TODO 自动生成的方法存根
        Message msg = new Message();
        if (networkState == true) {
            msg.what = NETWORK_STATE_SUCCESS;
        } else {
            msg.what = NETWORK_STATE_FAILURE;
        }
        handler.sendMessage(msg);
    }


}

interface NetWorkCallBack {
    public void accepMsg(boolean networkState);
}

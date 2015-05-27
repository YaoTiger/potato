package com.seable.potato.biz;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.lidroid.xutils.DbUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.seable.potato.data.entity.tigerentity.UserInfoEntity;
import com.seable.potato.data.http.HttpRequestManager;
import com.seable.potato.data.sharedata.ShareDataManager;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Chen Lei
 * @version 1.3.1
 */
public class LApplication extends Application {

    /**
     * 全局上下文对象
     */
    private Context mContext;

    /**
     * 将所有FragmentActivity存放在链表中，便于管理
     */
    private List<FragmentActivity> activityList;

    /**
     * 全局FragmentManager实例
     */
    private FragmentManager mFragmentManager;

    /**
     * 全局LApplication唯一实例
     */
    private static LApplication instance;

    /**
     * 全局数据库名称
     */
    private String appDBName;

    /**
     * 全局数据库版本
     */
    private int appDBVersion;

    /**
     * 全局数据库创建表的语句数组
     */
    private String[] appDBCreateTables;

    /**
     * 全局数据库删除表的语句数组
     */
    private String[] appDBDropTables;

    /**
     * 全局的是否打开DEBUG模式<br />
     * 默认为true
     */
    private boolean openDebugMode = true;

    /**
     * 全局APP标识名称
     */
    private String appName;

    /**
     * 全局APP服务器主路径
     */
    private String appServiceUrl;

    /**
     * 全局数据缓存数量<br />
     * 默认为20
     */
    private int cacheCount = 20;

    /**
     * 是否允许销毁所有Activity<br />
     * 默认为false
     */
    private boolean destroyActivitys = false;

    /**
     * 全局缓存文件存储文件对象
     */
    private File cacheFile;

    /**
     * 全局ImageLoader实例
     */
    private ImageLoader imageLoader;

    /**
     * 全局DisplayImageOptions实例
     */
    private DisplayImageOptions mOption;

    /**
     * 全局默认图片ID，默认值为-1
     */
    private int mDefaultImageId = -1;

    /**
     * 全局屏幕宽度
     */
    private int mDiaplayWidth;

    /**
     * 全局屏幕高度
     */
    private int mDiaplayHeight;

    public ShareDataManager mShareDataManager;
    public HttpRequestManager mHttpRequestManager;
    public UserInfoEntity userInfoEntity;
    public DbUtils dbUtils;

    @Override
    public void onCreate() {
        super.onCreate();
        mLocationClient = new LocationClient(this);
        mLocationClient.registerLocationListener(new BDListener());
        mShareDataManager = new ShareDataManager(this);
        mHttpRequestManager = new HttpRequestManager();
        userInfoEntity = new UserInfoEntity();
        dbUtils=DbUtils.create(this);
    }

    public UserInfoEntity getUserInfoEntity() {
        return userInfoEntity;
    }

    /**
     * @return 获取上下文对象
     */
    public Context getContext() {
        if (mContext == null) {
            mContext = this;
        }
        return mContext;
    }

    /**
     * 设置上下文对象
     *
     * @param mContext
     */
    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 添加一个Activity到数组里
     *
     * @param activity
     */
    public void addActivity(FragmentActivity activity) {
        if (this.activityList == null) {
            activityList = new LinkedList<FragmentActivity>();
        }
        this.activityList.add(activity);
    }

    /**
     * 删除一个Activity在数组里
     *
     * @param activity
     */
    public void delActivity(FragmentActivity activity) {
        if (activityList != null) {
            activityList.remove(activity);
        }
    }

    /**
     * 设置一个 FragmentManager 对象
     *
     * @param fm
     */
    public void setFragmentManager(FragmentManager fm) {
        this.mFragmentManager = fm;
    }

    /**
     * @return 获取一个 FragmentManager 对象
     */
    public FragmentManager getFragmentManager() {
        return this.mFragmentManager;
    }

    /**
     * 获取一个LApplication的实例
     *
     * @return
     */
    public static synchronized LApplication getInstance() {
        if (instance == null) {
            instance = new LApplication();
        }
        return instance;
    }

    /**
     * 设置一个LApplication的实例
     *
     * @param app
     */
    public static void setLApplication(LApplication app) {
        instance = app;
    }

    /**
     * 设置DBHelper实例信息
     *
     * @param dbName         ：数据库名称
     * @param dbVersion      ：数据库版本
     * @param dbCreateTables ：数据库创建表语句集合
     * @param dbDropTables   ：数据库删除表语句集合
     */
    public void setDBInfo(String dbName, int dbVersion,
                          String[] dbCreateTables, String[] dbDropTables) {
        this.appDBName = dbName;
        this.appDBVersion = dbVersion;
        this.appDBCreateTables = dbCreateTables;
        this.appDBDropTables = dbDropTables;
    }

    /**
     * 获取是否启用Debug模式
     *
     * @return
     */
    public boolean getIsOpenDebugMode() {
        return openDebugMode;
    }

    /**
     * 设置是否启用Debug模式
     *
     * @param openDebugMode
     */
    public void setOpenDebugMode(boolean openDebugMode) {
        this.openDebugMode = openDebugMode;
    }

    /**
     * 获取应用标识名称
     *
     * @return
     */
    public String getAppName() {
        return appName;
    }

    /**
     * 设置应用标识名称
     *
     * @param appName
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * 获取应用主服务器路径
     *
     * @return
     */
    public String getAppServiceUrl() {
        return appServiceUrl;
    }

    /**
     * 设置应用主服务器路径
     *
     * @param appServiceUrl
     */
    public void setAppServiceUrl(String appServiceUrl) {
        this.appServiceUrl = appServiceUrl;
    }

    /**
     * 获取应用内存缓存数量
     *
     * @return
     */
    public int getCacheCount() {
        return cacheCount;
    }

    /**
     * 设置应用内存缓存数量
     *
     * @param cacheCount
     */
    public void setCacheCount(int cacheCount) {
        this.cacheCount = cacheCount;
    }

    /**
     * 获取应用是否允许销毁所有Activity
     *
     * @return
     */
    public boolean getIsDestroyActivitys() {
        return destroyActivitys;
    }

    /**
     * 设置应用是否允许销毁所有Activity
     *
     * @param destroyActivitys
     */
    public void setDestroyActivitys(boolean destroyActivitys) {
        this.destroyActivitys = destroyActivitys;
    }

    /**
     * 获取应用文件缓存路径文件
     *
     * @return
     */
    public File getCacheFile() {
        if (cacheFile == null) {
            cacheFile = getCacheDir();
        }
        return cacheFile;
    }

    /**
     * 设置应用文件缓存路径文件
     *
     * @param cacheFile
     */
    public void setCacheFile(File cacheFile) {
        this.cacheFile = cacheFile;
    }

    /**
     * 设置ImageLoader对象
     *
     * @param configuration
     */
    public void setImageLoader(ImageLoaderConfiguration configuration) {
        if (configuration != null) {
            ImageLoader.getInstance().init(configuration);
            imageLoader = ImageLoader.getInstance();
        }
    }

    /**
     * 设置DisplayImageOptions对象
     *
     * @param options
     */
    public void setImageOptions(DisplayImageOptions options) {
        this.mOption = options;
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public int getDiaplayWidth() {
        if (mDiaplayWidth <= 0) {
            computeDiaplayWidthAndHeight();
        }
        return mDiaplayWidth;
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    public int getDiaplayHeight() {
        if (mDiaplayHeight <= 0) {
            computeDiaplayWidthAndHeight();
        }
        return mDiaplayHeight;
    }

    @SuppressWarnings("deprecation")
    private void computeDiaplayWidthAndHeight() {
        WindowManager mWindowManager = ((LActivity) getContext())
                .getWindowManager();
        Display display = mWindowManager.getDefaultDisplay();
        mDiaplayWidth = display.getWidth();
        mDiaplayHeight = display.getHeight();
    }

    /**
     * 完全退出应用<br />
     * 需要设置destroyActivitys为true
     */
    public void exitApp() {
        if (destroyActivitys) {
            for (FragmentActivity f : activityList) {
                f.finish();
            }
            System.exit(0);
        }
    }

    /**
     * 关闭所有活着的Activity<br />
     * 需要设置destroyActivitys为true
     */
    public void killActivities() {
        if (destroyActivitys) {
            for (FragmentActivity f : activityList) {
                f.finish();
            }
        }
    }

    private LocationClient mLocationClient;

    public void initLocationOption() {
        LocationClientOption option = new LocationClientOption();

        option.setIsNeedAddress(true);
        option.setLocationMode(LocationMode.Hight_Accuracy);
        option.setCoorType("gcj02"); // 设置返回值的坐标类型。
        option.setScanSpan(1000); // 设置定时定位的时间间隔。单位毫秒
        mLocationClient.setLocOption(option);
    }

    class BDListener implements BDLocationListener {
        public void onReceiveLocation(BDLocation location) {
            Log.i("dddd",location.toString()+"??????");
            if (onLocationChangeListener != null) {
                onLocationChangeListener.onLocationChange(location);
            }
        }
    }

    private OnLocationChangeListener onLocationChangeListener;

    public void setOnLocationChanger(OnLocationChangeListener onLocationChanger) {
        this.onLocationChangeListener = onLocationChanger;
    }

    public void startLocation() {
        mLocationClient.start();
    }

    public void stopLocation() {
        mLocationClient.stop();
    }

    public interface OnLocationChangeListener {
        public void onLocationChange(BDLocation location);
    }
}

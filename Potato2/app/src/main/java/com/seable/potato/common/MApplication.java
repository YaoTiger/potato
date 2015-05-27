package com.seable.potato.common;

import android.content.res.Resources;
import android.os.Environment;
import android.text.TextUtils;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.seable.potato.R;
import com.seable.potato.biz.LApplication;
import com.seable.potato.data.entity.Entity_User;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.io.File;
import java.util.Map;

public class MApplication extends LApplication implements Constants {
    public static String app_version = "1.0.0";


    private HttpClient httpClient;
    //
    // public static void setIMEI(){
    // LApplication.IMEI = LMobileInfo.getImei();
    // }

    /**
     * 实例
     */
    private static MApplication instance;

    /**
     * 全局ImageLoader实例
     */
    @SuppressWarnings("unused")
    private ImageLoader imageLoader;
    /**
     * 本地数据库名称
     */
    private static final String DB_NAME = "MILKSTATION_DB";

    /**
     * 本地数据库版本
     */

    private static final int DB_VERSION = 43;

    /**
     * 本地数据库创建表集合
     */
    private static String[] DB_CREATE_TABLES;

    /**
     * 本地数据库删除表集合
     */
    private static String[] DB_DROP_TABLES;


    @SuppressWarnings("unused")
    private static Map<String, String> ModuleList;

    /**
     * 本地缓存数量
     */
    private static final int CACHE_COUNT = 30;

    /**
     * 当前是否为离线
     */
    private boolean offLine;


    @Override
    public void onCreate() {
        super.onCreate();
        //TODO
//		SDKInitializer.initialize(this);
        // 极光推送
        // JPushInterface.setDebugMode(true); // 设置开启日志,发布时请关闭日志
        // JPushInterface.init(this); // 初始化 JPush

        // //用于全局未捕获异常 进行捕获提示处理
        // CrashHandler crashHandler = CrashHandler.getInstance();
        // crashHandler.init(getApplicationContext());
        //
        Resources resources = getResources();
        // 设置实例
        setLApplication(this);
        // 设置数据库
        // setDBInfo(DB_NAME, DB_VERSION, getDBCreateTables(),
        // getDBDropTables());
        // 打开DEBUG模式
        setOpenDebugMode(true);
        // 设置APP名称
        setAppName(resources.getString(R.string.app_name));
        // 设置服务器路径
        // setAppServiceUrl(resources.getString(R.string.app_server_url));
        // 设置缓存数量
        setCacheCount(CACHE_COUNT);
        httpClient = this.createHttpClient();
        // TODO 设置默认图片
        // setDefaultImage(R.drawable.grey_drawable);

        // LApplication.getInstance().getDBHelper();
        //设置应用是否允许销毁所有Activity
        setDestroyActivitys(true);
        // 创建离线图片保存文件夹--yufeng
        // createImgDir(resources.getString(R.string.app_server_url));
    }

    /**
     * 获取一个Application对象
     *
     * @return
     */
    public static synchronized MApplication get() {
        if (instance == null) {
            instance = (MApplication) getInstance();
        }
        return instance;
    }

    // /**
    // * 获取创建数据库数组
    // *
    // * @return
    // */
    // private String[] getDBCreateTables() {
    // if (DB_CREATE_TABLES == null) {
    // DB_CREATE_TABLES = getResources().getStringArray(R.array.create_tables);
    // }
    // return DB_CREATE_TABLES;
    // }

    // /**
    // * 获取删除数据库数组
    // *
    // * @return
    // */
    // private String[] getDBDropTables() {
    // if (DB_DROP_TABLES == null) {
    // DB_DROP_TABLES = getResources().getStringArray(R.array.drop_tables);
    // }
    // return DB_DROP_TABLES;
    // }

    // public static Entity_User getUser() {
    // return User;
    // }

    private File photoCache;

    public File getPhotoCache() {
        if (photoCache == null) {
            photoCache = new File(Environment.getExternalStorageDirectory()
                    + "/" + getResources().getString(R.string.app_name)
                    + "/cache/");
        }
        return photoCache;
    }

    /**
     * 缓存文件保存的路径
     */
    private String fileUrl;

    /**
     * @return 获取应用缓存主路径
     */
    public String getCacheUrl() {
        if (TextUtils.isEmpty(fileUrl)) {
            fileUrl = getCacheDir().getPath();
        }
        return fileUrl;
    }

    private void createImgDir(String imgDir) {
        File destDir = new File(imgDir);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
    }

    // 创建HttpClient实例
    private HttpClient createHttpClient() {
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params,
                HTTP.DEFAULT_CONTENT_CHARSET);
        HttpProtocolParams.setUseExpectContinue(params, true);
        HttpConnectionParams.setConnectionTimeout(params, 20 * 1000);
        HttpConnectionParams.setSoTimeout(params, 20 * 1000);
        HttpConnectionParams.setSocketBufferSize(params, 8192);
        SchemeRegistry schReg = new SchemeRegistry();
        schReg.register(new Scheme("http", PlainSocketFactory
                .getSocketFactory(), 80));
        schReg.register(new Scheme("https",
                SSLSocketFactory.getSocketFactory(), 443));

        ClientConnectionManager connMgr = new ThreadSafeClientConnManager(
                params, schReg);

        return new DefaultHttpClient(connMgr, params);
    }

    // 关闭连接管理器并释放资源
    private void shutdownHttpClient() {
        if (httpClient != null && httpClient.getConnectionManager() != null) {
            httpClient.getConnectionManager().shutdown();
        }
    }

    // 对外提供HttpClient实例
    public HttpClient getHttpClient() {
        return httpClient;
    }

	/*
     * public ImageLoader getImageLoader2() { if (imageLoader == null) {
	 * ImageLoaderConfiguration configuration = new
	 * ImageLoaderConfiguration.Builder
	 * (getContext()).memoryCacheExtraOptions(480, 800)
	 * .diskCacheExtraOptions(480, 800, null) .threadPoolSize(3)
	 * .threadPriority(Thread.NORM_PRIORITY - 1)
	 * .tasksProcessingOrder(QueueProcessingType.FIFO) //
	 * 调用此方法将不会生成每张图片的各个尺寸的缓存图片
	 * .denyCacheImageMultipleSizesInMemory().memoryCache(new LruMemoryCache(2 *
	 * 1024 * 1024)).memoryCacheSize(2 * 1024 * 1024)
	 * .memoryCacheSizePercentage(13).diskCache(new
	 * UnlimitedDiscCache(getCacheDir())).diskCacheSize(50 * 1024 *
	 * 1024).diskCacheFileCount(500) .diskCacheFileNameGenerator(new
	 * HashCodeFileNameGenerator()).imageDownloader(new
	 * BaseImageDownloader(getContext()))
	 * .defaultDisplayImageOptions(getImageOptions2()) // .writeDebugLogs()
	 * .build(); ImageLoader.getInstance().init(configuration); imageLoader =
	 * ImageLoader.getInstance(); } return imageLoader; }
	 */

    /**
     * 获取DisplayImageOptions对象
     *
     * @return
     */
    /*
     * private DisplayImageOptions mOption;
	 * 
	 * @SuppressWarnings("deprecation") public DisplayImageOptions
	 * getImageOptions2() { if (mOption == null) {
	 * 
	 * mOption = new
	 * DisplayImageOptions.Builder().showStubImage(R.drawable.grey_drawable) //
	 * 设置图片在下载期间显示的图片 .showImageForEmptyUri(R.drawable.grey_drawable) //
	 * 设置图片Uri为空或是错误的时候显示的图片 .showImageOnFail(R.drawable.grey_drawable) //
	 * 设置图片加载/解码过程中错误时候显示的图片 .cacheInMemory(true) // 是否緩存都內存中
	 * .resetViewBeforeLoading
	 * (false).delayBeforeLoading(100).imageScaleType(ImageScaleType
	 * .EXACTLY_STRETCHED).cacheInMemory(true)
	 * .cacheOnDisk(true).bitmapConfig(Bitmap.Config.ARGB_8888).displayer(new
	 * SimpleBitmapDisplayer()).build();
	 * 
	 * } return mOption; }
	 */

    /**
     * 判断当前是否为离线
     *
     * @return
     */
    public boolean offLine() {
        return offLine;
    }

    /**
     * 设置离线状态
     *
     * @param offLine
     */
    public void setOffLine(boolean offLine) {
        this.offLine = offLine;
    }


    // =================================用户信息================================================
//TODO 
    public static int tabNum0 = 0;
    public static int tabNum1 = 0;
    public static int tabNum2 = 0;
    public static int tabNum3 = 0;
    public static int tabNum4 = 0;
    public static int[] tabNum = {tabNum0, tabNum1, tabNum2, tabNum3, tabNum4};
    /**
     * 当前登陆用户
     */
    private static Entity_User User;


    public static Entity_User getUser() {
        return User;
    }

    public static void setUser(Entity_User user) {
        User = user;
    }

    public static boolean logining;


}

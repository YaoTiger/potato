package com.seable.potato.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.seable.potato.R;
import com.seable.potato.biz.LActivity;
import com.seable.potato.common.Constants;
import com.seable.potato.common.MApplication;
import com.seable.potato.ui.fragment.Fragment_My;
import com.seable.potato.ui.fragment.Fragment_TaskCenter;
import com.seable.potato.ui.fragment.Fragment_TaskCount;
import com.seable.potato.ui.view.View_TabPager;
import com.seable.potato.util.PreferenceUtil;
import com.seable.potato.util.TimeUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author 王维玉
 * @ClassName: MainActivity
 * @Description: 首页
 * @date 2015-03-25 17:27
 */
public class Activity_Main extends LActivity {

    // private View_BottomBar bottomBar;
    /**
     * 超过此天检查更新一次interval
     */
    public static final int CHECK_INTERVAL = 0;

    // private static int currentTab;
    /**
     * 业务逻辑
     */
    private static Activity_Main mInstantce;

    public static Activity_Main getCurrentInstance() {
        return mInstantce;
    }

    private FragmentTabHost mTabHost = null;
    ;
    private View indicator = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInstantce = Activity_Main.this;

        findViewById();
        initView();

    }

    private View getIndicatorView(int layoutId) {
        View v = getLayoutInflater().inflate(layoutId, null);
        return v;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTabHost = null;
    }

    private void findViewById() {
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realcontent);

        // 添加tab名称和图标
        indicator = getIndicatorView(R.layout.item_main_tab_01_task_center);
        mTabHost.addTab(mTabHost.newTabSpec(Constants.MAIN_TAB[0])
                .setIndicator(indicator), Fragment_TaskCenter.class, null);

        indicator = getIndicatorView(R.layout.item_main_tab_03_task_count);
        mTabHost.addTab(
                mTabHost.newTabSpec(Constants.MAIN_TAB[2]).setIndicator(indicator),
                Fragment_TaskCount.class, null);
        indicator = getIndicatorView(R.layout.item_main_tab_04_my);
        mTabHost.addTab(
                mTabHost.newTabSpec(Constants.MAIN_TAB[3]).setIndicator(indicator),
                Fragment_My.class, null);
        mTabHost.getTabWidget().setDividerDrawable(R.color.green);
//		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
//		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
//
//		mTabHost.addTab(
//				mTabHost.newTabSpec(Constants.MAIN_TAB[0]).setIndicator(
//						View.inflate(mContext,
//								R.layout.item_main_tab_01_task_center, null)),
//				Fragment_TaskCenter.class, null);
////		mTabHost.addTab(
////				mTabHost.newTabSpec(Constants.MAIN_TAB[1]).setIndicator(
////						View.inflate(mContext, R.layout.item_main_tab_02_area,
////								null)), Fragment_Area.class, null);
//		mTabHost.addTab(
//				mTabHost.newTabSpec(Constants.MAIN_TAB[2]).setIndicator(
//						View.inflate(mContext,
//								R.layout.item_main_tab_03_task_count, null)),
//				Fragment_TaskCount.class, null);
//		mTabHost.addTab(
//				mTabHost.newTabSpec(Constants.MAIN_TAB[3]).setIndicator(
//						View.inflate(mContext, R.layout.item_main_tab_04_my,
//								null)), Fragment_My.class, null);

        setCurrentTab(0, -1);

    }

    private void initView() {
        // getIntentData();
        if (getFirstIn()) {
            // 第一次登陆
            checkData();
            saveFirstIn(false);
        } else {
            // 不是第一次登陆
            // 判断时间戳，距离上次检查超过一周则进行检查更新
            long now = Long.parseLong(TimeUtil.getNowTimeCode());
            String strLastTime = getCheckTime();
            if (strLastTime != null && (!"".equals(strLastTime))) {
                long lastTime = Long.parseLong(strLastTime);
                if ((now - lastTime) > (CHECK_INTERVAL * TimeUtil.TIMECODE_DAY)) {
                    checkData();
                }
            } else {
                checkData();
            }
        }

        // setDensity((float) 1.538461538461);
        // setDensity((float) 1.538461538461);
        // int[] density =
        // {6,8,10,12,14,15,16,17,18,19,20,22,23,24,25,26,28,30,33,34,35,36,37,38,
        // 44,45,46,48,50,52,55,57,60,64,67,70,74,75,76,78,80,85,90,95,96,98,
        // 100,104,105,106,110,114,115,120,125,134,136,137,148,150,152,
        // 164,170,175,192,196,211,213,220,232,238,251,277,290,355,377,382,397,415,432,442,498,740};
        // for(int n:density){
        // L.e(DensityUtil.px2dip(mContext, n)+"<<<<<<phone<<<<<<<"+n);
        // // L.e(px2dip(mContext, n)+">>>>>>>>number"+n);
        // }

    }

    // private void getIntentData() {
    // try {
    //
    // Bundle bundle = getIntent().getBundleExtra(
    // Constants.JUMP_PARAMETER_KEY_BUNDLE);
    // currentTab = bundle
    // .getInt(Constants.JUMP_PARAMETER_KEY_CURRENT_TAB);
    // } catch (Exception e) {
    // currentTab = -1;
    // }finally{
    // if(currentTab<0||currentTab>Constants.PARAMETER_VALUE_TASK4_SUCCESS){
    // currentTab = 0;
    // }
    // }
    // }

    @SuppressWarnings("unchecked")
    public void setCurrentTab(int mainTab, int taskTab) {
        mTabHost.setCurrentTab(mainTab);
        if (taskTab >= 0) {
            View_TabPager tabPager = View_TabPager.getCurrentInstance();
            tabPager.setCurrentPage(Fragment_TaskCenter.classesArr[taskTab]);
        }
        getIntent().putExtra(Constants.JUMP_PARAMETER_KEY_CURRENT_TAB, taskTab);
    }

    /**
     * 检查更新完成后保存更新时间戳
     */
    private void checkData() {
        // 版本检查更新
        // TODO
        // bizMy.getUpdate();

    }

    @SuppressWarnings("unchecked")
    @Override
    public void onActivityResult(int request, int result, Intent data) {
        if (result == -1) {
            if (data != null) {
                int currentPageNum;
                View_TabPager tabPager = View_TabPager.getCurrentInstance();
                int taskType = data.getIntExtra(
                        Constants.PARAMETER_KEY_TASK_TYPE,
                        Constants.PARAMETER_VALUE_TASK0_CONFIRM);
                switch (taskType) {
                    case Constants.PARAMETER_VALUE_TASK0_CONFIRM:
                        currentPageNum = Constants.PARAMETER_VALUE_TASK1_HANDLE;
                        tabPager.setCurrentPage(Fragment_TaskCenter.classesArr[currentPageNum]);
                        break;
                    case Constants.PARAMETER_VALUE_TASK1_HANDLE:
                        currentPageNum = Constants.PARAMETER_VALUE_TASK2_APPROVAL;
                        tabPager.setCurrentPage(Fragment_TaskCenter.classesArr[currentPageNum]);
                        break;
                    case Constants.PARAMETER_VALUE_TASK2_APPROVAL:
                        break;
                    case Constants.PARAMETER_VALUE_TASK3_FAILED:
                        currentPageNum = Constants.PARAMETER_VALUE_TASK1_HANDLE;
                        tabPager.setCurrentPage(Fragment_TaskCenter.classesArr[currentPageNum]);
                        break;
                    case Constants.PARAMETER_VALUE_TASK4_SUCCESS:
                        // currentPageNum = Constants.PARAMETER_VALUE_TASK4_SUCCESS;
                        // tabPager.setCurrentPage(Fragment_TaskCenter.classesArr[currentPageNum]);
                        break;

                    default:
                        break;
                }

            }
        }

    }

    ;

    static float density = 1.538461538461f;
    static float defaultDensity = 1.538461538461f;

    public static int px2dip(Context mContext, float pxValue) {
        int dp;
        if (density == 0) {
            dp = (int) (pxValue / defaultDensity + 0.5f);
        } else {
            dp = (int) (pxValue / density + 0.5f);
        }
        return dp;
    }

    // TODO=================双击退出========================

    private boolean isExit = false;

    /**
     * 双击退出
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();
        }
        return false;
    }

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            MApplication.get().exitApp();
            // finish();
            // System.exit(0);
        }
    }

    // TODO==================第一次登陆上次检查更新的时间戳设置========================
    private static final String SHAREDPREFERENCES_NAME = "first_pref";
    private static final String SHAREDPREFERENCES_KEY_TIME = "checkTimeCode";
    private static final String SHAREDPREFERENCES_KEY_FIRST = "isFirstIn";

    /**
     * 保存是否第一次登陆
     */
    private void saveFirstIn(boolean flagFirstIn) {
        PreferenceUtil.getInstance(mContext, SHAREDPREFERENCES_NAME)
                .saveBoolean(SHAREDPREFERENCES_KEY_FIRST, flagFirstIn);
    }

    /**
     * 获取是否第一次登陆
     */
    private boolean getFirstIn() {
        boolean info = PreferenceUtil.getInstance(mContext,
                SHAREDPREFERENCES_NAME).getBoolean(SHAREDPREFERENCES_KEY_FIRST,
                true);
        return info;
    }

    /**
     * 保存上次检查更新的时间戳
     */
    private void saveCheckTime(String time) {
        PreferenceUtil.getInstance(mContext, SHAREDPREFERENCES_NAME)
                .saveString(SHAREDPREFERENCES_KEY_TIME, time);
    }

    /**
     * 获取上次检查更新的时间戳
     */
    private String getCheckTime() {
        String info = PreferenceUtil.getInstance(mContext,
                SHAREDPREFERENCES_NAME).getString(SHAREDPREFERENCES_KEY_TIME,
                "");
        return info;
    }


}

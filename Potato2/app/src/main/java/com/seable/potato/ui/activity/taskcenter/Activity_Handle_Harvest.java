package com.seable.potato.ui.activity.taskcenter;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.seable.potato.R;
import com.seable.potato.biz.LActivity;
import com.seable.potato.ui.fragment.taskcenter.handle.Fragment_TaskCenter_Harvest;
import com.seable.potato.ui.fragment.taskcenter.handle.Fragment_TaskCenter_TestProduction;
import com.seable.potato.ui.view.View_TabPager;
import com.seable.potato.ui.view.View_TitleBar;

/**
 * @author 王维玉
 * @ClassName: Activity_Handle_Harvest
 * @Description: 测产、收获后处理数据
 * @date 2015-04-02 13:45
 */
public class Activity_Handle_Harvest extends LActivity {

    private static View_TitleBar mTitleBar;
    private static View_TabPager mTabPager;
    private static int currentPageNum;
    private static int lastPageNum, nowPageNum;
    private static ViewPager mViewPager;
    public static int[] titles = new int[]{
            R.string.task_center_detail_type3_harvest,
            R.string.task_center_detail_type3_test_production};
    @SuppressWarnings("rawtypes")
    public static Class[] classesArr = new Class[]{
            Fragment_TaskCenter_Harvest.class,
            Fragment_TaskCenter_TestProduction.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_handle_harvest);
        findViewById();
    }

//	@Override
//	public void onResume() {
//		super.onResume();
//		checkCurrentTab();
//	}
//	@SuppressWarnings("unchecked")
//	private void checkCurrentTab() {
//		int tabNum = getIntent().getIntExtra(
//				Constants.JUMP_PARAMETER_KEY_CURRENT_TAB, -1);
//		if (tabNum >= 0) {
//			currentPageNum = tabNum;
//			mViewPager.setCurrentItem(currentPageNum);
//			mTabPager.setCurrentPage(classesArr[currentPageNum]);
//			getIntent().putExtra(
//					Constants.JUMP_PARAMETER_KEY_CURRENT_TAB, -1);
//		}
//	}

    @SuppressWarnings("unchecked")
    private void findViewById() {
        // 初始化view
        mTitleBar = (View_TitleBar) findViewById(R.id.TitleBar);
        mTitleBar.setBackImshow(false);
        nowPageNum = 0;
        lastPageNum = nowPageNum;
        try {
            mTabPager = (View_TabPager) findViewById(R.id.tab_pager);
            View_TabPager.init(this);
            mTabPager.addPage(classesArr[0], titles[0], -1,
                    R.drawable.line_bg_tab);
            mTabPager.addPage(classesArr[1], titles[1], -1,
                    R.drawable.line_bg_tab);
            mViewPager = (ViewPager) findViewById(R.id.pager);
            mViewPager.setOffscreenPageLimit(classesArr.length);
            mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

                @Override
                public void onPageSelected(int arg0) {
                    currentPageNum = arg0 % classesArr.length;
                    lastPageNum = arg0;
                    System.out.println("lastPageNum" + lastPageNum
                            + ">>>>>>>>>>nowPageNum" + nowPageNum
                            + ">>>>>>>>>>>>>arg0" + arg0);
                    mTabPager.setCurrentPage(classesArr[currentPageNum]);
                    mViewPager.setCurrentItem(currentPageNum);
                }

                @Override
                public void onPageScrolled(int arg0, float arg1, int arg2) {

                }

                @Override
                public void onPageScrollStateChanged(int arg0) {

                }
            });
            currentPageNum = 0;
            mViewPager.setCurrentItem(currentPageNum);
            mTabPager.setCurrentPage(classesArr[currentPageNum]);

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

    }

    public void testVolley(){

    }

}
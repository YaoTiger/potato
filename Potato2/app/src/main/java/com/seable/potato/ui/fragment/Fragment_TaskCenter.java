package com.seable.potato.ui.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seable.potato.R;
import com.seable.potato.biz.LFragment;
import com.seable.potato.common.Constants;
import com.seable.potato.ui.fragment.taskcenter.Fragment_TaskCenter_01Confirm;
import com.seable.potato.ui.fragment.taskcenter.Fragment_TaskCenter_02Handle;
import com.seable.potato.ui.fragment.taskcenter.Fragment_TaskCenter_03Approval;
import com.seable.potato.ui.fragment.taskcenter.Fragment_TaskCenter_04Failed;
import com.seable.potato.ui.fragment.taskcenter.Fragment_TaskCenter_05Success;
import com.seable.potato.ui.view.View_TabPager;
import com.seable.potato.ui.view.View_TitleBar;

/**
 * @author 王维玉
 * @ClassName: Fragment_TaskCenter
 * @Description: 任务中心
 * @date 2015-01-22 15:14
 */
public class Fragment_TaskCenter extends LFragment {
    private static View view;
    private static View_TitleBar mTitleBar;
    private static View_TabPager mTabPager;
    private static int currentPageNum;
    private static int lastPageNum, nowPageNum;
    private static ViewPager mViewPager;
    public static int[] titles = new int[]{R.string.task_center_01_confirm,
            R.string.task_center_02_handle, R.string.task_center_03_approval,
            R.string.task_center_04_failed, R.string.task_center_05_success};
    @SuppressWarnings("rawtypes")
    public static Class[] classesArr = new Class[]{
            Fragment_TaskCenter_01Confirm.class,
            Fragment_TaskCenter_02Handle.class,
            Fragment_TaskCenter_03Approval.class,
            Fragment_TaskCenter_04Failed.class,
            Fragment_TaskCenter_05Success.class};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_task_center_tabhost, null);
        // mInstantce = Fragment_TaskCenter.this;
        findViewById();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        checkCurrentTab();
    }

    @SuppressWarnings("unchecked")
    private void checkCurrentTab() {
        if (getActivity() != null) {
            int tabNum = getActivity().getIntent().getIntExtra(Constants.JUMP_PARAMETER_KEY_CURRENT_TAB, -1);
            if (tabNum >= 0) {
                currentPageNum = tabNum;
                mViewPager.setCurrentItem(currentPageNum);
                mTabPager.setCurrentPage(classesArr[currentPageNum]);
                getActivity().getIntent().putExtra(Constants.JUMP_PARAMETER_KEY_CURRENT_TAB, -1);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void findViewById() {
        // 初始化view
        mTitleBar = (View_TitleBar) view.findViewById(R.id.TitleBar);
        mTitleBar.setBackImshow(false);
        nowPageNum = 0;
        lastPageNum = nowPageNum;
        try {
            mTabPager = (View_TabPager) view.findViewById(R.id.tab_pager);
            View_TabPager.init(this);
            mTabPager.addPage(classesArr[0], titles[0], -1,
                    R.drawable.line_bg_tab);
            mTabPager.addPage(classesArr[1], titles[1], -1,
                    R.drawable.line_bg_tab);
            mTabPager.addPage(classesArr[2], titles[2], -1,
                    R.drawable.line_bg_tab);
            mTabPager.addPage(classesArr[3], titles[3], -1,
                    R.drawable.line_bg_tab);
            mTabPager.addPage(classesArr[4], titles[4], -1,
                    R.drawable.line_bg_tab);
            mViewPager = (ViewPager) view.findViewById(R.id.pager);
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
}

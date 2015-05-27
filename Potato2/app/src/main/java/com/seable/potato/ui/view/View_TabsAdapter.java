package com.seable.potato.ui.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.seable.potato.biz.LActivity;
import com.seable.potato.biz.LFragment;

import java.util.ArrayList;


public class View_TabsAdapter extends FragmentPagerAdapter implements
        TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener, View.OnClickListener {

    private final Context mContext;
    private final TabHost mTabHost;
    private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
    private final ViewPager mViewPager;

    public View_TabsAdapter(LActivity activity, TabHost tabHost,
                            ViewPager viewPager) {
        super(activity.getSupportFragmentManager());
        this.mContext = activity;
        this.mTabHost = tabHost;
        this.mViewPager = viewPager;
        this.mViewPager.setAdapter(this);
        this.mViewPager.setOnPageChangeListener(this);
        this.mTabHost.setOnTabChangedListener(this);
    }

    public View_TabsAdapter(LFragment fragment, TabHost tabHost,
                            ViewPager viewPager) {
        super(fragment.getChildFragmentManager());
        this.mContext = fragment.getActivity();
        this.mTabHost = tabHost;
        this.mViewPager = viewPager;
        this.mViewPager.setAdapter(this);
        this.mViewPager.setOnPageChangeListener(this);
        this.mTabHost.setOnTabChangedListener(this);
    }

    public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
        tabSpec.setContent(new DummyTabFactory(mContext));
        TabInfo tabInfo = new TabInfo(tabSpec.getTag(), clss, args);
        this.mTabs.add(tabInfo);
        this.mTabHost.addTab(tabSpec);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        TabInfo tabInfo = mTabs.get(position);
        return Fragment.instantiate(this.mContext, tabInfo.clss.getName(), tabInfo.args);
    }

    @Override
    public int getCount() {
        return this.mTabs.size();
    }

    @Override
    public void onPageScrollStateChanged(int position) {

    }

    @Override
    public void onPageScrolled(int from, float offset, int to) {

    }

    @Override
    public void onPageSelected(int position) {
        TabWidget tabWidget = mTabHost.getTabWidget();
        int focusable = tabWidget.getDescendantFocusability();
        tabWidget.setDescendantFocusability(TabWidget.FOCUS_BLOCK_DESCENDANTS);
        this.mTabHost.setCurrentTab(position);
        tabWidget.setDescendantFocusability(focusable);
    }

    public void OnClickListener(int position) {
        TabWidget tabWidget = mTabHost.getTabWidget();
        int focusable = tabWidget.getDescendantFocusability();
        tabWidget.setDescendantFocusability(TabWidget.FOCUS_BLOCK_DESCENDANTS);
        this.mTabHost.setCurrentTab(position);
        tabWidget.setDescendantFocusability(focusable);
        tabWidget.getChildAt(position).setOnClickListener(this);
    }

    @Override
    public void onTabChanged(String tabId) {
        int index = mTabHost.getCurrentTab();
        mViewPager.setCurrentItem(index);
    }

    static class DummyTabFactory implements TabHost.TabContentFactory {
        private final Context mContext;

        public DummyTabFactory(Context paramContext) {
            this.mContext = paramContext;
        }

        public View createTabContent(String paramString) {
            View view = new View(this.mContext);
            view.setMinimumWidth(0);
            view.setMinimumHeight(0);
            return view;
        }
    }

    static final class TabInfo {

        private final Bundle args;
        private final Class<?> clss;
        private final String tag;

        TabInfo(String tag, Class<?> clss, Bundle args) {
            this.tag = tag;
            this.clss = clss;
            this.args = args;
        }

        public String getTag() {
            return tag;
        }
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub

    }

}

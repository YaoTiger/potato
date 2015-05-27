package com.seable.potato.ui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.seable.potato.R;
import com.seable.potato.biz.L;
import com.seable.potato.biz.LActivity;
import com.seable.potato.biz.LFragment;


public class View_TabPager extends FrameLayout {
    private static Context mContext;

    private static ImageView mImageView;

    private static TextView mNumView;

    private static TabHost mTabHost;

    private static TabWidget mTabWidget;

    private static TextView mTextView;

    private static ViewPager mViewPager;

    private static TabAdapter mTabsAdapter;

    //    private static View mInvitedView;
    private static View_TabPager currentInstance;
//
//    public View_TabPager(Context context)
//    {
//        super(context);
//        mContext = context;
//        init();
//    }
//
//    public View_TabPager(Context context, AttributeSet attrs)
//    {
//        super(context, attrs);
//        mContext = context;
//        init();
//    }
//
//    public View_TabPager(Context context, AttributeSet attrs, int defStyle)
//    {
//        super(context, attrs, defStyle);
//        mContext = context;
//        init();
//    }

    public static View_TabPager getCurrentInstance() {
        return currentInstance;
    }

//    private void init()
//    {
//        LayoutInflater.from(mContext).inflate(R.layout.sliding_tab_pager, this, true);
//
//        mViewPager = (ViewPager)findViewById(R.id.pager);
//        mTabHost = (TabHost)findViewById(android.R.id.tabhost);
//        mTabWidget = (TabWidget)findViewById(android.R.id.tabs);
//        mTabHost.setup();
//        mTabsAdapter = new TabAdapter((LActivity)mContext, mTabHost, mViewPager);
//        currentInstance = this;
//    }

    public <T extends LFragment> void addPage(Class<T> clss, int imageId/*, View view*/) {
        addPage(clss, -1, -1, imageId/*, view*/);
    }

    public <T extends LFragment> void addPage(Class<T> clss, int nameId, int numBgId,
                                              int imageId/*, View InvitedView*/) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.sliding_tab_pager_item, null);
        mTextView = (TextView) view.findViewById(R.id.tab_text);
        mNumView = (TextView) view.findViewById(R.id.tab_new_info_num);
        mImageView = (ImageView) view.findViewById(R.id.tab_image);
//        mInvitedView = InvitedView;
        currentInstance = this;
        if (nameId > 0) {
            mTextView.setText(mContext.getString(nameId));
        } else {
            mTextView.setVisibility(View.GONE);
        }

        if (numBgId <= 0) {
            mNumView.setVisibility(View.GONE);
        } else {
            mNumView.setBackgroundDrawable(mContext.getResources().getDrawable(numBgId));
        }

        if (imageId <= 0) {
            mImageView.setVisibility(View.GONE);
        } else {
            mImageView.setImageDrawable(mContext.getResources().getDrawable(imageId));
        }

        mTabsAdapter.addTab(mTabHost.newTabSpec(clss.getSimpleName()).setIndicator(view),
                clss, null);
    }

    public void hideImageAt(int position) {
        View view = mTabHost.getTabWidget().getChildTabViewAt(position)
                .findViewById(R.id.tab_image);
        if (view != null) {
            view.setVisibility(View.GONE);
        }
    }

    public void hideNumAt(int position) {
        View view = mTabHost.getTabWidget().getChildTabViewAt(position)
                .findViewById(R.id.tab_new_info_num);
        if (view != null) {
            view.setVisibility(View.GONE);
        }
    }

    public void hideTabWidget() {
        mTabWidget.setVisibility(View.GONE);
    }

    public void hideTextAt(int position) {
        View view = mTabHost.getTabWidget().getChildTabViewAt(position).findViewById(R.id.tab_text);
        if (view != null) {
            view.setVisibility(View.GONE);
        }
    }

    public void OnTabChangeListener(TabHost.OnTabChangeListener onTabChangeListener) {
        mTabsAdapter.setOnTabChangeListener(onTabChangeListener);
    }

    public <T extends LFragment> void setCurrentPage(Class<T> clss) {
        mTabHost.setCurrentTabByTag(clss.getSimpleName());
    }

    public void setTextAt(int position, int resId) {
        View view = mTabHost.getTabWidget().getChildTabViewAt(position).findViewById(R.id.tab_text);
        if (view != null && view instanceof TextView) {
            ((TextView) view).setText(resId);
        }
    }

    public void setImageAt(int position, int resId) {
        View view = mTabHost.getTabWidget().getChildTabViewAt(position)
                .findViewById(R.id.tab_image);
        if (view != null && view instanceof ImageView) {
            ((ImageView) view).setImageResource(resId);
        }
    }

    public void setNumAt(int position, int num) {
        TextView numText = (TextView) mTabHost.getTabWidget().getChildTabViewAt(position)
                .findViewById(R.id.tab_new_info_num);
        if (numText != null) {
            if (num < 99)
                numText.setText(String.valueOf(num));
            else
                numText.setText("N");
        }
    }

    public void setTextColorAt(int position, int resId) {
        View view = mTabHost.getTabWidget().getChildTabViewAt(position).findViewById(R.id.tab_text);
        if (view != null && view instanceof TextView) {
            ((TextView) view).setTextColor(getResources().getColorStateList(resId));
        }
    }

    public void showCurrentImageOrText(int id) {
        View view = mTabHost.getCurrentView().findViewById(id);
        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public void showImageAt(int position) {
        View view = mTabHost.getTabWidget().getChildTabViewAt(position)
                .findViewById(R.id.tab_image);
        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public void showNumAt(int position) {
        View view = mTabHost.getTabWidget().getChildTabViewAt(position)
                .findViewById(R.id.tab_new_info_num);
        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public void showTabWidget() {
        mTabWidget.setVisibility(View.VISIBLE);
    }

    public void showTextAt(int position) {
        L.e("showTextAt() called");
        View view = mTabHost.getTabWidget().getChildTabViewAt(position).findViewById(R.id.tab_text);
        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
    }

//    private static void setInvitedBar(Boolean isShow)
//    {
////        if (isShow)
////        {
////            mInvitedView.setVisibility(View.VISIBLE);
////        } else
////        {
//            mInvitedView.setVisibility(View.GONE);
//
////        }
//
//    }
//
//	private static Context mContext;
//	private ImageView mImageView;
//	private List<TextView> mNumView=new ArrayList<TextView>();
//	private List<Integer> mNums=new ArrayList<Integer>();
//	private static TabHost mTabHost;
//	private static TabWidget mTabWidget;
//	private TextView mTextView;
//	private static ViewPager mViewPager;
//	private static TabAdapter mTabsAdapter;
//	private static View_TabPager currentInstance;
//	

    public View_TabPager(Context context) {
        super(context);
        mContext = context;
        currentInstance = this;
    }

    public View_TabPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        currentInstance = this;
    }

    public View_TabPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        currentInstance = this;
    }


    public static void init(LActivity activity) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.sliding_tab_pager, currentInstance, true);

        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mTabHost = (TabHost) view.findViewById(android.R.id.tabhost);
        mTabWidget = (TabWidget) view.findViewById(android.R.id.tabs);
        mTabHost.setup();
        mTabsAdapter = new TabAdapter(activity, mTabHost, mViewPager);
//		currentInstance=this;
    }

    public static void init(LFragment fragment) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.sliding_tab_pager, currentInstance, true);

        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mTabHost = (TabHost) view.findViewById(android.R.id.tabhost);
        mTabWidget = (TabWidget) view.findViewById(android.R.id.tabs);
        mTabHost.setup();
        mTabsAdapter = new TabAdapter(fragment, mTabHost, mViewPager);
//		currentInstance=this;
    }

//	public <T extends Fragment> void addPage(Class<T> clss, int imageId,Bundle bundle) {
//		addPage(clss, -1, -1, imageId,bundle);
//	}
//	
//	public <T extends Fragment> void addPage(Class<T> clss, int nameId, int numBgId, int imageId) {
//		addPage(clss, nameId, numBgId, imageId, null);
//	}
//	public <T extends Fragment> void addPage(Class<T> clss, int nameId, int numBgId, int imageId,Bundle bundle) {
//		View view = LayoutInflater.from(getContext()).inflate(R.layout.sliding_tab_pager_item, null);
//		mTextView = (TextView) view.findViewById(R.id.tab_text);
////		TextView unreadTV=(TextView) view.findViewById(R.id.tab_new_info_num);
////		int curNum=0;
////		if(mNumView.size()>0)
////			curNum=mNums.get(mNumView.size()-1);
////		else
////			mNums.add(0);
//		mNums.add(0);
////		unreadTV.setVisibility(View.GONE);
////		mNumView.add(unreadTV);
//		mImageView = (ImageView) view.findViewById(R.id.tab_image);
//		//设置是否显示文字
//		if(nameId > 0) {
//			mTextView.setText(mContext.getString(nameId));
//			mTextView.setVisibility(View.VISIBLE);
//		} else {
//			mTextView.setVisibility(View.GONE);
//		}
////		if(numBgId <= 0) {
////			mNumView.setVisibility(View.GONE);
////		} else {
////			mNumView.setBackgroundDrawable(mContext.getResources().getDrawable(numBgId));
////		}
//		
//		if(imageId <= 0) {
//			mImageView.setVisibility(View.GONE);
//		} else {
//			mImageView.setImageDrawable(mContext.getResources().getDrawable(imageId));
//		}
//		
//		mTabsAdapter.addTab(mTabHost.newTabSpec(clss.getSimpleName()).setIndicator(view), clss, bundle);
//	}
//	
//	public void OnClickListener(int position,android.view.View.OnClickListener  onClickListener) {
//		mTabWidget.getChildAt(position).setOnClickListener(onClickListener);
//	}
//	public void setUnread(int index,int unread){
//		mNums.set(index, unread);
//		TextView tv=mNumView.get(index);
//		if(tv!=null)
//			tv.setVisibility(unread>0?View.VISIBLE:View.GONE);
//	}
//
//	public List<TextView> getmNumView() {
//		return mNumView;
//	}


    public static class TabAdapter extends View_TabsAdapter {

        public TabHost.OnTabChangeListener mOnTabChangeListener;

        public TabAdapter(LActivity activity, TabHost tabHost,
                          ViewPager viewPager) {
            super(activity, tabHost, viewPager);
        }

        public TabAdapter(LFragment fragment, TabHost tabHost,
                          ViewPager viewPager) {
            super(fragment, tabHost, viewPager);
        }

        @Override
        public void onTabChanged(String tabId) {
            super.onTabChanged(tabId);
            if (mOnTabChangeListener != null) {
                mOnTabChangeListener.onTabChanged(tabId);
            }
        }

        public void setOnTabChangeListener(TabHost.OnTabChangeListener onTabChangedListener) {
            mOnTabChangeListener = onTabChangedListener;
        }
    }


}

package com.seable.potato.biz;

import android.os.Handler;


/**
 * @author Chen Lei
 * @version 1.3.9
 */
public abstract class LHandler extends Handler {

    /**
     * 默认进动画
     */
    private static final int DEFAULT_ENTER_ANIM = -1;

    /**
     * 默认出动画
     */
    private static final int DEFAULT_EXIT_ANIM = -1;

    /**
     * 默认返回进动画
     */
    private static final int DEFAULT_BACK_ENTER_ANIM = -1;

    /**
     * 默认返回出动画
     */
    private static final int DEFAULT_BACK_EXIT_ANIM = -1;

    /**
     * 当前Activity对象
     */
    private LActivity mActivity;

    /**
     * 当前Fragment对象
     */
    private LFragment mFragment;


    /**
     * 返回结果回调接口，用于通知所对应的LActivity、LFragment、或LBaseAdapter
     */
    private ILHandlerCallback mILHandlerCallback;

    /**
     * 构造函数
     *
     * @param activity
     */
    public LHandler(LActivity activity) {
        this.mActivity = activity;
        try {
            this.mILHandlerCallback = (ILHandlerCallback) activity;
        } catch (ClassCastException e) {
            this.mILHandlerCallback = null;
        }
    }

    /**
     * 构造函数
     *
     * @param fragment
     */
    public LHandler(LFragment fragment) {
        this.mFragment = fragment;
        try {
            this.mILHandlerCallback = (ILHandlerCallback) mFragment;
        } catch (ClassCastException e) {
            this.mILHandlerCallback = null;
        }
    }

    /**
     * 设置返回结果回调接口，用于通知所对应的LActivity、LFragment、或LBaseAdapter
     *
     * @param calback
     */
    public void setILHandlerCallback(ILHandlerCallback calback) {
        this.mILHandlerCallback = calback;
    }

    /**
     * 获取返回结果回调接口
     *
     * @return
     */
    public ILHandlerCallback getCallback() {
        return mILHandlerCallback;
    }


    /**
     * 注销当前Activity
     */
    public void finishActivity() {
        if (mActivity != null) {
            mActivity.finish();
            mActivity.overridePendingTransition(DEFAULT_BACK_ENTER_ANIM,
                    DEFAULT_BACK_EXIT_ANIM);
        }
    }


}

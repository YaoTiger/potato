package com.seable.potato.biz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.seable.potato.R;
import com.seable.potato.biz.CustomProgressDialog.OnKeyBackListener;
import com.seable.potato.data.entity.tigerentity.UserInfoEntity;
import com.seable.potato.util.LFormat;


public abstract class LFragment extends Fragment implements ILHandlerCallback,
        OnKeyBackListener {
    /**
     * 全局的上下文对象
     */
    public Context mContext;
    /**
     * 默认进动画
     */
    private static final int DEFAULT_ENTER_ANIM = R.anim.activity_input;

    /**
     * 默认出动画
     */
    private static final int DEFAULT_EXIT_ANIM = R.anim.activity_out;

    /**
     * 默认返回进动画
     */
    private static final int DEFAULT_BACK_ENTER_ANIM = R.anim.activity_back_input;

    /**
     * 默认返回出动画
     */
    private static final int DEFAULT_BACK_EXIT_ANIM = R.anim.activity_back_out;

    protected LActivity mActivity;

    protected View mView;
    public boolean noMoreData;
    public boolean isRefreshing;
    public UserInfoEntity entity;
    public LApplication application;

    /**
     * 进度提示框
     */
//	private LProgress mProgressDialog;
    private static CustomProgressDialog mProgressDialog;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (LActivity) activity;
        this.mContext = getActivity();
        entity = ((LActivity) activity).mLApplication.userInfoEntity;
        this.application = (LApplication) mActivity.getApplication();
    }

    @Override
    public void onPause() {
        super.onPause();
        dismissProgressDialog();
        mProgressDialog = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 显示一个ProgressDialog，直接调用即可<br/>
     * 此ProgressDialog会在onPause方法内自动销毁
     *
     * @param text 提示文字
     */
    public void showProgressDialog(String text, boolean isCancelable, boolean isFinish) {
//		if (mProgressDialog == null) {
//			mProgressDialog = new LProgress(this.getActivity());
//			mProgressDialog.setOnKeyBackListener(this);
//		}
//		mProgressDialog.show(text);
        if (mProgressDialog == null) {
//			mProgressDialog = new LProgress(this);
            mProgressDialog = CustomProgressDialog.createDialog(getActivity(), isCancelable);
            mProgressDialog.setOnKeyBackListener(isFinish, this);
        }
        mProgressDialog.show(text);
    }

    /**
     * 销毁一个ProgressDialog
     */
    public void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onKeyBackListener(boolean isFinish) {
        if (isFinish) {
            getActivity().finish();
        }
        getActivity().overridePendingTransition(DEFAULT_BACK_ENTER_ANIM,
                DEFAULT_BACK_EXIT_ANIM);
    }


    @Override
    public void onResultHandler(LMessage msg, int requestId) {
        // ... 写入你需要的代码
    }

    /**
     * 跳转Activity1
     */
    public void defaultJumpActivity(Class<?> cls) {
        Intent intent = null;
        if (cls != null) {
            intent = new Intent(mActivity, cls);
        }
        if (cls != null && intent != null) {
            this.startActivity(intent);
        }
        mActivity.overridePendingTransition(DEFAULT_ENTER_ANIM,
                DEFAULT_EXIT_ANIM);
    }

    /**
     * 默认带Bundle跳转
     */
    public void jumpActivityBundle(Class<?> cls, String key, Bundle mBundle) {
        Intent intent = null;
        if (cls != null) {
            intent = new Intent(mActivity, cls);
        }
        if (!LFormat.isEmpty(key) && mBundle != null) {
            intent.putExtra(key, mBundle);
        }
        if (cls != null && intent != null) {
            this.startActivity(intent);
        }
        // API LEVEL5.0
        mActivity.overridePendingTransition(DEFAULT_ENTER_ANIM, DEFAULT_EXIT_ANIM);
    }

    /**
     * 默认带Bundle跳转
     */
    public void jumpActivityBundleForResult(int requestCode, Class<?> cls, String key, Bundle mBundle) {
        Intent intent = null;
        if (cls != null) {
            intent = new Intent(mActivity, cls);
        }
        if (!LFormat.isEmpty(key) && mBundle != null) {
            intent.putExtra(key, mBundle);
        }
        if (cls != null && intent != null) {
            startActivityForResult(intent, requestCode);
        }
        // API LEVEL5.0
        mActivity.overridePendingTransition(DEFAULT_ENTER_ANIM, DEFAULT_EXIT_ANIM);
    }

    // 无参数
    public void deftStartActivityForResult(int requestCode, Class<?> cls) {
        Intent intent = null;
        if (cls != null) {
            intent = new Intent(mActivity, cls);
        }
        if (cls != null && intent != null) {
            startActivityForResult(intent, requestCode);
        }
        mActivity.overridePendingTransition(DEFAULT_ENTER_ANIM,
                DEFAULT_EXIT_ANIM);
    }
}

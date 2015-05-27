package com.seable.potato.biz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.seable.potato.R;
import com.seable.potato.biz.CustomProgressDialog.OnKeyBackListener;
import com.seable.potato.util.LFormat;


/**
 * @author Chen Lei
 * @version 1.3.1
 */
public abstract class LActivity extends FragmentActivity implements
        ILHandlerCallback, OnKeyBackListener {
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

    /**
     * 当前Activity对象
     */
    @SuppressWarnings("unused")
    private LActivity mActivity;

    /**
     * 当前Fragment对象
     */
    @SuppressWarnings("unused")
    private LFragment mFragment;

    /**
     * 全局的上下文对象
     */
    public static Context mContext;


    public LApplication mLApplication;
    public boolean noMoreData;
    public boolean isRefreshing;
    /**
     * 进度提示框
     */
//	private LProgress mProgressDialog;
    private static CustomProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

    }

    /**
     * @param savedInstanceState
     * @param showTitle          是否显示Title
     */
    protected void onCreate(Bundle savedInstanceState, boolean showTitle) {
        if (!showTitle) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        mContext = this;
        mLApplication = (LApplication) getApplication();
        mLApplication.setContext(mContext);
//		mLApplication.setDestroyActivitys(false);
        mLApplication.addActivity(this);
        mLApplication.setFragmentManager(this.getSupportFragmentManager());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dismissProgressDialog();
        mProgressDialog = null;
    }

    @Override
    protected void onDestroy() {
        mLApplication.delActivity(this);
        super.onDestroy();
    }

    /**
     * 显示一个ProgressDialog，直接调用即可<br/>
     * 此ProgressDialog会在onPause方法内自动销毁
     *
     * @param text 提示文字
     */
    public void showProgressDialog(String text, boolean isCancelable, boolean isFinish) {

        if (mProgressDialog == null) {
//			mProgressDialog = new LProgress(this);
            mProgressDialog = CustomProgressDialog.createDialog(this, isCancelable);
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
            finish();
        }
        overridePendingTransition(DEFAULT_BACK_ENTER_ANIM,
                DEFAULT_BACK_EXIT_ANIM);
    }

    /**
     * 当你使用了
     * {@linkplain com.leo.base.handler.LHandler#startLoadingData(com.leo.base.net.LReqEntity)
     * LHandler.startLoadingData(LReqEntity)} 方法请求网络后，
     * {@linkplain com.leo.base.handler.LHandler LHandler} 会自动调用此方法，并将解析的结果
     * {@linkplain com.leo.base.entity.LMessage LMessage} 对象传回
     *
     * @param msg
     */
    @Override
    public void onResultHandler(LMessage msg, int requestId) {
        // ... 写入你需要的代码
    }

    // 封装Activity中页面跳转

    /**
     * 销毁当前Activity
     */
    public void defaultFinishActivity() {
        this.finish();
        this.overridePendingTransition(DEFAULT_BACK_ENTER_ANIM,
                DEFAULT_BACK_EXIT_ANIM);
    }

    /**
     * 跳转Activity1
     */
    public void defaultJumpActivity(Class<?> cls) {
        Intent intent = null;
        if (cls != null) {
            intent = new Intent(this, cls);
        }
        if (cls != null && intent != null) {
            this.startActivity(intent);
        }
        this.overridePendingTransition(DEFAULT_ENTER_ANIM, DEFAULT_EXIT_ANIM);
    }

    /**
     * 跳转跳转前 关闭当前activity
     */
    public void defaultJumpActivityAndFinsh(Class<?> cls) {
        Intent intent = null;
        if (cls != null) {
            intent = new Intent(this, cls);
        }
        if (cls != null && intent != null) {
            this.startActivity(intent);
        }
        this.finish();
        this.overridePendingTransition(DEFAULT_ENTER_ANIM, DEFAULT_EXIT_ANIM);
    }

    // 无参数startActivityForResult
    public void startActivityForResult(int requestCode, Class<?> cls) {
        Intent intent = null;
        if (cls != null) {
            intent = new Intent(this, cls);
        }
        if (cls != null && intent != null) {
            this.startActivityForResult(intent, requestCode);
        }
        this.overridePendingTransition(DEFAULT_ENTER_ANIM, DEFAULT_EXIT_ANIM);
    }

    /**
     * 有参数startActivityForResult
     */
    public void startActivityForResult(int requestCode, Class<?> cls,
                                       String key, String value) {
        Intent intent = null;
        if (cls != null) {
            intent = new Intent(this, cls);
        }
        if (!LFormat.isEmpty(key) && !LFormat.isEmpty(value)) {
            intent.putExtra(key, value);
        }
        if (cls != null && intent != null) {
            this.startActivityForResult(intent, requestCode);
        }
        this.overridePendingTransition(DEFAULT_ENTER_ANIM, DEFAULT_EXIT_ANIM);
    }

    /**
     * 带BundlestartActivityForResult
     */
    public void jumpActivityBundleForResult(int requestCode, Class<?> cls,
                                            String key, Bundle mBundle) {
        Intent intent = null;
        if (cls != null) {
            intent = new Intent(this, cls);
        }
        if (!LFormat.isEmpty(key) && mBundle != null) {
            intent.putExtra(key, mBundle);
        }
        if (cls != null && intent != null) {
            this.startActivityForResult(intent, requestCode);
        }
        this.overridePendingTransition(DEFAULT_ENTER_ANIM, DEFAULT_EXIT_ANIM);
    }

    /**
     * 默认带Bundle跳转
     */
    public void jumpActivityBundle(Class<?> cls, String key, Bundle mBundle) {
        Intent intent = null;
        if (cls != null) {
            intent = new Intent(this, cls);
        }
        if (!LFormat.isEmpty(key) && mBundle != null) {
            intent.putExtra(key, mBundle);
        }
        if (cls != null && intent != null) {
            this.startActivity(intent);
        }
        // API LEVEL5.0
        this.overridePendingTransition(DEFAULT_ENTER_ANIM, DEFAULT_EXIT_ANIM);
    }

    /**
     * 默认带Bundle跳转  关闭当前activity
     */
    public void jumpActivityBundleAndFinsh(Class<?> cls, String key, Bundle mBundle) {
        Intent intent = null;
        if (cls != null) {
            intent = new Intent(this, cls);
        }
        if (!LFormat.isEmpty(key) && mBundle != null) {
            intent.putExtra(key, mBundle);
        }
        if (cls != null && intent != null) {
            this.startActivity(intent);
        }
        this.finish();
        // API LEVEL5.0
        this.overridePendingTransition(DEFAULT_ENTER_ANIM, DEFAULT_EXIT_ANIM);
    }

    // 默认带参数跳转
    public void jumpActivityString(Class<?> cls, String key, String value) {
        Intent intent = null;
        if (cls != null) {
            intent = new Intent(this, cls);
        }
        if (!LFormat.isEmpty(key) && !LFormat.isEmpty(value)) {
            intent.putExtra(key, value);
        }
        if (cls != null && intent != null) {
            this.startActivity(intent);
        }
        // API LEVEL5.0
        this.overridePendingTransition(DEFAULT_ENTER_ANIM, DEFAULT_EXIT_ANIM);
    }

    // 默认带参数跳转
    public void jumpActivityStringAndFinish(Class<?> cls, String key, String value) {
        Intent intent = null;
        if (cls != null) {
            intent = new Intent(this, cls);
        }
        if (!LFormat.isEmpty(key) && !LFormat.isEmpty(value)) {
            intent.putExtra(key, value);
        }
        if (cls != null && intent != null) {
            this.startActivity(intent);
        }
        this.finish();
        // API LEVEL5.0
        this.overridePendingTransition(DEFAULT_ENTER_ANIM, DEFAULT_EXIT_ANIM);
    }

    // 自定义带参数跳转,自定义动画
    public void jumpActivity(Class<?> cls, String key, String value,
                             int enterAnim, int exitAnim) {
        Intent intent = null;
        if (cls != null) {
            intent = new Intent(this, cls);
        }
        if (!LFormat.isEmpty(key) && !LFormat.isEmpty(value)) {
            intent.putExtra(key, value);
        }
        if (cls != null && intent != null) {
            this.startActivity(intent);
        }
        // API LEVEL5.0
        this.overridePendingTransition(DEFAULT_ENTER_ANIM, DEFAULT_EXIT_ANIM);
    }
}

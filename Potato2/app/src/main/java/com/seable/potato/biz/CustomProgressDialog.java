package com.seable.potato.biz;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.seable.potato.R;


/**
 * 自定义样式数据加载框
 *
 * @author wang wei yu
 */
public class CustomProgressDialog extends Dialog implements DialogInterface.OnKeyListener, OnClickListener {

    private static boolean isFinish = false;
    private static Context context = null;
    //	private static ImageButton btnLoadingClose;
    private static CustomProgressDialog customProgressDialog = null;
    private OnKeyBackListener mOnKeyBackListener;

    public CustomProgressDialog(Context contexts) {
        super(contexts);
        context = contexts;
    }

    public CustomProgressDialog(Context contexts, int theme) {
        super(contexts, theme);
        context = contexts;
    }

    public static CustomProgressDialog createDialog(Context contexts, boolean isCancelable
    ) {
        context = contexts;
        customProgressDialog = new CustomProgressDialog(context,
                R.style.CustomProgressDialog);
        customProgressDialog.setContentView(R.layout.customprogressdialog_square);
        customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        customProgressDialog.getWindow().getAttributes().width = LayoutParams.FILL_PARENT;
        customProgressDialog.getWindow().getAttributes().height = LayoutParams.FILL_PARENT;
//		btnLoadingClose = (ImageButton) customProgressDialog
//				.findViewById(R.id.tv_loading_close_icon);
        customProgressDialog.setCancelable(isCancelable);
        customProgressDialog.setOnKeyListener(customProgressDialog);
//		btnLoadingClose.setVisibility(View.GONE);
//		btnLoadingClose
//				.setOnClickListener(new android.view.View.OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						// TODO 自动生成的方法存根
//						if (((Activity) context) != null) {
//							((Activity) context).finish();
//						}else{
//							customProgressDialog.dismiss();
//						}
//					}
//				});
        return customProgressDialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO 自动生成的方法存根
        super.onCreate(savedInstanceState);
    }

    public void setOnKeyBackListener(boolean isFinishs, OnKeyBackListener keyBackListener) {
        isFinish = isFinishs;
        customProgressDialog.mOnKeyBackListener = keyBackListener;
    }

    public void show(String text) {
        if (text != null || (!"".equals(text))) {
            customProgressDialog.setMessage(text);
        }
        if (!isShowing()) {
            customProgressDialog.show();
        }
    }


    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            dialog.dismiss();
            if (mOnKeyBackListener != null) {
                mOnKeyBackListener.onKeyBackListener(isFinish);//处理回调操作内容

            }
            return true;
        }
        return false;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO 自动生成的方法存根
        super.onWindowFocusChanged(hasFocus);
        if (customProgressDialog == null) {
            return;
        }
        ImageView imageView = (ImageView) customProgressDialog
                .findViewById(R.id.loadingImageView);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView
                .getBackground();
        animationDrawable.start();
    }

    /**
     * [Summary] setTitile 标题
     *
     * @param strTitle
     * @return
     */
    public CustomProgressDialog setTitile(String strTitle) {
        return customProgressDialog;
    }

    /**
     * [Summary] setMessage 提示内容
     *
     * @param strMessage
     * @return
     */
    public CustomProgressDialog setMessage(String strMessage) {
        TextView tvMsg = (TextView) customProgressDialog
                .findViewById(R.id.id_tv_loadingmsg);

        if (tvMsg != null) {
            tvMsg.setText(strMessage);
        }

        return customProgressDialog;
    }

    @Override
    public void onClick(View v) {


    }

    public interface OnKeyBackListener {
        void onKeyBackListener(boolean isFinish);
    }

}

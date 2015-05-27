package com.seable.potato.data.listener;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.seable.potato.R;
import com.seable.potato.data.entity.Entity_PublicDialogItem;
import com.seable.potato.util.dialog.PublicDialogListener;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * @author 王维玉
 * @ClassName: PublicDialogUtil
 * @Description: 公共对话框工具类
 * @date 2015-01-22 11:39
 */
public class PublicDialogUtil {

    private static Context mContext;
    private static String strTtitel;
    private static ArrayList<Entity_PublicDialogItem> itemList;

    private TextView tvTitel;
    private ListView mListView;
    private SimpleAdapter sAdapter;
    private ArrayList<HashMap<String, Object>> sList = new ArrayList<HashMap<String, Object>>();

    // 对话框
    private mCancelDialog mDialog;
    // 對話框视图VIEW
    private View mDialogView;
    private static PublicDialogUtil mInstantce;
    private static PublicDialogListener l;

    public PublicDialogUtil() {
        if (null == mDialog) {
            initDialog(l);
        }
        mDialog.show();
    }

    /**
     * 初始化
     *
     * @param mContexts
     * @param titel     对话框标题
     * @param items     对话框选项列表
     * @param lis       监听用户点击时间，返回选择项数 对应的code
     */
    public static PublicDialogUtil getInstance(Context mContexts, String titel,
                                               ArrayList<Entity_PublicDialogItem> items, PublicDialogListener lis) {
        mContext = mContexts;
        strTtitel = titel;
        itemList = items;
        l = lis;
        mInstantce = new PublicDialogUtil();
        return mInstantce;
    }

    /**
     * 初始化对话框
     */
    private void initDialog(final PublicDialogListener l) {
        mDialog = new mCancelDialog(mContext, R.style.public_customer_dialog);
        mDialog.setCancelable(true);
        mDialogView = LayoutInflater.from(mContext).inflate(
                R.layout.dialog_public, null);
        mDialog.setContentView(mDialogView);
        mListView = (ListView) mDialogView.findViewById(R.id.lv_public);
        tvTitel = (TextView) mDialogView.findViewById(R.id.tv_public_titel);
        tvTitel.setText(strTtitel);
        HashMap<String, Object> map;
        for (int i = 0; i < itemList.size(); i++) {
            map = new HashMap<String, Object>();
            map.put("tv", itemList.get(i).getName());
            sList.add(map);
        }
        sAdapter = new SimpleAdapter(mContext, sList,
                R.layout.item_dialog_public, new String[]{"tv"},
                new int[]{R.id.tv_public_Item});
        mListView.setAdapter(sAdapter);


        WindowManager windowManager = mDialog.getWindow().getWindowManager();
        Display d = windowManager.getDefaultDisplay();                                // 获取屏幕宽、高用
        WindowManager.LayoutParams p = mDialog.getWindow().getAttributes();        // 获取对话框当前的参数值
        DisplayMetrics metric = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metric);
        float density = metric.density;

        ListAdapter listAdapter = mListView.getAdapter();
        int count = listAdapter.getCount();
        int totalHeight = 0;
        for (int i = 0; i < count; i++) {
            View listItem = listAdapter.getView(i, null, mListView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        totalHeight = totalHeight
                + (mListView.getDividerHeight() * (count - 1))
                + mListView.getPaddingTop() + mListView.getPaddingBottom();

        tvTitel.measure(0, 0);
        if (totalHeight + tvTitel.getMeasuredHeight() >= (int) (d.getHeight() - 200 * density)) {
            p.height = (int) (d.getHeight() - 200 * density);                                    // 高度设置为屏幕的0.6
        }
        p.width = (int) (d.getWidth() - 64 * density);                                        // 宽度设置为屏幕的0.65
        mDialog.getWindow().setAttributes(p);


        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                mDialog.cancel();
                if (l != null) {
                    l.onPublicDialogResult(itemList.get(position).getCode());
                }
            }
        });
        if (l == null) {
            LinearLayout mLinear = (LinearLayout) mDialogView
                    .findViewById(R.id.ll_public);
            mLinear.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    mDialog.cancel();

                }
            });
        }
        mDialog.setContentView(mDialogView);

    }


    public Dialog getmDialog() {
        return mDialog;
    }

    public void setmDialog(mCancelDialog mDialog) {
        this.mDialog = mDialog;
    }

    public class mCancelDialog extends Dialog {

        public mCancelDialog(Context context) {
            super(context);
        }

        public mCancelDialog(Context context, int style) {
            super(context, style);
        }

        @Override
        public void cancel() {
            l.onPublicDialogResult(-1);
            super.cancel();
        }

    }
}

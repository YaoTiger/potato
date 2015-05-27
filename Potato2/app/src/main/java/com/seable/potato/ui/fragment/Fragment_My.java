package com.seable.potato.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.seable.potato.R;
import com.seable.potato.biz.LFragment;
import com.seable.potato.biz.T;
import com.seable.potato.common.Constants;
import com.seable.potato.common.MApplication;
import com.seable.potato.data.entity.Entity_My;
import com.seable.potato.data.entity.Entity_Update;
import com.seable.potato.ui.activity.Activity_Login;
import com.seable.potato.ui.activity.my.Activity_Info;
import com.seable.potato.ui.activity.my.Activity_My_Edit;
import com.seable.potato.ui.adapter.Adapter_My;
import com.seable.potato.ui.view.View_TitleBar;
import com.seable.potato.util.PreferencesService;
import com.seable.potato.util.dialog.Effectstype;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王维玉
 * @ClassName: Fragment_My
 * @Description: 个人中心
 * @date 2015-04-01 15:26
 */
public class Fragment_My extends LFragment implements OnItemClickListener, OnClickListener {

    private View view;
    private ImageView ivImage;
    private View_TitleBar titleBar;
    private static ListView mListView;
    private static Adapter_My sAdapter;
    private static List<Entity_My> list;
    /**
     * 业务逻辑
     */


    private Entity_Update infoUpdate;
    private Effectstype effect;
    private static double PastVersion;
//	private static double NowVersion;
//	private static String apkUrl;
    /**
     * 下载的进度条
     */
    private ProgressBar pBar;
    /**
     * 下载的进度条
     */
    private TextView rateTv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, null);
        findViewById();
        initView();
        return view;
    }

    private void findViewById() {
        titleBar = (View_TitleBar) view.findViewById(R.id.TitleBar);
        titleBar.setBackImshow(false);
        mListView = (ListView) view.findViewById(R.id.lv_my);
        ivImage = (ImageView) view.findViewById(R.id.iv_my_image);
        ivImage.setOnClickListener(this);
        // 初始化view
    }

    // @Override
    // public void setUserVisibleHint(boolean isVisibleToUser) {
    // super.setUserVisibleHint(isVisibleToUser);
    // if (isVisibleToUser) {
    // // 相当于Fragment的onResume
    // } else {
    // // 相当于Fragment的onPause
    // }
    // }

    private void initView() {
        // 初始化view
        list = new ArrayList<Entity_My>();
        // 部长
        for (int i = 0; i < Constants.MY.length; i++) {
            Entity_My en = new Entity_My();
            en.setTitle(Constants.MY[i]);
            en.setImg(Constants.MY_COLOR[i]);
            list.add(en);
        }
        sAdapter = new Adapter_My(mContext, list);
        mListView.setAdapter(sAdapter);
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_my_image:
                defaultJumpActivity(Activity_My_Edit.class);
                break;

            default:
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        switch (position) {
            case 0:
                //"版本更新"
                T.ss("已是最新版本");
//			biz.getUpdate();
                break;
            case 1:
                //"关于我们"
                defaultJumpActivity(Activity_Info.class);
                break;
            case 2:
                //"编辑资料",
                defaultJumpActivity(Activity_My_Edit.class);
                break;
            case 3:
                //"退出登录"
                PreferencesService service = new PreferencesService(mContext);
                service.clearLoginInfo();
                MApplication.get().killActivities();
                defaultJumpActivity(Activity_Login.class);
                break;

            default:
                break;
        }
    }


}



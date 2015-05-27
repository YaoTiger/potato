package com.seable.potato.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.seable.potato.R;
import com.seable.potato.biz.LFragment;
import com.seable.potato.biz.LMessage;
import com.seable.potato.biz.T;
import com.seable.potato.common.Constants;
import com.seable.potato.common.MApplication;
import com.seable.potato.data.entity.Entity_TaskCount;
import com.seable.potato.data.entity.base.Entity_Base;
import com.seable.potato.ui.activity.Activity_Main;
import com.seable.potato.ui.activity.taskcount.Activity_Task_History;
import com.seable.potato.ui.adapter.Adapter_Task_Count;
import com.seable.potato.ui.view.View_TitleBar;
import com.seable.potato.util.LFormat;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王维玉
 * @ClassName: Fragment_TaskCount
 * @Description: 任务统计
 * @date 2015-04-01 13:16
 */
public class Fragment_TaskCount extends LFragment implements OnItemClickListener {

    private View view;
    private View_TitleBar titleBar;
    private static ListView mListView;
    private static Adapter_Task_Count sAdapter;
    private static List<Entity_TaskCount> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_task_count, null);
        findViewById();
        initView();
        return view;
    }

    private void findViewById() {
        titleBar = (View_TitleBar) view.findViewById(R.id.TitleBar);
        titleBar.setBackImshow(false);
        mListView = (ListView) view.findViewById(R.id.lv_task_count);
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
        list = new ArrayList<Entity_TaskCount>();
        // 部长
        for (int i = 0; i < Constants.TASK_COUNT.length; i++) {
            Entity_TaskCount en = new Entity_TaskCount();
            en.setTitle(Constants.TASK_COUNT[i]);
            en.setContent(MApplication.tabNum[i] + Constants.TASK_COUNT_UNIT);
            en.setColor(Constants.TASK_COUNT_COLOR[i]);
            list.add(en);
        }
        sAdapter = new Adapter_Task_Count(mContext, list);
        mListView.setAdapter(sAdapter);
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onResultHandler(LMessage msg, int requestId) {
        super.onResultHandler(msg, requestId);
        if (msg != null) {
            Entity_Base entity = (Entity_Base) msg.getObj();
            if (entity != null) {
                if (entity.getCode() == 1) {
                    switch (requestId) {
                        default:
                            break;
                    }

                } else if (entity.getCode() == 0) {
                    if (!LFormat.isEmpty(entity.getMsg())) {
                        T.ss(entity.getMsg());
                    } else {
                        T.ss(R.string.msg_loading_failed);
                    }
                }

            }
        }
        dismissProgressDialog();
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        switch (position) {
            case 0://待确认
                Activity_Main.getCurrentInstance().setCurrentTab(0, 0);
                break;
            case 1://待处理
                Activity_Main.getCurrentInstance().setCurrentTab(0, 1);
                break;
            case 2://待审核
                Activity_Main.getCurrentInstance().setCurrentTab(0, 2);
                break;
            case 3://历史未通过
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.JUMP_PARAMETER_KEY_TYPE,
                        Constants.PARAMETER_VALUE_TASK5_HISTORY_FAILED);
                jumpActivityBundle(Activity_Task_History.class,
                        Constants.JUMP_PARAMETER_KEY_BUNDLE, bundle);
                break;
            case 4://历史已完成
                bundle = new Bundle();
                bundle.putInt(Constants.JUMP_PARAMETER_KEY_TYPE,
                        Constants.PARAMETER_VALUE_TASK5_HISTORY_SUCCESS);
                jumpActivityBundle(Activity_Task_History.class,
                        Constants.JUMP_PARAMETER_KEY_BUNDLE, bundle);

                break;

            default:
                break;
        }
    }

}

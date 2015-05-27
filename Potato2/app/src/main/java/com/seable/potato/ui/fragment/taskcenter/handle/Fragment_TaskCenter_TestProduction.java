package com.seable.potato.ui.fragment.taskcenter.handle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seable.potato.R;
import com.seable.potato.biz.LFragment;
import com.seable.potato.biz.LMessage;
import com.seable.potato.biz.T;
import com.seable.potato.data.entity.base.Entity_Base;
import com.seable.potato.util.LFormat;

/**
 * @author 王维玉
 * @ClassName: Fragment_TaskCenter_05Success
 * @Description: 任务中心 - 已完成
 * @date 2015-03-25 11:45
 */
public class Fragment_TaskCenter_TestProduction extends LFragment {
    private static View view;
    // 标志位，标志已经初始化完成。
    private static boolean isPrepared = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            return view;
        }
        view = inflater.inflate(R.layout.fragment_task_handle_testproduction, null);
        isPrepared = true;
        findViewById();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // 相当于Fragment的onResume
            if (isPrepared) {
                initView();
                // Fragment_TaskCenter.getCurrentInstance().setUnreadNum(1, 9);
            }
        } else {
            // 相当于Fragment的onPause
        }
    }

    @Override
    public void onResume() {
        initView();
        super.onResume();
    }

    private void findViewById() {
        // titleBar = (View_TitleBar) view.findViewById(R.id.TitleBar);
        // titleBar.setBackImshow(false);
        // tv = (TextView) view.findViewById(R.id.tv);
        // tv.setOnClickListener(new OnClickListener() {
        //
        // @Override
        // public void onClick(View v) {
        // // TODO Auto-generated method stub
        // defaultJumpActivity(Activity_Check.class);
        // }
        // });
        // 初始化view
    }

    private void initView() {
        // 初始化view
    }


    @Override
    public void onResultHandler(LMessage msg, int requestId) {
        super.onResultHandler(msg, requestId);
        if (msg != null) {
            Entity_Base entity = (Entity_Base) msg.getObj();
            if (entity != null) {
                if (entity.getCode() == 1) {
                    switch (requestId) {
                        // case Constants.PARAMETER_VALUE_PAGE_LAST:
                        // Entity_Task_PartBase base = (Entity_Task_PartBase)
                        // msg.getObj();
                        // List<Entity_Task_Part> listData = base.getDatas();
                        // for (int i = 0; i < listData.size(); i++) {
                        // list.set(i, listData.get(i));
                        // }
                        // if (base.getDatas() == null) {
                        // T.ss("当前已是最新数据了");
                        // }
                        // break;
                        // case Constants.PARAMETER_VALUE_PAGE_NEXT:
                        // base = (Entity_Task_PartBase) msg.getObj();
                        // listData = base.getDatas();
                        // for (int i = 0; i < listData.size(); i++) {
                        // list.add(listData.get(i));
                        // }
                        // if (listData.size() < Constants.LIMIT_NEIGHBOUR_LIST_NUM
                        // || base.getDatas() == null) {
                        // isLast = true;
                        // }
                        // if (base.getDatas()== null) {
                        // T.ss("没有更多了");
                        // }
                        // break;

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
}

package com.seable.potato.ui.fragment.taskcenter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.seable.potato.R;
import com.seable.potato.biz.LFragment;
import com.seable.potato.biz.LMessage;
import com.seable.potato.biz.T;
import com.seable.potato.common.Constants;
import com.seable.potato.common.MApplication;
import com.seable.potato.data.entity.HttpResponseObject;
import com.seable.potato.data.entity.base.Entity_Base;
import com.seable.potato.data.entity.tigerentity.HttpBaseQuery;
import com.seable.potato.data.entity.tigerentity.TaskInfoEntity;
import com.seable.potato.data.http.HttpRequestManager;
import com.seable.potato.ui.activity.taskcenter.Activity_Task_Detail;
import com.seable.potato.ui.adapter.Adapter_Task_Part_ListView;
import com.seable.potato.ui.view.View_TabPager;
import com.seable.potato.util.LFormat;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王维玉
 * @ClassName: Fragment_TaskCenter_04Failed
 * @Description: 任务中心 - 未通过
 * @date 2015-03-25 11:45
 */
public class Fragment_TaskCenter_04Failed extends LFragment implements
        OnItemClickListener {
    private static View view;
    // 标志位，标志已经初始化完成。
    private static boolean isPrepared = false;

    private static ListView mListView;
    private static PullToRefreshListView mPullRefreshListView;
    private static Adapter_Task_Part_ListView mAdapter;
    private static List<TaskInfoEntity> list;
    private static int tabUnread;

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
        view = inflater.inflate(R.layout.fragment_02_handle, null);
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
        mPullRefreshListView = (PullToRefreshListView) view
                .findViewById(R.id.lv_task);
        // 设置下拉 或 上拉时显示的文字
        mPullRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel(
                getString(R.string.label_pull_up));
        mPullRefreshListView.getLoadingLayoutProxy().setRefreshingLabel(
                getString(R.string.label_refreshing));
        mPullRefreshListView.getLoadingLayoutProxy().setReleaseLabel(
                getString(R.string.label_release));
        // 为mPullRefreshListView设置刷新监听
        mPullRefreshListView
                .setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

                    @Override
                    public void onPullDownToRefresh(
                            PullToRefreshBase<ListView> refreshView) {
                        list = new ArrayList<TaskInfoEntity>();
                        syncFromNetworkFirst();
                    }

                    @Override
                    public void onPullUpToRefresh(
                            PullToRefreshBase<ListView> refreshView) {
                        if (list != null && list.size() > 0) {
                            syncMoreFromNetwork();
                        } else {
                            syncFromNetworkFirst();
                        }
                    }
                });
        // 设置刷新的图标
        mPullRefreshListView.setLoadingDrawable(getResources().getDrawable(
                R.drawable.icon_refresh_03));
        // TODO
        mPullRefreshListView.setMode(Mode.DISABLED);
        // mPullRefreshListView.setMode( Mode.BOTH);
        mListView = mPullRefreshListView.getRefreshableView();
        // mListView.setDividerHeight(0);
        mListView.setEmptyView(view.findViewById(android.R.id.empty));
        mListView.setOnItemClickListener(this);
    }

    private void initView() {
        // 初始化view
        tabUnread = MApplication.tabNum3;
        setUnreadTab(0, MApplication.tabNum0);
        setUnreadTab(1, MApplication.tabNum1);
        setUnreadTab(2, MApplication.tabNum2);
        setUnreadTab(3, MApplication.tabNum3);
        setUnreadTab(4, MApplication.tabNum4);
        syncFromNetworkFirst();
    }

    private void setUnreadTab(int position, int tabUnread) {
        View_TabPager tabPager = View_TabPager.getCurrentInstance();
        if (tabUnread == 0) {
            tabPager.hideNumAt(position);
        } else {
            try {
                tabPager.showNumAt(position);
            } catch (Exception e) {
            }
            tabPager.setNumAt(position, tabUnread);
        }
    }

    int pageNo=1;

    /**
     * 第一次获取
     */
    protected void syncFromNetworkFirst() {
        list = new ArrayList<TaskInfoEntity>();
        pageNo = 1;
        requestHttp();


        // biz.getBulletinList(- 1,
        // Constants.PARAMETER_VALUE_PAGE_NEXT);

    }

    private void requestHttp() {

        try {
            list=application.dbUtils.findAll(Selector.from(TaskInfoEntity.class).where("status","=",HttpRequestManager.status_wait_deal));
            mAdapter = new Adapter_Task_Part_ListView(mContext, list);
            mListView.setAdapter(mAdapter);
            checkEmpty();
            MApplication.tabNum1=mAdapter.getCount();
//            initView();
        } catch (DbException e) {
            e.printStackTrace();
        }

        application
                .mHttpRequestManager.getTaskList(entity.getId(), HttpRequestManager.status_fail, pageNo, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                resultParse(responseInfo);
            }

            @Override
            public void onFailure(HttpException error, String msg) {

                error.printStackTrace();
            }
        });
    }
    private void resultParse(ResponseInfo<String> responseInfo) {
        String result = responseInfo.result;

        Log.i("ttt",result);
        Gson gson = new Gson();
        HttpBaseQuery<TaskInfoEntity> object = gson.fromJson(result, new TypeToken<HttpBaseQuery<TaskInfoEntity>>() {
        }.getType());
        if (object.isSuccess()) {
            ArrayList<TaskInfoEntity> listEntity=object.getData().getRows();
            if(listEntity!=null&&listEntity.size()>0) {
                pageNo += 1;
                mAdapter = new Adapter_Task_Part_ListView(mContext, list);
                mListView.setAdapter(mAdapter);
                checkEmpty();
                MApplication.tabNum1 = mAdapter.getCount();
            }
        }

        Log.i(Fragment_TaskCenter_01Confirm.class.getName(), "result==" + result);

        mPullRefreshListView.onRefreshComplete();
    }

    private void checkEmpty() {
        View emptyView = (View) view.findViewById(R.id.empty);
        if (list == null || list.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
        }

    }

    /**
     * 上拉加载更多
     */
    protected void syncMoreFromNetwork() {
        requestHttp();
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
                        // TaskInfoEntityBase base = (TaskInfoEntityBase)
                        // msg.getObj();
                        // List<TaskInfoEntity> listData = base.getDatas();
                        // for (int i = 0; i < listData.size(); i++) {
                        // list.set(i, listData.get(i));
                        // }
                        // if (base.getDatas() == null) {
                        // T.ss("当前已是最新数据了");
                        // }
                        // break;
                        // case Constants.PARAMETER_VALUE_PAGE_NEXT:
                        // base = (TaskInfoEntityBase) msg.getObj();
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
                    mAdapter.notifyDataSetChanged();

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
    public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                            long arg3) {
        // TODO Auto-generated method stub
        position=position-mListView.getHeaderViewsCount();
        TaskInfoEntity taskInfoEntity=mAdapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.JUMP_PARAMETER_KEY_ID, taskInfoEntity
                .getId());
        bundle.putInt(Constants.JUMP_PARAMETER_KEY_TYPE, taskInfoEntity
                .getTaskType());
        bundle.putInt(Constants.JUMP_PARAMETER_KEY_TASK_TYPE,
                Constants.PARAMETER_VALUE_TASK3_FAILED);
        bundle.putSerializable("entity",taskInfoEntity);
        jumpActivityBundleForResult(1,
                Activity_Task_Detail.class,
                Constants.JUMP_PARAMETER_KEY_BUNDLE, bundle);
    }
}

package com.seable.potato.ui.fragment.taskcenter.handle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.seable.potato.R;
import com.seable.potato.biz.LFragment;
import com.seable.potato.biz.LMessage;
import com.seable.potato.biz.T;
import com.seable.potato.data.entity.base.Entity_Base;
import com.seable.potato.util.LFormat;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王维玉
 * @ClassName: Fragment_TaskCenter_01Confirm
 * @Description: 待确认任务列表
 * @date 2015-03-30 13:12
 */
public class Fragment_TaskCenter_Harvest extends LFragment {

    private View view;
    // private View_TitleBar titleBar;
    // 标志位，标志已经初始化完成。
    private static boolean isPrepared = false;

    private ListView mListView;
    private List<Integer> lists;
    private AddAdapter mAdapter;
    private Button btnAdd, btnSave;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_task_handle_harvest, null);
        findViewById();
        isPrepared = true;
        initView();
        return view;
    }

    private void initView() {
        // TODO Auto-generated method stub

    }

    private void findViewById() {
        mListView = (ListView) view.findViewById(R.id.listView1);
        btnSave = (Button) view.findViewById(R.id.btn_handle_haevest_ok);
        btnAdd = (Button) view.findViewById(R.id.btn_handle_haevest_add);
        lists = new ArrayList<Integer>();
        lists.add(1);// 保证adapter 中有一条数据（默认）
        mAdapter = new AddAdapter(lists);
        mListView.setAdapter(mAdapter);
        // 点击按钮添加一个item
        btnAdd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mAdapter.addItem(1);

            }
        });
    }

    class AddAdapter extends BaseAdapter {

        private List<Integer> data;

        public AddAdapter(List<Integer> data) {
            super();
            this.data = data;
        }

        // 动态添加一个item，添加后adapter更新
        public void addItem(int num) {
            data.add(num);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Integer getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(mContext, R.layout.view_image, null);
            return convertView;
        }

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

}

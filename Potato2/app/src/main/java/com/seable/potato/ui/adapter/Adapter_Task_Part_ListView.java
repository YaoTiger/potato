package com.seable.potato.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.seable.potato.R;
import com.seable.potato.common.Constants;
import com.seable.potato.data.entity.tigerentity.TaskInfoEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王维玉
 * @ClassName: BulletinAdapter
 * @Description: 小区公告- 帖子适配
 * @date 2015-01-23 13:26
 */
public class Adapter_Task_Part_ListView extends BaseAdapter {
    private List<TaskInfoEntity> myList;
    private Context ctx;

    public Adapter_Task_Part_ListView(Context ctx, List<TaskInfoEntity> myList) {
if(myList==null){
    myList=new ArrayList<TaskInfoEntity>();
}
        this.myList = myList;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public TaskInfoEntity getItem(int arg0) {
        return myList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(final int arg0, View arg1, ViewGroup arg2) {
        Holder hold;
        if (arg1 == null) {
            hold = new Holder();
            arg1 = View.inflate(ctx, R.layout.item_task_part, null);
            hold.tvTime = (TextView) arg1.findViewById(R.id.tv_item_task_part_date);
            hold.tvType = (TextView) arg1.findViewById(R.id.tv_item_task_part_type);
            hold.tvCode = (TextView) arg1.findViewById(R.id.tv_item_task_part_code);
            hold.tvCompany = (TextView) arg1.findViewById(R.id.tv_item_task_part_company);
            hold.tvArea = (TextView) arg1.findViewById(R.id.tv_item_task_part_area);
            arg1.setTag(hold);

        } else {
            hold = (Holder) arg1.getTag();
        }
        hold.tvTime.setText(myList.get(arg0).getPlanDate() + "");
        hold.tvCode.setText(myList.get(arg0).getBatchNo() + "");
        hold.tvCompany.setText(myList.get(arg0).getCompany() + "");
        hold.tvArea.setText(myList.get(arg0).getAddress() + "");

        int type = myList.get(arg0).getTaskType();
        if (type == Constants.TYPE_TEST_1_FIRST_FEILD) {
            hold.tvType.setText(Constants.PARAMETER_VALUE_TYPE1_TEXT_FORST_FEILD);
        } else if (type == Constants.TYPE_TEST_2_SECOND_FEILD) {
            hold.tvType.setText(Constants.PARAMETER_VALUE_TYPE2_TEXT_SECOND_FEILD);
        } else if (type == Constants.TYPE_TEST_3_HARVEST) {
            hold.tvType.setText(Constants.PARAMETER_VALUE_TYPE3_TEXT_HARVEST);
        } else if (type == Constants.TYPE_TEST_4_STOREROOM) {
            hold.tvType.setText(Constants.PARAMETER_VALUE_TYPE4_TEXT_STOREROOM);
        }
        return arg1;
    }

    public static class Holder {

        TextView tvType;
        TextView tvCode;
        TextView tvTime;
        TextView tvCompany;
        TextView tvArea;

    }

}

package com.seable.potato.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seable.potato.R;
import com.seable.potato.data.entity.Entity_TaskCount;

import java.util.List;

/**
 * @author 王维玉
 * @ClassName: Adapter_Task_Count
 * @Description: 任务统计列表适配
 * @date 2015-04-01 13:24
 */
public class Adapter_Task_Count extends BaseAdapter {
    private List<Entity_TaskCount> myList;
    private Context ctx;

    public Adapter_Task_Count(Context ctx, List<Entity_TaskCount> myList) {

        this.myList = myList;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Object getItem(int arg0) {
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
            arg1 = View.inflate(ctx, R.layout.item_task_count, null);
            hold.tvTitle = (TextView) arg1.findViewById(R.id.tv_item_title);
            hold.tvContent = (TextView) arg1.findViewById(R.id.tv_item_content);
            hold.ivLine = (ImageView) arg1.findViewById(R.id.iv_item_left);
            arg1.setTag(hold);

        } else {
            hold = (Holder) arg1.getTag();
        }
        hold.tvTitle.setText(myList.get(arg0).getTitle() + "");
        hold.tvContent.setText(myList.get(arg0).getContent());

        hold.ivLine.setBackgroundResource(myList.get(arg0).getColor());
        hold.tvContent.setTextColor(myList.get(arg0).getColor());
        return arg1;
    }

    public static class Holder {

        TextView tvTitle;
        TextView tvContent;
        ImageView ivLine;

    }

}

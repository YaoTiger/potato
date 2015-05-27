package com.seable.potato.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.seable.potato.R;
import com.seable.potato.data.entity.Entity_Check;
import com.seable.potato.data.entity.tigerentity.CheckTaskEntity;

import java.util.List;

/**
 * @author 王维玉
 * @ClassName: Adapter_field
 * @Description: 地图页 - 上拉信息列表的适配
 * @date 2015-03-8 13:13
 */
public class Adapter_Check extends BaseAdapter {


    private List<Entity_Check> myList;
    private Context ctx;
    CheckTaskEntity entity;

    public Adapter_Check(Context ctx, List<Entity_Check> myList) {

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
            arg1 = View.inflate(ctx, R.layout.item_check, null);
            hold.tvName = (TextView) arg1.findViewById(R.id.tv_item_check_name);
            hold.tvNum = (TextView) arg1.findViewById(R.id.tv_item_check_num);
            hold.btnSub = (Button) arg1.findViewById(R.id.btn_item_check_sub);
            hold.btnAdd = (Button) arg1.findViewById(R.id.btn_item_check_add);
            arg1.setTag(hold);

        } else {
            hold = (Holder) arg1.getTag();
        }
        hold.tvName.setText(myList.get(arg0).getName() + "");
        hold.tvNum.setText(myList.get(arg0).getNum() + "");
        hold.btnSub.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                int num = myList.get(arg0).getNum() - 1;
                if (num >= 0) {
                    myList.get(arg0).setNum(num);
                    notifyDataSetChanged();
                }
            }
        });
        hold.btnAdd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                int num = myList.get(arg0).getNum() + 1;
                myList.get(arg0).setNum(num);
                notifyDataSetChanged();
            }
        });
        return arg1;
    }

    public static class Holder {

        TextView tvName;
        TextView tvNum;
        Button btnSub;
        Button btnAdd;

    }

    public List<Entity_Check> getMyList() {
        return myList;
    }
}

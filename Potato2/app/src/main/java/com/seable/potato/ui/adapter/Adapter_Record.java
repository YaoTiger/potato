package com.seable.potato.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seable.potato.R;
import com.seable.potato.data.entity.CheckEntity;

import java.util.ArrayList;


/**
 * @author 王维玉
 * @ClassName: GridImageAdapter
 * @Description: 添加照片gridview的适配
 * @date 2015-01-17 13:13
 */
public class Adapter_Record extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> dataList;

    public Adapter_Record(Context c, ArrayList<String> dataList) {

        mContext = c;
        if(dataList==null){
            dataList= new ArrayList<String>();
        }
        this.dataList = dataList;

    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder hold;
        if (convertView == null) {
            hold = new Holder();
            convertView = View.inflate(mContext, R.layout.item_test_record_gridview, null);
            hold.tvTitle = (TextView) convertView.findViewById(R.id.tv_item_record);
            hold.ivImage = (ImageView) convertView.findViewById(R.id.iv_item_record);
            convertView.setTag(hold);

        } else {
            hold = (Holder) convertView.getTag();
        }
        String path;
        if (dataList != null && position < dataList.size()) {
            path = dataList.get(position);
        } else {
            path = "camera_default";
        }
        if (path.contains("default")) {//如果选择�?��
            hold.ivImage.setImageResource(R.drawable.add_record);
            hold.tvTitle.setText(R.string.task_center_test_record_add);
        } else {
            hold.ivImage.setImageResource(R.drawable.test_record);
            hold.tvTitle.setText(path);
        }
        return convertView;
    }

    public static class Holder {
        TextView tvTitle;
        ImageView ivImage;
    }
}

package com.seable.potato.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.seable.potato.R;
import com.seable.potato.common.Constants;
import com.seable.potato.data.entity.Entity_Location;

import java.util.ArrayList;
import java.util.List;


/**
 * @author 王维玉
 * @ClassName: Adapter_SosInfoList
 * @Description: 地图页 - 上拉信息列表的适配
 * @date 2015-03-8 13:13
 */
public class Adapter_SosInfoList extends BaseAdapter {


    private List<Entity_Location> mData = new ArrayList<Entity_Location>();

    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public Adapter_SosInfoList(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    public long getMinId() {
        return mData.get(getCount() - 1).getId();
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    public void add(List<Entity_Location> data, boolean append) {
        if (!append) {
            mData.clear();
        }
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Entity_Location getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_sos_list, parent, false);
        }

        Entity_Location info = getItem(position);

        TextView ivSos = (TextView) convertView.findViewById(R.id.iv_item_sos);
        int status = info.getIsDeal();
        if (status > 1) {
            //未解决
            ivSos.setText(Constants.INFO_UNDONE);
            ivSos.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.round_bg_red));
        } else {
            ivSos.setText(Constants.INFO_DONE);
            ivSos.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.round_bg_green));

        }


//        // 根据id拼接的头像url，不取社区头像地址 (废弃 采用Userimage)
//        try
//        {
//            String str = AvatarUrlUtils.getSmallAvatarUrl(comment.getUserId());
//            ImageUtils.displayAvatar(AvatarUrlUtils.getSmallAvatarUrl(comment.getUserId()),
//                    avatarView);
//        } catch (Exception e)
//        {
//            // TODO: handle exception
//        }
        // ImageUtils.displayAvatar(AvatarUrlUtils.getSmallAvatarUrl(comment.getUserImage()),
        // avatarView);

//        ImageUtils.displayAvatar(info.getUserImage(),avatarView);

        TextView tvAdd = (TextView) convertView.findViewById(R.id.tv_item_sos_add);
        tvAdd.setText(info.getAdd());
        TextView tvName = (TextView) convertView.findViewById(R.id.tv_item_sos_name);
        tvName.setText(info.getName());
        TextView tvTime = (TextView) convertView.findViewById(R.id.tv_item_sos_time);
        tvTime.setText(info.getTime());
        return convertView;
    }

}

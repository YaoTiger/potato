package com.seable.potato.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.seable.potato.R;
import com.seable.potato.ui.activity.Activity_Album;
import com.seable.potato.util.addImage.ImageManager2;

import java.util.ArrayList;

/**
 * @author 王维玉
 * @ClassName: AlbumGridViewAdapter
 * @Description: 相册拍照选择页gridview的适配
 * @date 2015-01-17 13:13
 */
@SuppressLint("ResourceAsColor")
public class Adapter_Album_GridView extends BaseAdapter implements
        OnClickListener {

    private Context mContext;
    private ArrayList<String> dataList;
    private ArrayList<String> selectedDataList;
    private DisplayMetrics dm;

    public Adapter_Album_GridView(Context c, ArrayList<String> dataList,
                                  ArrayList<String> selectedDataList) {

        mContext = c;
        this.dataList = dataList;
        this.selectedDataList = selectedDataList;
        dm = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay()
                .getMetrics(dm);

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

    /**
     * 存放列表项控件句柄
     */
    private class ViewHolder {
        public ImageView imageView, check;
        public ToggleButton toggleButton;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (position == 0) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.choose_camera, parent, false);
            LinearLayout camera_ll = (LinearLayout) convertView.findViewById(R.id.camera_ll);
            camera_ll.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    ((Activity) mContext).startActivityForResult(intent,
                            Activity_Album.IMAGE_CODE_TAKING);

                }
            });
        } else {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.choose_select_imageview, parent, false);
            viewHolder.imageView = (ImageView) convertView
                    .findViewById(R.id.image_view);
            viewHolder.toggleButton = (ToggleButton) convertView
                    .findViewById(R.id.toggle_button);
            viewHolder.check = (ImageView) convertView.findViewById(R.id.check);
            convertView.setTag(viewHolder);
            String path;
            if (dataList != null && dataList.size() > position) {
                path = dataList.get(position);
            } else {
                path = "camera_default";
            }

            if (path.contains("picture")) {
                // "take_picture" in the first place
                viewHolder.imageView.setImageResource(R.drawable.icon_camera);
            } else if (path.contains("default")) {
                // "camera_default" before loading completed
                viewHolder.imageView.setImageResource(R.drawable.icon_picturer);
            } else {
                ImageManager2.from(mContext).displayImage(viewHolder.imageView,
                        path, R.drawable.icon_picturer, 200, 200);//R.drawable.icon_picture   100
            }
            viewHolder.toggleButton.setTag(position);
            viewHolder.toggleButton.setOnClickListener(this);
            if (isInSelectedDataList(path)) {
                viewHolder.toggleButton.setChecked(true);
                viewHolder.toggleButton.setBackgroundColor(Color.TRANSPARENT);
                viewHolder.check.setVisibility(View.VISIBLE);
            } else {
                viewHolder.toggleButton.setChecked(false);
                viewHolder.toggleButton.setBackgroundColor(Color.TRANSPARENT);
                viewHolder.check.setVisibility(View.GONE);
            }
        }
        return convertView;
    }

    private boolean isInSelectedDataList(String selectedString) {
        for (int i = 0; i < selectedDataList.size(); i++) {
            if (selectedDataList.get(i).equals(selectedString)) {
                return true;
            }
        }
        return false;
    }

    public int dipToPx(int dip) {
        return (int) (dip * dm.density + 0.5f);
    }

    @Override
    public void onClick(View view) {
        if (view instanceof ToggleButton) {
            ToggleButton toggleButton = (ToggleButton) view;
            int position = (Integer) toggleButton.getTag();
            if (dataList != null && mOnItemClickListener != null
                    && position < dataList.size()) {
                mOnItemClickListener.onItemClick(toggleButton, position,
                        dataList.get(position), toggleButton.isChecked());
            }
        }
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener l) {
        mOnItemClickListener = l;
    }

    public interface OnItemClickListener {
        public void onItemClick(ToggleButton view, int position, String path,
                                boolean isChecked);
    }

}

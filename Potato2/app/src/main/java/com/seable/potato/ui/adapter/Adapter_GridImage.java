package com.seable.potato.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.seable.potato.R;
import com.seable.potato.util.addImage.ImageManager2;

import java.util.ArrayList;


/**
 * @author 王维玉
 * @ClassName: GridImageAdapter
 * @Description: 添加照片gridview的适配 - 前照片最后加号
 * @date 2015-01-17 13:13
 */
public class Adapter_GridImage extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> dataList;
    private DisplayMetrics dm;

    public Adapter_GridImage(Context c, ArrayList<String> dataList) {

        mContext = c;
        this.dataList = dataList;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
//			imageView.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, dipToPx(80)));
            imageView.setLayoutParams(new GridView.LayoutParams(114, 114));
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        } else
            imageView = (ImageView) convertView;
        String path;
        if (dataList != null && position < dataList.size())
            path = dataList.get(position);
        else
            path = "camera_default";
        Log.i("path", "path:" + path + "::position" + position);
        if (path.contains("default") && (dataList.size() == 1))//如果选择�?��
            imageView.setImageResource(R.drawable.add_repair_background);
        else {
            ImageManager2.from(mContext).displayImage(imageView, path, R.drawable.add_repair_background, 100, 100);
        }
        return imageView;
    }

    public int dipToPx(int dip) {
        return (int) (dip * dm.density + 0.5f);
    }

}

package com.seable.potato.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.Arrays;
import java.util.List;

/**
 * @author 王维玉
 * @ClassName: Adapter_PopListData
 * @Description: 登录页 - 历史登陆账号列表的适配
 * @date 2015-03-20 13:13
 */
public class Adapter_PopListData<T> extends BaseAdapter implements Filterable {

    private IGetView mGetView;
    private List<T> mListData;

    public static <T> Adapter_PopListData<T> createAdapter(T[] data, IGetView getView) {
        Adapter_PopListData<T> adapter = new Adapter_PopListData<T>();
        adapter.setData(Arrays.asList(data));
        adapter.setGetView(getView);
        return adapter;
    }

    public static <T> Adapter_PopListData<T> createAdapter(List<T> data, IGetView getView) {
        Adapter_PopListData<T> adapter = new Adapter_PopListData<T>();
        adapter.setData(data);
        adapter.setGetView(getView);
        return adapter;
    }

    @Override
    public int getCount() {
        if (mListData != null) {
            return mListData.size();
        }
        return 0;
    }

    @Override
    public T getItem(int position) {
        if (mListData != null) {
            return mListData.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (mGetView == null) {
            throw new IllegalStateException("Must set a valid IGetView for " + getClass().getName());
        }
        return mGetView.getView(position, view, parent);
    }

    public void setData(T[] data) {
        setData(Arrays.asList(data));
    }

    public void setData(List<T> data) {
        setData(data, true);
    }

    public void setData(List<T> data, boolean notify) {
        mListData = data;
        if (notify) {
            notifyDataSetChanged();
        }
    }

    public void setGetView(IGetView getView) {
        if (getView == null) {
            throw new IllegalStateException("Must set a valid IGetView for " + getClass().getName());
        }
        mGetView = getView;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}

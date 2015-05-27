package com.seable.potato.ui.view;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;


/**
 * @author 王维玉
 * @ClassName: NoScrollListView
 * @Description: 不滚动的ListView，嵌套scrollview时使用
 * @date 2015-01-20 9:00
 */
public class View_NoScrollListView extends ListView {

    public View_NoScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }
}
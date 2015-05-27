package com.seable.potato.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * @author 王维玉
 * @ClassName: MyScrollView
 * @Description: 解决ScrollView嵌套ViewPager出现的滑动冲突问题
 * @date 2015-01-20 9:00
 */
public class View_ScrollView extends ScrollView {
    OnTouchListener mGestureListener;
    // 滑动距离及坐标
    private float xDistance, yDistance, xLast, yLast;

    public View_ScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();

                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;

                if (xDistance > yDistance) {
                    return false;
                }
        }

        return super.onInterceptTouchEvent(ev);
    }
}

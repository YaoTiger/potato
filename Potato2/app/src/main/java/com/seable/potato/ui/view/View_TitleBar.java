package com.seable.potato.ui.view;


import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.seable.potato.R;

/**
 * @author 王维玉修改
 * @ClassName: TitleBar
 * @Description: 标题
 * @date 2015-01-19 17:27
 */
public class View_TitleBar extends LinearLayout {

    private TextView titleName_tv;
    private ImageView back_im;
    private ImageView setting_im;
    private Context context;
    private FragmentActivity activity;
    private FragmentManager fragmentManager;
    private String name;

    public View_TitleBar(Context context) {
        super(context);
    }


    public View_TitleBar(final Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        try {
            this.activity = (FragmentActivity) context;
            this.fragmentManager = activity.getSupportFragmentManager();
        } catch (Exception e) {
            this.activity = null;
        }

        LayoutInflater.from(context)
                .inflate(R.layout.titlebar_view, this, true);
        titleName_tv = (TextView) findViewById(R.id.title_name);
        back_im = (ImageView) findViewById(R.id.back);
        setting_im = (ImageView) findViewById(R.id.setting);
        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.TitleView);
        name = array.getString(R.styleable.TitleView_titleName);
        int backResId = array.getResourceId(R.styleable.TitleView_backImage,
                R.drawable.back_selector);
        int settingResId = array
                .getResourceId(R.styleable.TitleView_settingImage,
                        R.drawable.setting_selector);
        array.recycle();

        titleName_tv.setText(name);

        back_im.setImageResource(backResId);
        setting_im.setImageResource(settingResId);
        // back_im.setOnClickListener(this);
        // setting_im.setOnClickListener(this);
        back_im.setOnClickListener(back);
    }

    public void setTitleName(String titleName) {
        titleName_tv.setText(titleName);
    }

    public void setBackImage(int resId) {
        back_im.setImageResource(resId);
    }

    public void setSettingImage(int resId) {
        setting_im.setImageResource(resId);
    }


    // @Override
    // public void onClick(View v) {
    // switch (v.getId()) {
    // case R.id.back:
    //
    // if (activity == null) {
    // ((Activity) context).finish();
    // } else {
    // if (!fragmentManager.popBackStackImmediate()) {
    // activity.finish();
    // }
    // }
    // break;
    // case R.id.setting:
    // ActivityJump.NormalJump(context, Activity_Sos_Setting.class);
    // break;
    // case R.id.writing:
    // ActivityJump.NormalJump(context, Activity_Neighbour_Write.class);
    // break;
    //
    // default:
    // break;
    // }
    // }

    public void setBackImshow(boolean isVisible) {

        if (isVisible) {
            back_im.setVisibility(VISIBLE);
        } else {
            back_im.setVisibility(GONE);
        }
    }

    public void setBackImClick(boolean isClickable) {

        if (isClickable) {
            back_im.setOnClickListener(back);
        } else {
            back_im.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    public void setBackImClickListener(OnClickListener l) {

        back_im.setOnClickListener(l);
    }


    OnClickListener back = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (activity == null) {
                ((Activity) context).finish();
            } else {
                if (!fragmentManager.popBackStackImmediate()) {
                    activity.finish();
                }
            }

        }
    };

    public void setSettingImshow(boolean isVisible) {
        if (isVisible) {
            setting_im.setVisibility(VISIBLE);
        } else {
            setting_im.setVisibility(GONE);
        }

    }

    public void setSettingImAction(OnClickListener l) {

        setting_im.setOnClickListener(l);
    }

}

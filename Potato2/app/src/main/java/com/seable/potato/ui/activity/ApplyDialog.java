package com.seable.potato.ui.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.seable.potato.R;
import com.seable.potato.biz.LActivity;
import com.seable.potato.biz.LApplication;
import com.seable.potato.ui.activity.taskcenter.Activity_Handle_TestRecord;

/**
 * 申请职位的dialog
 *
 * @author tiger
 */
public class ApplyDialog extends Dialog implements
        View.OnClickListener, LApplication.OnLocationChangeListener {


    Activity_Handle_TestRecord activity;
    private TextView tv1, tv2, tv3, tv4;
    private Button btn1, btn2, btn3, btn4, apply, cancel;
    private int currentIndex = -1;
    private double latln[] = new double[6];
    private double areWith, areLong, areNum;


    public ApplyDialog(Activity_Handle_TestRecord context) {
        super(context, R.style.DialogStyle);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(lp);
        this.activity = context;
        activity.mLApplication.setOnLocationChanger(this);
        activity.mLApplication.startLocation();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply_dialog);
        initView();

        setCanceledOnTouchOutside(false);
    }

    private void initView() {

        tv1 = (TextView) findViewById(R.id.first);
        tv2 = (TextView) findViewById(R.id.second);
        tv3 = (TextView) findViewById(R.id.end);
        tv4 = (TextView) findViewById(R.id.area);
        btn1 = (Button) findViewById(R.id.firstbegin);
        btn2 = (Button) findViewById(R.id.secondbegin);
        btn3 = (Button) findViewById(R.id.endbegin);
        btn4 = (Button) findViewById(R.id.areabegin);
        apply = (Button) findViewById(R.id.apply);
        cancel = (Button) findViewById(R.id.cancel_apply);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        apply.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_apply:
                dismiss();
                break;
            case R.id.apply:
                applyJob();
                break;
            case R.id.firstbegin:
                isSecond = 0;
                currentIndex = 1;
                activity.mLApplication.startLocation();
                break;
            case R.id.secondbegin:
                isSecond = 0;
                currentIndex = 2;
                activity.mLApplication.startLocation();
                break;
            case R.id.endbegin:
                isSecond = 0;
                currentIndex = 3;
                activity.mLApplication.startLocation();
                break;
            case R.id.areabegin:

                areWith = getDistanceFromXtoY(latln[0], latln[1], latln[2], latln[3]);
                areLong = getDistanceFromXtoY(latln[2], latln[3], latln[4], latln[5]);

                areNum = areLong * areWith;
                tv4.setText("面积为：" + areNum);
                break;
            default:
                break;
        }

    }

    public int getUnitValue(int value) {
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                value, getContext().getResources().getDisplayMetrics());
        return size;
    }

    private void applyJob() {
        activity.getAreNum(areNum);
        dismiss();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    int isSecond = 0;

    @Override
    public void onLocationChange(BDLocation location) {
        if (location != null) {

            if (location.getLatitude() > 0 && location.getLongitude() > 0) {
                Log.i("ddd", location.toString());

                activity.mLApplication.stopLocation();
                if (currentIndex == 1) {

                    latln[0] = location.getLatitude();
                    latln[1] = location.getLongitude();
                    tv1.setText("起点位置：定位成功");
                } else if (currentIndex == 2) {
                    latln[2] = location.getLatitude();
                    latln[3] = location.getLongitude();
                    tv2.setText("转折点位置：定位成功");
                } else if (currentIndex == 3) {
                    latln[4] = location.getLatitude();
                    latln[5] = location.getLongitude();
                    tv3.setText("终点位置：定位成功");
                }
                Log.i("dddd", location.getLatitude() + "????" + location.getLongitude());
//                if(isSecond==1){
//                    activity.mLApplication.stopLocation();
//                }
//                isSecond=isSecond+1;
            }

        }
    }

    public double getDistanceFromXtoY(double lat_a, double lng_a,
                                      double lat_b, double lng_b) {
        double pk = (double) (180 / 3.14169);

        double a1 = lat_a / pk;
        double a2 = lng_a / pk;
        double b1 = lat_b / pk;
        double b2 = lng_b / pk;

        double t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2);
        double t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2);
        double t3 = Math.sin(a1) * Math.sin(b1);
        double tt = Math.acos(t1 + t2 + t3);


        return 6366000 * tt;
    }
}

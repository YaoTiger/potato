package com.seable.potato.ui.activity.my;


import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.seable.potato.R;
import com.seable.potato.biz.LActivity;
import com.seable.potato.biz.T;
import com.seable.potato.data.entity.Entity_ProviderCompany;
import com.seable.potato.data.entity.Entity_Update;
import com.seable.potato.util.DeviceUtils;
import com.seable.potato.util.PhoneUtil;
import com.seable.potato.util.dialog.Effectstype;

/**
 * @author 王维玉
 * @ClassName: Activity_Info
 * @Description: 软件及服务商信息
 * @date 2015-01-20 15:23
 */
public class Activity_Info extends LActivity implements OnClickListener {

    private TextView tvCompany, tvTel, tvWeb, tvSummary, tvEmail, tvAddress, tvAppName, tvAppVersion, tvUpdate;

    private Entity_ProviderCompany infoCompany;
    private Entity_Update infoUpdate;
    private Effectstype effect;
    private static double PastVersion;
//	private static double NowVersion;
//	private static String apkUrl;
    /**
     * 下载的进度条
     */
    private ProgressBar pBar;
    /**
     * 下载的进度条
     */
    private TextView rateTv;

    /**
     * 业务逻辑
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        initView();
    }

    private void initView() {
        tvCompany = (TextView) findViewById(R.id.tv_my_setting_about_company);
        tvTel = (TextView) findViewById(R.id.tv_my_setting_about_tel);
        tvWeb = (TextView) findViewById(R.id.tv_my_setting_about_web);
        tvSummary = (TextView) findViewById(R.id.tv_my_setting_about_summary);
        tvEmail = (TextView) findViewById(R.id.tv_my_setting_about_email);
        tvAddress = (TextView) findViewById(R.id.tv_my_setting_about_address);
        tvAppName = (TextView) findViewById(R.id.tv_my_setting_about_appname);
        tvAppVersion = (TextView) findViewById(R.id.tv_my_setting_about_appversion);
        tvUpdate = (TextView) findViewById(R.id.tv_my_setting_about_update);
        tvTel.setOnClickListener(this);
        tvEmail.setOnClickListener(this);
        tvUpdate.setOnClickListener(this);
        tvAppName.setText(mContext.getString(R.string.app_name));
        tvAppVersion.setText(DeviceUtils.getVersionName(mContext) + "");
        infoCompany = new Entity_ProviderCompany();
        tvCompany.setText("哈尔滨兴业宝科技有限公司");
        tvAddress.setText("哈尔滨平房区松花路9号");
        tvSummary.setText("简介");
        tvTel.setText("0451-88888888");
        tvWeb.setText("www.baidu.com");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_my_setting_about_tel:
                PhoneUtil.makeCall(mContext, tvTel.getText().toString());
                break;
            case R.id.tv_my_setting_about_email:
                PhoneUtil.sendEmail(mContext, tvEmail.getText().toString(), " ", " ");
                break;
            case R.id.tv_my_setting_about_update:
                T.ss("当前已是最新版本");
                break;

            default:
                break;
        }

    }

}

package com.seable.potato.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.seable.potato.biz.LActivity;
import com.seable.potato.biz.LApplication;
import com.seable.potato.biz.LApplication.OnLocationChangeListener;

public class CaculateDialog extends LActivity implements
        OnLocationChangeListener {
    private LApplication application;
    private LocationManager locationManager;
    private Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (LApplication) getApplication();
        application.setOnLocationChanger(this);
        application.initLocationOption();
        openGPSSettings();

    }

    private void openGPSSettings() {
        LocationManager alm = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);
        if (alm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "GPS模块正常", Toast.LENGTH_SHORT).show();
            init();
        } else {
            Toast.makeText(this, "请开启GPS！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
            startActivityForResult(intent, 0); // 此为设置完成后返回到获取界面
        }

    }

    @Override
    public void onLocationChange(BDLocation location) {
        Toast.makeText(application, location.getAddrStr(), Toast.LENGTH_SHORT)
                .show();
    }

    private void init() {
        // 获取系统LocationManager服务
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // // 从GPS获取最近的定位信息
        currentLocation = locationManager
                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
        // 绑定监听，有4个参数
        // 参数1，设备：有GPS_PROVIDER和NETWORK_PROVIDER两种
        // 参数2，位置信息更新周期，单位毫秒
        // 参数3，位置变化最小距离：当位置距离变化超过此值时，将更新位置信息
        // 参数4，监听
        // 备注：对于参数2和3：若参数3不为0，则以参数3为准；若参数3为0，则通过时间来定时更新；两者为0，则随时刷新
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000, 1, new LocationListener() {

                    @Override
                    public void onLocationChanged(Location location) {
                        Toast.makeText(
                                application,
                                location.getLongitude() + "location"
                                        + location.getLatitude(),
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {
                        Toast.makeText(
                                application,
                                locationManager.getLastKnownLocation(provider)
                                        .getLatitude() + "??",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onStatusChanged(String provider, int status,
                                                Bundle extras) {
                        Toast.makeText(
                                application,
                                locationManager.getLastKnownLocation(provider)
                                        .getLatitude() + "??" + status,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

package com.seable.potato.util;

public class DistanceUtils {
    // 计算两点距离

    private final double EARTH_RADIUS = 6378137.0;

    public double gps2m(double lat_a, double lng_a, double lat_b, double lng_b) {

        double radLat1 = (lat_a * Math.PI / 180.0);

        double radLat2 = (lat_b * Math.PI / 180.0);

        double a = radLat1 - radLat2;

        double b = (lng_a - lng_b) * Math.PI / 180.0;

        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)

                + Math.cos(radLat1) * Math.cos(radLat2)

                * Math.pow(Math.sin(b / 2), 2)));

        s = s * EARTH_RADIUS;

        s = Math.round(s * 10000) / 10000;

        return s;

    }

    // 计算方位角pab。

    public double gps2d(double lat_a, double lng_a, double lat_b, double lng_b) {

        double d = 0;

        lat_a = lat_a * Math.PI / 180;

        lng_a = lng_a * Math.PI / 180;

        lat_b = lat_b * Math.PI / 180;

        lng_b = lng_b * Math.PI / 180;

        d = Math.sin(lat_a) * Math.sin(lat_b) + Math.cos(lat_a)
                * Math.cos(lat_b) * Math.cos(lng_b - lng_a);

        d = Math.sqrt(1 - d * d);

        d = Math.cos(lat_b) * Math.sin(lng_b - lng_a) / d;

        d = Math.asin(d) * 180 / Math.PI;

        // d = Math.round(d*10000);

        return d;

    }
}

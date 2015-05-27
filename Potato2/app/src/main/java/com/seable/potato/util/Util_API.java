package com.seable.potato.util;

import android.os.Build;

public class Util_API {
    public static boolean isSupport(int apiNo) {
        return Build.VERSION.SDK_INT >= apiNo;
    }
}

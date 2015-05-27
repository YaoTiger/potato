package com.seable.potato.util.assets;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.seable.potato.common.MApplication;

import java.io.ByteArrayOutputStream;

public class BitmapUtil {

    public static BitmapDrawable BitmapToDrawable(Bitmap bitmap) {
        // Drawable drawable =new BitmapDrawable(bitmap);
        Resources res = MApplication.getInstance().getResources();
        return new BitmapDrawable(res, bitmap);
    }

    public static Bitmap DrawableToBitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * compressNum  压缩率 10 压缩剩多少
     */
    public static byte[] BitmapToBytes(Bitmap bitmap, int compressNum) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 压缩格式 Png 无用

        bitmap.compress(Bitmap.CompressFormat.JPEG, compressNum, baos);

        return baos.toByteArray();
    }

    private Bitmap BytesToBimap(byte[] b) {

        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }
}

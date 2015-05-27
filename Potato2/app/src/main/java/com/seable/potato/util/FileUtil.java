package com.seable.potato.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.widget.ImageView;

import com.seable.potato.R;
import com.seable.potato.biz.T;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * @author 王维玉
 * @ClassName: FileUtil
 * @Description: 文件管理工具类
 * @date 2015-01-17 13:32
 */
public class FileUtil {
    final static String FOLDER = "/property/";
    final static String SUFFIX = ".jpg";

    /**
     * 用户头像图像压缩并保存到本地 返回处理过的图片
     *
     * @param bit      要处理的图片
     * @param fileName 储存地址
     * @param iv       加载图片
     */
    public static String saveImage(Context mContext, Bitmap bit, ImageView iv, String name) {
//		Bitmap bit = ThumbnailUtils.extractThumbnail(bitmap, 105, 105);
        // 头像保存到本地
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            String fileName = Environment.getExternalStorageDirectory()
                    .getAbsolutePath()
                    + FOLDER
                    + name
                    + SUFFIX;
            if (bit != null && iv != null) {
                iv.setImageBitmap(bit);
            }
            File file = new File(fileName);
            String foldername = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + FOLDER;
            File folder = new File(foldername);
            if (folder != null && !folder.exists()) {
                if (!folder.mkdir() && !folder.isDirectory()) {
                    System.out.println("Error: make dir failed!");
                    return null;
                }
            }
            try {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bit.compress(CompressFormat.JPEG, 100, stream);
                // 70 是压缩率，表示压缩30%; 如果不压缩是100，表示压缩率为0
                FileOutputStream os = new FileOutputStream(file);
                os.write(stream.toByteArray());
                os.close();
                T.sl(mContext.getResources()
                        .getString(R.string.login_upload_picture) + fileName);
                return fileName;
            } catch (Exception e) {
                file = null;
                T.ss(mContext.getResources()
                        .getString(R.string.login_file_wrong) + e.toString());
                return null;
            }
        } else {
            T.ss(mContext.getResources().getString(
                    R.string.login_sdcard_wrong));
            return null;
        }
    }

    public static String getImagePath(String name) {
        String fileName = Environment.getExternalStorageDirectory()
                .getAbsolutePath()
                + FOLDER
                + name
                + SUFFIX;
        return fileName;
    }


}

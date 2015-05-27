package com.seable.potato.ui.activity.taskcenter;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.text.TextUtils;

/**
 * �ļ�����Ĺ�����
 *
 * @author limin
 */
@SuppressLint("NewApi")
public class FileManagementUtil {

    /**
     * Ĭ�ϱ����ļ���ַ.
     */
    private static String downPathRootDir = File.separator + "ltlcommunity"
            + File.separator;
    /**
     * Ĭ������ͼƬ�ļ���ַ.
     */
    private static String downPathImageDir = downPathRootDir + "cache_images"
            + File.separator;
    /**
     * Ĭ�ϱ���ͼƬ�ļ���ַ.
     */
    private static String savePathImageDir = downPathRootDir + "LTLsave_images"
            + File.separator;
    /**
     * Ĭ������ͼƬ�ļ���ַ.
     */
    private static String catchPathImageDir = downPathRootDir + "tmp_images"
            + File.separator;
    /**
     * Ĭ�ϸ�������app�洢��ַ
     */
    private static String appUpdateDownloadDir = downPathRootDir + "download"
            + File.separator;
    /**
     * Ĭ�������ļ�����·��
     */
    private static String savePathVoiceDir = downPathRootDir + "save_voice"
            + File.separator;

    private static String savePathDocDir = downPathRootDir + "save_doc"
            + File.separator;

    /**
     * ������SD���Ƿ�����.
     *
     * @return true ����,false������
     */
    public static boolean isCanUseSD() {
        try {
            return Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * ��������ȡĬ�ϵ�ͼƬ���ص�ȫ·��.
     *
     * @return the default image down path dir
     */
    public static String getDefaultImageDownPathDir() {
        return getFilePath(downPathImageDir);
    }

    /**
     * ��������ȡĬ�ϵ�ͼƬ���ص��ļ�
     *
     * @return the default image down file
     */
    public static File getDefaultImageDownFile() {
        return getFile(downPathImageDir);
    }

    /**
     * ��������ȡĬ�ϵ�ͼƬ����ȫ·��.
     *
     * @return the default image catch path dir
     */
    public static String getDefaultImageCatchPathDir() {
        return getFilePath(catchPathImageDir);
    }

    /**
     * ��������ȡĬ�ϵ�ͼƬ�����ļ�
     *
     * @return the default image catch path dir
     */
    public static File getDefaultImageCatchFile() {
        return getFile(catchPathImageDir);
    }

    /**
     * ��������ȡĬ�ϵ�ͼƬ����ȫ·��.
     *
     * @return the default image save path dir
     */
    public static String getDefaultImageSavePathDir() {
        return getFilePath(savePathImageDir);
    }

    /**
     * ��������ȡĬ�ϵ�app���ظ��±���·��
     *
     * @return the default app down path dir
     */
    public static String getAppUpdateDownloadPathDir() {
        return getFilePath(appUpdateDownloadDir);
    }

    /**
     * ��������ȡĬ�ϵ�app���ظ�����������·�� ��������ȡ��Ƶ���ر���·��
     *
     * @return the default voice down path dir
     */
    public static String getDefaultVoiceSavePathDir() {
        return getFilePath(savePathVoiceDir);
    }

    /**
     * ��������ȡĬ�ϵ�app���ظ������������ļ�
     *
     * @return the default voice down file
     */
    public static File getDefaultVoiceSaveFile() {
        return getFile(savePathVoiceDir);
    }

    /**
     * �����õ���urlת��ΪSD����ͼƬ·��
     */
    public static String[] getPathFromUrl(Uri uri, Activity activity) {
        String path[] = new String[2];
        if (TextUtils.isEmpty(uri.getAuthority())) {
            if (!TextUtils.isEmpty(uri.getPath())) {
                return new String[]{uri.getPath()};
            }
            return null;
        }

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        if (isKitKat) {
            path = getPath(activity, uri);
            if (path == null || TextUtils.isEmpty(path[0])) {
                path = new String[2];
                path[0] = getRealFilePath(activity, uri);
            }
        } else {
            path[0] = getRealFilePath(activity, uri);
        }
        return path;
    }

    private static String[] getPath(final Context context, final Uri uri) {

        if (DocumentsContract.isDocumentUri(context, uri)) {

            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return new String[]{Environment
                            .getExternalStorageDirectory() + "/" + split[1]};
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {

                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String[] getDataColumn(Context context, Uri uri,
                                         String selection, String[] selectionArgs) {
        String[] datas = null;
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column,
                ImageColumns.SIZE};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                final int sizeindex = cursor.getColumnIndex(projection[1]);
                datas = new String[]{cursor.getString(index),
                        cursor.getString(sizeindex)};
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return datas;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    /**
     * Try to return the absolute file path from the given Uri
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    private static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri)
            return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[]{ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * ��������ȡĬ�ϵ�ͼƬ������ļ�
     *
     * @return the default image save file
     */
    public static File getDefaultImageSaveFile() {
        return getFile(savePathImageDir);
    }

    /**
     * ��������ȡĬ�ϵ�ͼƬ������ļ�
     *
     * @return the default image save file
     */
    public static File getDefaultPdfSaveFile() {
        return getFile(savePathDocDir);
    }

    /**
     * �ж��ļ��Ƿ���ڣ��������򴴽�
     *
     * @param path
     * @return ����·��
     */
    private static String getFilePath(String path) {

        String pathDir = null;
        try {
            if (!isCanUseSD()) {
                return null;
            }
            // ��ʼ��ͼƬ����·��
            File fileRoot = Environment.getExternalStorageDirectory();
            File dirFile = new File(fileRoot.getAbsolutePath() + path);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            pathDir = dirFile.getPath();
        } catch (Exception e) {
        }
        return pathDir;
    }

    /**
     * �ж��ļ��Ƿ���ڣ��������򴴽�
     *
     * @param path
     * @return �����ļ�
     */
    private static File getFile(String path) {

        File file = null;
        try {
            if (!isCanUseSD()) {
                return null;
            }
            // ��ʼ��ͼƬ����·��
            File fileRoot = Environment.getExternalStorageDirectory();
            File dirFile = new File(fileRoot.getAbsolutePath() + path);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            file = dirFile;
        } catch (Exception e) {
        }
        return file;
    }

}

package com.seable.potato.util.assets;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.seable.potato.R;
import com.seable.potato.biz.L;

import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author 王维玉
 * @ClassName: ImageUtils
 * @Description: 图片处理相关工具类
 * @date 2015-01-20 15:08
 */
public class ImageUtils {

    private static ImageUtils mInstantce;
    static Context mContext;

    public ImageUtils() {
        sImageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
    }

    /**
     * 分享浮窗初始�?
     */
    public static ImageUtils getInstance(Context contexts) {
        mContext = contexts;
        if (mInstantce == null) {
            mInstantce = new ImageUtils();
        }
        return mInstantce;
    }

    static ImageLoader sImageLoader = ImageLoader.getInstance();

    // static boolean isCurrentYidongApn =
    // !Chmobileutil.iswifi(SchoolApp.getInstance().getApplicationContext())&&
    // ApnUtility.getinstance(SchoolApp.getInstance().getApplicationContext())
    // .IsCurrentYidongApn();


    public final static DisplayImageOptions DISYPLAY_OPTION_AVATAR = new DisplayImageOptions.Builder()
            .showStubImage(R.drawable.avatar)//avatar_0
            .showImageForEmptyUri(R.drawable.avatar)
            .showImageOnFail(R.drawable.avatar)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .displayer(new RoundedBitmapDisplayer(10)).build();

    public final static DisplayImageOptions DISYPLAY_OPTION_WEB_IMAGE = new DisplayImageOptions.Builder()
            .cacheInMemory()
            .cacheOnDisc().bitmapConfig(Bitmap.Config.RGB_565).build();

    public final static DisplayImageOptions DISYPLAY_OPTION_CACHEINMEM = new DisplayImageOptions.Builder()
            .showStubImage(R.drawable.avatar)//avatar_0
            .showImageForEmptyUri(R.drawable.avatar)
            .showImageOnFail(R.drawable.avatar).cacheInMemory()
            .bitmapConfig(Bitmap.Config.RGB_565).build();

    public final static DisplayImageOptions DISYPLAY_OPTION_ALBUM = new DisplayImageOptions.Builder()
            .showStubImage(R.color.transparent)
            .showImageForEmptyUri(R.color.transparent)
            .showImageOnFail(R.color.transparent).cacheInMemory()
            .bitmapConfig(Bitmap.Config.RGB_565).build();

    public static void displayAlbum(String url, ImageView imageView) {
        sImageLoader.displayImage(url, imageView, DISYPLAY_OPTION_ALBUM);
    }

    public static void displayAvatar(String url, ImageView imageView) {
        sImageLoader.displayImage(url, imageView, DISYPLAY_OPTION_AVATAR);
    }

    public static void cancelDisplay(ImageView imageView) {
        sImageLoader.cancelDisplayTask(imageView);
    }


    public static void displayWebImage(String url, ImageView imageView) {
        sImageLoader.displayImage(url, imageView, DISYPLAY_OPTION_WEB_IMAGE);
    }

    public static void displayCacheInMemImage(String url, ImageView imageView) {
        sImageLoader.displayImage(url, imageView, DISYPLAY_OPTION_CACHEINMEM);
    }

    public String getImagePathFromProvider(Context context, Uri uri) {
        if (!uri.getScheme().equals("content"))
            return uri.getPath();
        String[] projection = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection,
                null, null, null);
        if (cursor == null)
            return null;
        int rowNums = cursor.getCount();
        if (rowNums == 0) {
            return null;
        }
        cursor.moveToFirst();
        String filePath = cursor.getString(0);
        cursor.close();
        return filePath;
    }

    public Bitmap scaleImage(String imagePath, int requestWidth,
                             int requestHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);

        options.inSampleSize = calculateInSampleSize(options, requestWidth,
                requestHeight);

        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);

        String orientation = getExifOrientation(imagePath, "0");

        Matrix matrix = new Matrix();
        matrix.postRotate(Float.valueOf(orientation));

        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, false);

        return newBitmap;
    }

    public int calculateInSampleSize(BitmapFactory.Options options,
                                     int reqW, int reqH) {
        final int h = options.outHeight;
        final int w = options.outWidth;
        int inSampleSize = 1;

        if (h > reqH || w > reqW) {
            final int heightRatio = Math.round((float) h / (float) reqH);
            final int widthRatio = Math.round((float) w / (float) reqW);

            inSampleSize = Math.min(heightRatio, widthRatio);
        }

        return inSampleSize;
    }

    public String getExifOrientation(String path, String orientation) {
        // get image EXIF orientation if Android 2.0 or higher, using reflection
        // http://developer.android.com/resources/articles/backward-compatibility.html
        Method exif_getAttribute;
        Constructor<ExifInterface> exif_construct;
        String exifOrientation = "";

        int sdk_int = 0;
        try {
            sdk_int = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (Exception e1) {
            sdk_int = 3; // assume they are on cupcake
        }
        if (sdk_int >= 5) {
            try {
                exif_construct = ExifInterface.class
                        .getConstructor(new Class[]{String.class});
                Object exif = exif_construct.newInstance(path);
                exif_getAttribute = ExifInterface.class
                        .getMethod("getAttribute", new Class[]{String.class});
                try {
                    exifOrientation = (String) exif_getAttribute.invoke(exif,
                            ExifInterface.TAG_ORIENTATION);
                    if (exifOrientation != null) {
                        if (exifOrientation.equals("1")) {
                            orientation = "0";
                        } else if (exifOrientation.equals("3")) {
                            orientation = "180";
                        } else if (exifOrientation.equals("6")) {
                            orientation = "90";
                        } else if (exifOrientation.equals("8")) {
                            orientation = "270";
                        }
                    } else {
                        orientation = "0";
                    }
                } catch (InvocationTargetException ite) {
                    /* unpack original exception when possible */
                    orientation = "0";
                } catch (IllegalAccessException ie) {
                    System.err.println("unexpected " + ie);
                    orientation = "0";
                }
                /* success, this is a newer device */
            } catch (NoSuchMethodException nsme) {
                orientation = "0";
            } catch (IllegalArgumentException e) {
                orientation = "0";
            } catch (InstantiationException e) {
                orientation = "0";
            } catch (IllegalAccessException e) {
                orientation = "0";
            } catch (InvocationTargetException e) {
                orientation = "0";
            }

        }
        return orientation;
    }

    public byte[] processUploadImage(String filePath, int maxSize) {
        Bitmap bitmap = scaleImage(filePath, maxSize, maxSize);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 100, out);
        bitmap.recycle();
        return out.toByteArray();
    }

    public String getWebImage(String imageurl) {
        String url = imageurl;

        int dotIndex = imageurl.lastIndexOf('.');
        String fileExtension = "";
        fileExtension = imageurl.substring(dotIndex + 1);

        if (fileExtension.equals("thumbnail")) {
            url = imageurl.substring(0, dotIndex + 1) + "web";
        }
        return url;
    }

    public void savePictoFile(Uri url, String filePath)
            throws IOException {
        String originalfilepath = url.getPath();
        Bitmap photo = scaleImage(originalfilepath, 400, 400);
        if (photo != null) {
            File file = new File(filePath);
            FileOutputStream outstream = new FileOutputStream(file);
            if (photo.compress(CompressFormat.JPEG, 100, outstream)) {
                outstream.flush();
                outstream.close();
            }
        }
    }

    public Bitmap loadBitmapFromPathLimitSiding(String path, int limitWidth, int limitHeight) {
        Bitmap bitmap = null;
        if (path != null) {
            File file = new File(path);
            if (!file.exists()) {
                L.e("ImageUtils", "图片文件不存在");
                return null;
            } else if (file.length() > 8 * 1024 * 1024) {
                L.e("getPicFromPath", "图片太大");
                return null;
            }
            FileInputStream fileinputstream = null;
            try {
                fileinputstream = new FileInputStream(file);
                FileDescriptor filedescriptor = fileinputstream.getFD();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPurgeable = true;
                options.inInputShareable = true;
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFileDescriptor(filedescriptor, null, options);
                //计算压缩比
                int be = 1;
                while (options.outWidth > limitWidth) {
                    be *= 2;
                    limitWidth *= 2;
                }
                limitHeight = limitHeight * be;
                while (options.outHeight > limitHeight) {
                    be *= 2;
                    limitHeight *= 2;
                }
                L.e("loadBitmapFromPathLimitSiding", "比例为" + be);
                options.inSampleSize = be;
                options.inJustDecodeBounds = false;
                // 重新读入图片，注意这次要把options.inJustDecodeBounds设为false
                bitmap = BitmapFactory.decodeFileDescriptor(filedescriptor, null, options);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fileinputstream != null) {
                        fileinputstream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return standImg(path, bitmap);
    }

    //解决三星等手机拍照 照片不朝上问题
    public Bitmap standImg(String bitmapUrl, Bitmap bitmap) {
        Bitmap resultBitmap = null;
        ExifInterface exifInterface;
        try {
            exifInterface = new ExifInterface(bitmapUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return bitmap;
        }
        int orc = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
        int degree = 0;
        if (orc == ExifInterface.ORIENTATION_ROTATE_90) {
            degree = 90;
        } else if (orc == ExifInterface.ORIENTATION_ROTATE_180) {
            degree = 180;
        } else if (orc == ExifInterface.ORIENTATION_ROTATE_270) {
            degree = 270;
        }

        L.e("standimg", "degree=" + degree);
        if (degree != 0 && bitmap != null) {
            Matrix mt = new Matrix();
            mt.setRotate(degree);
            try {
                resultBitmap = Bitmap.createBitmap(bitmap,
                        0,
                        0,
                        bitmap.getWidth(),
                        bitmap.getHeight(),
                        mt,
                        true);

            } catch (OutOfMemoryError ex) {
                ex.printStackTrace();
                return bitmap;
                // 如何出现了内存不足异常，最好return 原始的bitmap对象。.
            }
        }
        if (resultBitmap != null) {
            return resultBitmap;
        } else {
            return bitmap;
        }
    }
}

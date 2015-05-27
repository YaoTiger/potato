package com.seable.potato.util.assets;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

public class ImageLoderUtil {
    // 使用DisplayImageOptions.Builder()创建DisplayImageOptions
    public static DisplayImageOptions options = new DisplayImageOptions.Builder()
            //	.showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
            //	.showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
            //	.showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
            .postProcessor(new BitmapProcessor() {

                @Override
                public Bitmap process(Bitmap arg0) {
                    // TODO Auto-generated method stub
                    return null;
                }
            })
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
            .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
            .build(); // 创建配置过得DisplayImageOption对象

    // public static ImageLoaderConfiguration config = new
    // ImageLoaderConfiguration.Builder(context)
    // .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
    // .diskCacheExtraOptions(480, 800, CompressFormat.JPEG, 75, null)
    // .taskExecutor(...)
    // .taskExecutorForCachedImages(...)
    // .threadPoolSize(3) // default
    // .threadPriority(Thread.NORM_PRIORITY - 1) // default
    // .tasksProcessingOrder(QueueProcessingType.FIFO) // default
    // .denyCacheImageMultipleSizesInMemory()
    // .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
    // .memoryCacheSize(2 * 1024 * 1024)
    // .memoryCacheSizePercentage(13) // default
    // .diskCache(new UnlimitedDiscCache(cacheDir)) // default
    // .diskCacheSize(50 * 1024 * 1024)
    // .diskCacheFileCount(100)
    // .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
    // .imageDownloader(new BaseImageDownloader(context)) // default
    // .imageDecoder(new BaseImageDecoder()) // default
    // .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) //
    // default
    // .writeDebugLogs()
    // .build();
}

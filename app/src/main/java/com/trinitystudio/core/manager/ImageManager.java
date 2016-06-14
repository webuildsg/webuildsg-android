package com.trinitystudio.core.manager;

import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by liccowee on 7/30/15.
 */
public class ImageManager {
    private static ImageManager instance;
    private DisplayImageOptions options;

    public static ImageManager getInstance()
    {
        if(instance == null)
        {
            instance = new ImageManager();
        }
        return instance;
    }

    public static ImageLoader getLoader()
    {
        return ImageLoader.getInstance();
    }

    public ImageManager()
    {

    }

    public DisplayImageOptions getImageOptions()
    {
        options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).imageScaleType(ImageScaleType.EXACTLY).considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).showImageForEmptyUri(android.R.color.holo_red_light).showImageOnFail(android.R.color.holo_red_light).build();
        return options;
    }

    public void init(Context context)
    {
        //File cacheDir = StorageUtils.getCacheDirectory(context);
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.memoryCache(new WeakMemoryCache());
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        //config.writeDebugLogs();

        ImageLoader.getInstance().init(config.build());
    }
}

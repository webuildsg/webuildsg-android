package com.trinitystudio.core.manager;

import com.trinitystudio.core.GlobalConstant;
import com.vincentbrison.openlibraries.android.dualcache.lib.DualCache;
import com.vincentbrison.openlibraries.android.dualcache.lib.DualCacheBuilder;

/**
 * Created by liccowee on 2/17/16.
 */
public class CacheManager {
    private static CacheManager instance;
    private DualCache<String> mCache;

    public static CacheManager getInstance()
    {
        if(instance == null)
        {
            instance = new CacheManager();
        }
        return instance;
    }

    public CacheManager()
    {
        mCache = new DualCacheBuilder<String>(GlobalConstant.CACHE_NAME, GlobalConstant.CACHE_VERSION, String.class).useDefaultSerializerInRam(GlobalConstant.EXTRA_RAM_CACHE_SIZE).useDefaultSerializerInDisk(GlobalConstant.EXTRA_DISK_CACHE_SIZE, true);
        System.out.println(mCache.getRamSize() + " " + mCache.getDiskSize());
    }

    public DualCache<String> getCache()
    {
        return mCache;
    }
}

package com.trinitystudio.webuildsg;

import android.app.Application;

import com.android.volley.VolleyLog;
import com.trinitystudio.core.manager.ImageManager;
import com.trinitystudio.core.manager.RequestQueueManager;
import com.vincentbrison.openlibraries.android.dualcache.lib.DualCacheContextUtils;

/**
 * Created by liccowee on 4/18/16.
 */
public class MainApplication extends Application {

    public MainApplication() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        RequestQueueManager.getInstance().init(getCacheDir());

        DualCacheContextUtils.setContext(getApplicationContext());

        ImageManager.getInstance().init(getApplicationContext());

        VolleyLog.setTag("Volley");
    }
}

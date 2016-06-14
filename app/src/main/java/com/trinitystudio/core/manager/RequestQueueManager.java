package com.trinitystudio.core.manager;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

import java.io.File;

/**
 * Created by liccowee on 8/17/15.
 */
public class RequestQueueManager
{
    private static RequestQueueManager instance;
    private RequestQueue requestQueue;

    public static RequestQueueManager getInstance()
    {
        if(instance == null)
        {
            instance = new RequestQueueManager();
        }
        return instance;
    }

    public RequestQueueManager()
    {

    }

    public void init(File cacheDir)
    {
        Cache cache = new DiskBasedCache(cacheDir, 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());

        requestQueue = new RequestQueue(cache, network);

        requestQueue.start();
    }

    public RequestQueue getRequestQueue()
    {
        return requestQueue;
    }
}

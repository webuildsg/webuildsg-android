package com.trinitystudio.core.customtabs;

import android.support.customtabs.CustomTabsClient;

/**
 * Created by liccowee on 11/4/15.
 */
public interface ServiceConnectionCallback {
    /**
     * Called when the service is connected.
     * @param client a CustomTabsClient
     */
    void onServiceConnected(CustomTabsClient client);

    /**
     * Called when the service is disconnected.
     */
    void onServiceDisconnected();
}

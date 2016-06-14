package com.trinitystudio.core.net;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.StringRequest;
import com.trinitystudio.core.util.DeviceUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liccowee on 3/1/16.
 */
public class CustomObjectRequest extends StringRequest {

    private Context ctx;
    private RetryPolicy retryPolicy;
    public CustomObjectRequest(Context ctx, int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.ctx = ctx;
        retryPolicy = new DefaultRetryPolicy(16000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        setRetryPolicy(retryPolicy);
    }

    @Override
    public String getBodyContentType() {
        return "application/x-www-form-urlencoded; charset=" + getParamsEncoding();
    }

    @Override
    public RetryPolicy getRetryPolicy() {
        //return super.getRetryPolicy();
        return retryPolicy;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        final Map<String, String> params = new HashMap<>();
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)
        {
            params.put("udid", DeviceUtil.getDeviceId(ctx));
        }


        params.put("platform", DeviceUtil.getPlatform());
        params.put("device_type", DeviceUtil.getDeviceType(ctx));
        params.put("version", String.valueOf(DeviceUtil.getAppVersion(ctx)));
        params.put("os", String.valueOf(DeviceUtil.getOSVersion()));

        return params;
    }
}

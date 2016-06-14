package com.trinitystudio.core.net;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;

import java.util.Map;

/**
 * Created by liccowee on 3/1/16.
 */
public class CustomAuthObjectRequest extends CustomObjectRequest {

    private Context context;

    public CustomAuthObjectRequest(Context context, int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(context, method, url, listener, errorListener);
        this.context = context;
    }

    @Override
    public String getBodyContentType() {
        return "application/x-www-form-urlencoded; charset=" + getParamsEncoding();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return super.getHeaders();
    }
}

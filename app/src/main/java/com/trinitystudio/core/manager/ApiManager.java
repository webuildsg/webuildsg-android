package com.trinitystudio.core.manager;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.trinitystudio.core.listener.OnResponseListener;
import com.trinitystudio.core.net.CustomAuthObjectRequest;
import com.trinitystudio.core.net.CustomObjectRequest;

import java.util.Map;

/**
 * Created by liccowee on 3/7/16.
 */
public class ApiManager {
    private static ApiManager instance;

    public static ApiManager getInstance()
    {
        if(instance == null)
            instance = new ApiManager();

        return instance;
    }

    public CustomObjectRequest urlRequestWithParams(Context context, final String key, boolean loadFromCached, final boolean saveToCache, int method, String url, final Map<String, String>params, final OnResponseListener listener, final Response.ErrorListener errorListener)
    {
        if(loadFromCached)
        {
            String cached = CacheManager.getInstance().getCache().get(key);
            if(cached != null && cached.length() > 0) {
                listener.onResponse(cached, true);
            }
        }

        CustomObjectRequest request = new CustomObjectRequest(context, method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(saveToCache)
                    CacheManager.getInstance().getCache().put(key, response.toString());
                listener.onResponse(response, false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorListener.onErrorResponse(error);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> sParams = super.getParams();
                sParams.putAll(params);
                return sParams;
            }
        };
        RequestQueueManager.getInstance().getRequestQueue().add(request);

        return request;
    }

    public CustomObjectRequest urlRequestWithHeaderParams(Context context, final String key, boolean loadFromCached, final boolean saveToCache, int method, String url, final Map<String, String> headers, final Map<String, String>params, final OnResponseListener listener, final Response.ErrorListener errorListener)
    {
        if(loadFromCached)
        {
            String cached = CacheManager.getInstance().getCache().get(key);
            if(cached != null && cached.length() > 0) {
                listener.onResponse(cached, true);
            }
        }

        CustomObjectRequest request = new CustomObjectRequest(context, method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(saveToCache)
                    CacheManager.getInstance().getCache().put(key, response.toString());
                listener.onResponse(response, false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorListener.onErrorResponse(error);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> sParams = super.getParams();
                sParams.putAll(params);
                return sParams;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }
        };
        RequestQueueManager.getInstance().getRequestQueue().add(request);

        return request;
    }

    public CustomObjectRequest urlRequest(Context context, final String key, boolean loadFromCached, final boolean saveToCache, int method, String url, final OnResponseListener listener, final Response.ErrorListener errorListener)
    {
        if(loadFromCached)
        {
            String cached = CacheManager.getInstance().getCache().get(key);
            if(cached != null && cached.length() > 0) {
                listener.onResponse(cached, true);
            }
        }

        CustomObjectRequest request = new CustomObjectRequest(context, method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(saveToCache)
                    CacheManager.getInstance().getCache().put(key, response.toString());
                listener.onResponse(response, false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorListener.onErrorResponse(error);
            }
        });
        RequestQueueManager.getInstance().getRequestQueue().add(request);

        return request;
    }


    // Authentation request here!!!
    public CustomObjectRequest authUrlRequest(Context context, final String key, boolean loadFromCached, final boolean saveToCache, int method, String url, final OnResponseListener listener, final Response.ErrorListener errorListener)
    {
        if(loadFromCached)
        {
            String cached = CacheManager.getInstance().getCache().get(key);
            if(cached != null && cached.length() > 0) {
                listener.onResponse(cached, true);
            }
        }

        CustomAuthObjectRequest request = new CustomAuthObjectRequest(context, method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(saveToCache)
                    CacheManager.getInstance().getCache().put(key, response.toString());
                listener.onResponse(response, false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorListener.onErrorResponse(error);
            }
        });
        RequestQueueManager.getInstance().getRequestQueue().add(request);

        return request;
    }

    public CustomObjectRequest authUrlRequestWithHeaderParams(Context context, final String key, boolean loadFromCached, final boolean saveToCache, int method, String url, final Map<String, String> headers, final Map<String, String>params, final OnResponseListener listener, final Response.ErrorListener errorListener)
    {
        if(loadFromCached)
        {
            String cached = CacheManager.getInstance().getCache().get(key);
            if(cached != null && cached.length() > 0) {
                listener.onResponse(cached, true);
            }
        }

        final CustomAuthObjectRequest request = new CustomAuthObjectRequest(context, method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(saveToCache)
                    CacheManager.getInstance().getCache().put(key, response.toString());
                listener.onResponse(response, false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorListener.onErrorResponse(error);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> sParams = super.getParams();
                sParams.putAll(params);
                return sParams;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = super.getHeaders();
                if(headers != null)
                    params.putAll(headers);
                return params;
            }
        };
        RequestQueueManager.getInstance().getRequestQueue().add(request);

        return request;
    }

    public CustomObjectRequest authUrlRequestWithHeaderBodyParams(Context context, final String key, boolean loadFromCached, final boolean saveToCache, int method, String url, final Map<String, String> headers, final String params, final OnResponseListener listener, final Response.ErrorListener errorListener)
    {
        if(loadFromCached)
        {
            String cached = CacheManager.getInstance().getCache().get(key);
            if(cached != null && cached.length() > 0) {
                listener.onResponse(cached, true);
            }
        }

        CustomAuthObjectRequest request = new CustomAuthObjectRequest(context, method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(saveToCache)
                    CacheManager.getInstance().getCache().put(key, response.toString());
                listener.onResponse(response, false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorListener.onErrorResponse(error);
            }
        })
        {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return params.getBytes();
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> sParams = super.getParams();
                return sParams;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = super.getHeaders();
                if(headers != null)
                    params.putAll(headers);
                return params;
            }
        };
        RequestQueueManager.getInstance().getRequestQueue().add(request);

        return request;
    }
}

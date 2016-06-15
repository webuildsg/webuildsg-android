package com.trinitystudio.webuildsg.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.trinitystudio.core.listener.OnItemClickListener;
import com.trinitystudio.core.listener.OnResponseListener;
import com.trinitystudio.core.manager.ApiManager;
import com.trinitystudio.core.manager.UriManager;
import com.trinitystudio.core.net.CustomObjectRequest;
import com.trinitystudio.webuildsg.R;
import com.trinitystudio.webuildsg.activity.EventDetailActivity;
import com.trinitystudio.webuildsg.adapter.EventInfoAdapter;
import com.trinitystudio.webuildsg.config.KeyConfig;
import com.trinitystudio.webuildsg.model.events.EventModel;

/**
 * Created by liccowee on 4/18/16.
 */
public class EventFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private EventInfoAdapter adapter;
    private EventModel events;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CustomObjectRequest urlRequestEvents;
    private CoordinatorLayout coordinatorLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_event, container, false);

        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinator_layout);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        adapter = new EventInfoAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.notifyDataSetChanged();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                urlRequestEvents = null;
                swipeRefreshLayout.setRefreshing(true);
                getEvents();
            }
        });

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent();
                intent.setClass(getContext(), EventDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(KeyConfig.BUNDLE_EVENT, events.getEvents().get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        getEvents();

        return view;
    }

    private void getEvents()
    {
        String uri = String.format("%s", UriManager.events());
        urlRequestEvents = ApiManager.getInstance().urlRequest(getContext(), KeyConfig.CACHE_EVENT, true, true, StringRequest.Method.GET, uri, new OnResponseListener() {
            @Override
            public void onResponse(String response, boolean fromCached) {
                System.out.println(response);

                events = new Gson().fromJson(response, EventModel.class);
                if(events != null) {

                    adapter.setData(events.getEvents());
                    adapter.notifyDataSetChanged();

                    if (!fromCached) {
                        if (urlRequestEvents != null) urlRequestEvents.markDelivered();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
                else
                {
                    Snackbar.make(coordinatorLayout, getString(R.string.err_error_data), Snackbar.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(coordinatorLayout, getString(R.string.err_error_network), Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}

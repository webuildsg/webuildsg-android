package com.trinitystudio.webuildsg.fragment;

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
import com.trinitystudio.webuildsg.adapter.RepoInfoAdapter;
import com.trinitystudio.webuildsg.config.KeyConfig;
import com.trinitystudio.webuildsg.model.repos.RepoModel;

/**
 * Created by liccowee on 4/18/16.
 */
public class RepoFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private RepoInfoAdapter adapter;
    private RepoModel repos;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CustomObjectRequest urlRequestRepos;
    private CoordinatorLayout coordinatorLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_repo, container, false);

        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinator_layout);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        adapter = new RepoInfoAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.notifyDataSetChanged();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                urlRequestRepos = null;
                swipeRefreshLayout.setRefreshing(true);
                getRepos();
            }
        });

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                Intent intent = new Intent();
//                intent.setClass(getContext(), TrafficPhotoActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString(KeyConfig.BUNDLE_CAMERA_ID, trafficInfo.get(position).getCameraId());
//                intent.putExtras(bundle);
//                startActivity(intent);
            }
        });

        getRepos();

        return view;
    }

    private void getRepos()
    {
        String uri = String.format("%s", UriManager.repos());
        urlRequestRepos = ApiManager.getInstance().urlRequest(getContext(), KeyConfig.CACHE_REPO, true, true, StringRequest.Method.GET, uri, new OnResponseListener() {
            @Override
            public void onResponse(String response, boolean fromCached) {
                System.out.println(response);

                RepoModel repos = new Gson().fromJson(response, RepoModel.class);
                if(repos != null) {

                    adapter.setData(repos.getRepos());
                    adapter.notifyDataSetChanged();

                    if (!fromCached) {
                        if (urlRequestRepos != null) urlRequestRepos.markDelivered();
                        swipeRefreshLayout.setRefreshing(false);
                    }
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

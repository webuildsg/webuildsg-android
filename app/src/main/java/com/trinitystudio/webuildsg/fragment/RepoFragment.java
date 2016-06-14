package com.trinitystudio.webuildsg.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trinitystudio.core.net.CustomObjectRequest;
import com.trinitystudio.webuildsg.R;
import com.trinitystudio.webuildsg.adapter.EventInfoAdapter;

/**
 * Created by liccowee on 4/18/16.
 */
public class RepoFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private EventInfoAdapter adapter;
    //private ArrayList<TrafficInfoModel> trafficInfo;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CustomObjectRequest corCauseway;
    private CustomObjectRequest cor2ndLink;
    private TextView tvUpdated;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_repo, container, false);

//        trafficInfo = new ArrayList<>();
//        trafficInfo.add(new TrafficInfoModel(getString(R.string.via_causeway), "2702"));
//        trafficInfo.add(new TrafficInfoModel(getString(R.string.via_2nd_link), "4713"));

        tvUpdated = (TextView) view.findViewById(R.id.tv_updated);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        adapter = new EventInfoAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //adapter.setData(trafficInfo);
        adapter.notifyDataSetChanged();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cor2ndLink = null;
                corCauseway = null;
                swipeRefreshLayout.setRefreshing(true);
                getCausewayWoodland();
                get2ndLink();
            }
        });

//        adapter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                Intent intent = new Intent();
//                intent.setClass(getContext(), TrafficPhotoActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString(KeyConfig.BUNDLE_CAMERA_ID, trafficInfo.get(position).getCameraId());
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });

        getCausewayWoodland();
        get2ndLink();

        return view;
    }


    private void getCausewayWoodland()
    {
//        String uri = String.format("%s?origin=%s&destination=%s&key=%s&departure_time=%s", UriManager.googleDirection(), GlobalConstant.CAUSEWAY_JB, GlobalConstant.CAUSEWAY_SG, GlobalConstant.GOOGLE_DIRECTION_API_KEY, "now");
//        corCauseway = ApiManager.getInstance().urlRequest(getContext(), KeyConfig.CACHE_NONE, true, true, StringRequest.Method.GET, uri, new OnResponseListener() {
//            @Override
//            public void onResponse(String response, boolean fromCached) {
//                //System.out.println(response);
//                GoogleDirectionModel routesModel = new Gson().fromJson(response, GoogleDirectionModel.class);
//                if(routesModel != null)
//                {
//                    if(!routesModel.getRoutes().isEmpty())
//                    {
//                        if(!routesModel.getRoutes().get(0).getLegs().isEmpty())
//                        {
//                            trafficInfo.get(0).setInfo(routesModel.getRoutes().get(0).getLegs().get(0));
//                            adapter.notifyDataSetChanged();
//                        }
//                    }
//                }
//                if(!fromCached) {
//                    if (corCauseway != null) corCauseway.markDelivered();
//                    checkIsLoaded();
//                    saveUpdatedTime();
//                    refreshTime();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                if(corCauseway != null) corCauseway.markDelivered();
//                checkIsLoaded();
//            }
//        });
    }

    private void get2ndLink()
    {
//        String uri = String.format("%s?origin=%s&destination=%s&key=%s&departure_time=%s", UriManager.googleDirection(), GlobalConstant.SECOND_LINK_JB2, GlobalConstant.SECOND_LINK_SG2, GlobalConstant.GOOGLE_DIRECTION_API_KEY, "now");
//        cor2ndLink = ApiManager.getInstance().urlRequest(getContext(), KeyConfig.CACHE_NONE, true, true, StringRequest.Method.GET, uri, new OnResponseListener() {
//            @Override
//            public void onResponse(String response, boolean fromCached) {
//                //System.out.println(response);
//                GoogleDirectionModel routesModel = new Gson().fromJson(response, GoogleDirectionModel.class);
//                if(routesModel != null)
//                {
//                    if(!routesModel.getRoutes().isEmpty())
//                    {
//                        if(!routesModel.getRoutes().get(0).getLegs().isEmpty())
//                        {
//                            trafficInfo.get(1).setInfo(routesModel.getRoutes().get(0).getLegs().get(0));
//                            adapter.notifyDataSetChanged();
//                        }
//                    }
//                }
//
//                if(!fromCached) {
//                    if (cor2ndLink != null) cor2ndLink.markDelivered();
//                    checkIsLoaded();
//                    saveUpdatedTime();
//                    refreshTime();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                if(cor2ndLink != null) cor2ndLink.markDelivered();
//                checkIsLoaded();
//            }
//        });
    }

    private void checkIsLoaded()
    {
        if(cor2ndLink != null && corCauseway != null)
        {
            if(cor2ndLink.hasHadResponseDelivered() && corCauseway.hasHadResponseDelivered())
                swipeRefreshLayout.setRefreshing(false);
        }
    }
}

package com.trinitystudio.webuildsg.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.trinitystudio.webuildsg.R;
import com.trinitystudio.webuildsg.config.KeyConfig;
import com.trinitystudio.webuildsg.model.events.EventSingleModel;

/**
 * Created by liccowee on 6/14/16.
 */
public class EventDetailActivity extends BaseActivity implements OnMapReadyCallback {
    private TextView tvTitle;
    private TextView tvDate;
    private TextView tvLocationName;
    private EventSingleModel event;
    private TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            event = (EventSingleModel) bundle.getSerializable(KeyConfig.BUNDLE_EVENT);
            if(event == null)
            {
                return;
            }
        }
        else
            return;

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvDate = (TextView) findViewById(R.id.tv_date);
        tvLocationName = (TextView) findViewById(R.id.tv_location_name);
        tvDescription = (TextView) findViewById(R.id.tv_description);

        tvTitle.setText(event.getName());
        tvDate.setText(event.getFormatted_time());
        tvLocationName.setText(event.getLocation());
        tvDescription.setText(event.getDescription());

        SupportMapFragment map = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        map.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.getUiSettings().setAllGesturesEnabled(false);
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                String mapUrl = String.format("http://maps.google.co.in/maps?q=%s", event.getLocation());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl));
                startActivity(intent);
            }
        });

        if(event.getLatitude() == 0f && event.getLongitude() == 0f)
        {
            event.setLatitude(1.290270f);
            event.setLongitude(103.851959f);
        }

        LatLng eventLatLng = new LatLng(event.getLatitude(), event.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(eventLatLng).title(event.getLocation()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eventLatLng, 15f));
    }
}

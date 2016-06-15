package com.trinitystudio.webuildsg.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.trinitystudio.core.GlobalConstant;
import com.trinitystudio.core.customtabs.CustomTabActivityHelper;
import com.trinitystudio.webuildsg.R;
import com.trinitystudio.webuildsg.config.KeyConfig;
import com.trinitystudio.webuildsg.model.events.EventSingleModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by liccowee on 6/14/16.
 */
public class EventDetailActivity extends BaseActivity implements OnMapReadyCallback {
    private TextView tvTitle;
    private TextView tvDate;
    private TextView tvLocationName;
    private EventSingleModel event;
    private TextView tvDescription;
    private View btnAddCalendr;
    private View btnRsvpEvent;
    private SimpleDateFormat sdf = new SimpleDateFormat(GlobalConstant.DATE_TIME_FORMAT3);
    private SimpleDateFormat sdf1 = new SimpleDateFormat(GlobalConstant.DATE_TIME_FORMAT4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        sdf1.setTimeZone(TimeZone.getTimeZone("GMT"));

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
        btnAddCalendr = findViewById(R.id.btn_add_calendar);
        btnRsvpEvent = findViewById(R.id.btn_rsvp_event);

        tvTitle.setText(event.getName());
        tvDate.setText(event.getFormatted_time());
        tvLocationName.setText(event.getLocation());
        tvDescription.setText(event.getDescription());

        btnAddCalendr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Date sDate = null;
                    sDate = sdf.parse(event.getStart_time());
                    Date eDate = sdf.parse(event.getEnd_time());
                    Intent intent = new Intent(Intent.ACTION_INSERT)
                            .setData(CalendarContract.Events.CONTENT_URI)
                            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, sDate.getTime())
                            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, eDate.getTime())
                            .putExtra(CalendarContract.Events.TITLE, event.getName())
                            .putExtra(CalendarContract.Events.DESCRIPTION, event.getDescription())
                            .putExtra(CalendarContract.Events.EVENT_LOCATION, event.getLocation())
                            .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
                    startActivity(intent);
                } catch (ParseException e) {

                    e.printStackTrace();

                    try {
                        Date sDate = sdf1.parse(event.getStart_time());
                        Date eDate = sdf1.parse(event.getEnd_time());
                        Intent intent = new Intent(Intent.ACTION_INSERT)
                                .setData(CalendarContract.Events.CONTENT_URI)
                                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, sDate.getTime())
                                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, eDate.getTime())
                                .putExtra(CalendarContract.Events.TITLE, event.getName())
                                .putExtra(CalendarContract.Events.DESCRIPTION, event.getDescription())
                                .putExtra(CalendarContract.Events.EVENT_LOCATION, event.getLocation())
                                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
                        startActivity(intent);
                    }
                    catch (Exception e1)
                    {
                        e1.printStackTrace();
                    }
                }
            }
        });

        btnRsvpEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(event.getUrl());
                CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().setToolbarColor(ContextCompat.getColor(EventDetailActivity.this, R.color.colorPrimary)).build();
                CustomTabActivityHelper.openCustomTab(EventDetailActivity.this, customTabsIntent, uri, new CustomTabActivityHelper.CustomTabFallback() {

                    @Override
                    public void openUri(Activity activity, Uri uri) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });
            }
        });

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

                if(event.getLatitude() == 0f && event.getLongitude() == 0f)
                {
                    String mapUrl = String.format("http://maps.google.co.in/maps?q=%s", event.getLocation());
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl));
                    startActivity(intent);
                }
                else
                {
                    String mapUrl = String.format("geo:%s,%s?q=%s", event.getLatitude(), event.getLongitude(), event.getLocation());
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl));
                    startActivity(intent);
                }
            }
        });

        if(event.getLatitude() == 0f && event.getLongitude() == 0f)
        {
            LatLng eventLatLng = new LatLng(1.290270f, 103.851959f);
            googleMap.addMarker(new MarkerOptions().position(eventLatLng).title(event.getLocation()));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eventLatLng, 15f));
        }
        else {

            LatLng eventLatLng = new LatLng(event.getLatitude(), event.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(eventLatLng).title(event.getLocation()));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eventLatLng, 15f));
        }
    }
}

package com.trinitystudio.webuildsg.model.events;

/**
 * Created by liccowee on 6/14/16.
 */
public class EventSingleModel
{
    private String name;
    private String description;
    private String location;
    private int resv_count;
    private String url;
    private String group_name;
    private String group_url;
    private String formatted_time;
    private String start_time;
    private String end_time;
    private float latitude;
    private float longitude;

    public String getDescription() {
        return description;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getFormatted_time() {
        return formatted_time;
    }

    public String getGroup_name() {
        return group_name;
    }

    public String getGroup_url() {
        return group_url;
    }

    public float getLatitude() {
        return latitude;
    }

    public String getLocation() {
        return location;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public int getResv_count() {
        return resv_count;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getUrl() {
        return url;
    }
}

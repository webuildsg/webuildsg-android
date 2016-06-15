package com.trinitystudio.core;

import java.text.SimpleDateFormat;

/**
 * Created by Licco on 12/1/16.
 */
public class GlobalConstant {

    // cache settings
    public static final String CACHE_NAME = "WeBuildSG-Cache";
    public static final int CACHE_VERSION = 1;
    public static final int EXTRA_RAM_CACHE_SIZE = 160000;
    public static final int EXTRA_DISK_CACHE_SIZE = EXTRA_RAM_CACHE_SIZE * 20;
    public static final String FOLDER_NAME_FOR_IMAGES = "webuildsg-images";
    public static final String FOLDER_NAME_FOR_STORAGE = FOLDER_NAME_FOR_IMAGES + "/";


    public static final int ATTACH_PHOTO_SIZE_WIDTH = 1200;

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "T00:00:00+08:00";
    public static final String TIME_FORMAT2 = "'T'HH:mm:ssZZZZZ";
    public static final String TIME_FORMAT3 = "'T'HH:mm:ss'Z'";
    public static final String TIME_FORMAT4 = "'T'HH:mm:ss.sss'Z'";
    public static final String STANDARD_DATE_FORMAT = "d MMMM yyyy";
    public static final String STANDARD_DATE_TIME_FORMAT = "d MMMM yyyy hh:mm a";
    public static final String DATE_TIME_FORMAT = DATE_FORMAT + TIME_FORMAT2;
    public static final String DATE_TIME_FORMAT3 = DATE_FORMAT + TIME_FORMAT3;
    public static final String DATE_TIME_FORMAT4 = DATE_FORMAT + TIME_FORMAT4;
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
}

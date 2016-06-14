package com.trinitystudio.core.manager;

import com.trinitystudio.webuildsg.SharedConfig;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liccowee on 7/8/15.
 */
public class UriManager
{
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private static String getDomain()
    {
        if(isProduction())
        {
            return "https://webuild.sg";
        }
        else
        {
            return "https://webuild.sg";
        }
    }

    public static String events()
    {
        return getDomain() + "/api/v1/events";
    }

    public static String eventsInDay()
    {
        return getDomain() + "/api/v1/events/day";
    }

    public static String eventsInHour()
    {
        return getDomain() + "/api/v1/events/hour";
    }

    public static String searchEventByDate(Date date)
    {
        String d = sdf.format(date);
        return getDomain() + "/api/v1/check/" + d;
    }

    public static String repos()
    {
        return getDomain() + "/api/v1/repos";
    }

    public static String reposInDay()
    {
        return getDomain() + "/api/v1/repos/day";
    }

    public static String reposInHour()
    {
        return getDomain() + "/api/v1/repos/hour";
    }

    public static String searchRepoByLanguage(String language)
    {
        return getDomain() + "/api/v1/repos/" + language;
    }

    private static boolean isProduction()
    {
        return SharedConfig.IS_PRODUCTION;
    }
}

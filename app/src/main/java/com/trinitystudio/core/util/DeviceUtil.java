package com.trinitystudio.core.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.WindowManager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;

public class DeviceUtil {
    private static String device_id = "device_id";
    public static String getDeviceId(Context context) {
    	if (PrefUtil.loadStr(context, device_id)!=null && !PrefUtil.loadStr(context, device_id).equals("")) {
    		return PrefUtil.loadStr(context, device_id);
    	}
    	
        String serviceName = Context.TELEPHONY_SERVICE;
        TelephonyManager tele = (TelephonyManager)context.getSystemService(
                serviceName);
        String uuid = tele.getDeviceId();
        
        if (uuid==null) uuid = Secure.getString(context.getContentResolver(),
                Secure.ANDROID_ID);
        
        if (uuid==null) uuid = random(10);
        
        uuid += System.currentTimeMillis();
        
        PrefUtil.saveStr(context, device_id, uuid);
        
        return uuid;
    }

    public static String getDeviceString() {
        String deviceString = "";
        try {
            deviceString = URLEncoder.encode(DeviceUtil.getDeviceName(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return deviceString;
    }
    
    /**
	 * Get App Version
	 * 
	 * @param context
	 * @return App Version Int
	 */
	public static String getAppVersion(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}

    public static int getAppCodeVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            return info.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public static int getOSVersion()
    {
    	return Build.VERSION.SDK_INT;
    }

	public static String getDeviceModel(Context context) {
		String manufacturer = Build.MANUFACTURER;
		String model = Build.MODEL;
		if (model.startsWith(manufacturer)) {
			return model.toUpperCase();
		} else {
			return model.toUpperCase() + " " + model;
		}
	}

    public static String getMobileNetworkCode(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager)context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getNetworkOperator();
    }

    public static String getMobileCountryCode(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager)context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getNetworkCountryIso();
    }

    public static String getCarrier(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager)context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getNetworkOperatorName();
    }

    public static String getDevice() {
        // TODO: not implemented yet
        return Build.CPU_ABI;
    }
    
    public static String getDeviceName() {
        StringBuilder deviceSB = new StringBuilder();
        deviceSB.append(Build.MANUFACTURER);
        deviceSB.append("-");
        deviceSB.append(Build.MODEL);
        deviceSB.append("@");
        deviceSB.append(Build.VERSION.RELEASE);
        return deviceSB.toString();
    }

    public static int getScreenWidth(Context context) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        return display.getWidth();
    }

    public static int getScreenHeight(Context context)
    {
    	 Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
         return display.getHeight();
    }
    
    public static boolean isTablet(Context context)
    {
    	return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
    
    public static String getPlatform()
    {
    	return "android";
    }
    
    public static String getDeviceType(Context context)
    {
    	return getPlatform() + (isTablet(context)?"-tablet":"-phone");
    }
    
    private static String random(int len) {
		String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random rnd = new Random();
		
		StringBuilder sb = new StringBuilder( len );
		   for( int i = 0; i < len; i++ ) 
		      sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
		   return sb.toString();
	}
}

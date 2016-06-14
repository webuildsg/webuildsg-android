package com.trinitystudio.core.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefUtil {

	public static void saveStr(Context context, String key, String value) {
		SharedPreferences.Editor prefs = PreferenceManager
				.getDefaultSharedPreferences(context).edit();
		prefs.putString(key, value);
		prefs.commit();
	}

	public static String loadStr(Context context, String key) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		String str = prefs.getString(key, null);
		return str;
	}

	public static String loadStr(Context context, String key, String def) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		String str = prefs.getString(key, def);
		return str;
	}

	public static void saveInt(Context context, String key, int value) {
		SharedPreferences.Editor prefs = PreferenceManager
				.getDefaultSharedPreferences(context).edit();
		prefs.putInt(key, value);
		prefs.commit();
	}

	public static int loadInt(Context context, String key, int defaultValue) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		int prefix = prefs.getInt(key, defaultValue);
		return prefix;
	}

	public static void saveLong(Context context, String key, long value) {
		SharedPreferences.Editor prefs = PreferenceManager
				.getDefaultSharedPreferences(context).edit();
		prefs.putLong(key, value);
		prefs.commit();
	}

	public static long loadLong(Context context, String key, long defaultValue) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		return prefs.getLong(key, defaultValue);
	}

	public static void saveDouble(Context context, String key, double value) {
		SharedPreferences.Editor prefs = PreferenceManager
				.getDefaultSharedPreferences(context).edit();
		prefs.putString(key, String.valueOf(value));
		prefs.commit();
	}

	public static double loadDouble(Context context, String key, double defValue) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		return prefs.getString(key, null) == null ? defValue : Double
				.parseDouble(prefs.getString(key, null));
	}

	public static void saveBool(Context context, String key, boolean value) {
		SharedPreferences.Editor prefs = PreferenceManager
				.getDefaultSharedPreferences(context).edit();
		prefs.putBoolean(key, value);
		prefs.commit();
	}

	public static boolean loadBool(Context context, String key,
			boolean defaultValue) {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		boolean prefix = prefs.getBoolean(key, defaultValue);
		return prefix;
	}

	public static void delete(Context context, String key) {
		SharedPreferences.Editor prefs = PreferenceManager
				.getDefaultSharedPreferences(context).edit();
		prefs.remove(key);
		prefs.commit();
	}
}

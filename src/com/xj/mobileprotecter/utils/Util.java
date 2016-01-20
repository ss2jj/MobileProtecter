package com.xj.mobileprotecter.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

public class Util {

	/*
	 * ºÏ≤‚Õ¯¬Á
	 */
	public static boolean checkNetwork(Context context)	{
		boolean ok = false;
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo nis = cm.getActiveNetworkInfo();
		if(nis != null && nis.isAvailable()) ok = true;
		else ok = false;
		return ok;
	}
	
	/**
	 * ºÏ≤‚ «∑Ò”–SDø®
	 */
	public static boolean checkSDExist()	{
		boolean exist = false;
		exist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)?true:false;
		return exist;
	}
}

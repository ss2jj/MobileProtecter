package com.xj.mobileprotecter.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
	
	/**
	 * md5º”√‹
	 */
	public static String md5(String pwd)	{
		StringBuilder sb = new StringBuilder();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte []bs = md.digest(pwd.getBytes());
			for(byte b : bs)	{
				int i = b & 0xFF;
				String bt = Integer.toHexString(i);
				if(bt.length() == 1)	{
					sb.append("0");
				}
				sb.append(bt);
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return "";
		}
		
		
		return sb.toString();
	}
}

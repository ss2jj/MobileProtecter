package com.xj.mobileprotecter.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

public class Util {

	/*
	 * 检测网络
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
	 * 检测是否有SD卡
	 */
	public static boolean checkSDExist()	{
		boolean exist = false;
		exist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)?true:false;
		return exist;
	}
	
	/**
	 * md5加密
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
	
	/**
	 * 发送post 请求
	 *
	 */
	public static String sendHttpPost(String url, List<NameValuePair> params)	{
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		
		
		try {
			post.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8)); //设置post 参数 以utf编码
			HttpResponse response = client.execute(post);
			
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)	{
				return EntityUtils.toString(response.getEntity());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}

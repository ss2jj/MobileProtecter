package com.xj.mobileprotecter.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.xj.mobileprotecter.R;
import com.xj.mobileprotecter.R.layout;
import com.xj.mobileprotecter.R.menu;
import com.xj.mobileprotecter.utils.StreamTools;

import android.os.Bundle;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.Menu;
import android.widget.TextView;

public class SplashActivity extends Activity {
	
	private TextView tv_version = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		initView();
		checkUpdate();
	}
	
	/*
	 * 联网检查升级
	 */
	private void checkUpdate() {
		// TODO Auto-generated method stub
		try {
			URL url = new URL(getString(R.string.server_url));
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setConnectTimeout(4000);
			int code = con.getResponseCode();
			if(code == 200)	{
				InputStream is = con.getInputStream();
				String updateInfo = StreamTools.readFromStream(is);
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private void initView()	{
		tv_version = (TextView) findViewById(R.id.text_version);
		tv_version.setText("版本号："+getVersioName());
	}
	
	/*
	 * 获取软件版本号
	 *
	 */
	private String getVersioName()	{
		PackageManager pm = getPackageManager();
		try {
			PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
			return pi.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

}

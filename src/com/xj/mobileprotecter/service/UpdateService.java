package com.xj.mobileprotecter.service;

import com.xj.mobileprotecter.utils.ConstUtil;
import com.xj.mobileprotecter.utils.HttpDownload;
import com.xj.mobileprotecter.utils.Util;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;


public class UpdateService extends Service {
		@Override
		public void onCreate() {
			// TODO Auto-generated method stub
			super.onCreate();
		}
		
		@Override
		public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		final String url = intent.getStringExtra("apkurl");
		if(Util.checkSDExist() && Util.checkNetwork(this))	{
			final String filePath =  Environment.getExternalStorageDirectory().getAbsolutePath() + url.substring(url.lastIndexOf("/"));
			//filePath+= url.substring(url.lastIndexOf("/"));
			Log.i(ConstUtil.TAG, "filePath :"+filePath);
			new Thread(){
				public void run(){
					HttpDownload.down(url, filePath);
				}
				
			}.start();
			
		}else {
			Log.i(ConstUtil.TAG, "sd is not exist");
		}
		}

		@Override
		public IBinder onBind(Intent intent) {
			// TODO Auto-generated method stub
			return null;
		}

	
	
}

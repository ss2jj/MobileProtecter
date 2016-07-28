package com.xj.mobileprotecter.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.xj.mobileprotecter.utils.ConstUtil;
import com.xj.mobileprotecter.utils.Util;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.widget.Toast;

public class PhoneListernService extends Service {
private TelephonyManager tm = null;
private String result;
	
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		tm.listen(new PhoneStateListener(){
			
			@Override
			public void onCallStateChanged(int state, String incomingNumber) {
				// TODO Auto-generated method stub
				
				super.onCallStateChanged(state, incomingNumber);
				switch (state) {
				case TelephonyManager.CALL_STATE_RINGING:
					final List<NameValuePair> params = new ArrayList<NameValuePair>();
					  // 设置需要传递的参数
				    params.add(new BasicNameValuePair("mobileCode",incomingNumber ));
				    params.add(new BasicNameValuePair("userId", ""));
				    
				     result = Util.sendHttpPost(ConstUtil.ADDRESS_FIND_URL, params);
				     Toast.makeText(getApplicationContext(),""+result.replaceAll("</?[^>]+>","").trim() , 1000).show();		
				   
					break;

				default:
					break;
				}
			}
		}, PhoneStateListener.LISTEN_CALL_STATE);
	}
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}

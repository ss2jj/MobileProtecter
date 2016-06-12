package com.xj.mobileprotecter.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;

public class BootCompleteReceiver extends BroadcastReceiver {
private TelephonyManager thm;
private SharedPreferences sp;
private Context mContext;
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		mContext = arg0;
		sp = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
		String seri = sp.getString("serino", "");
		String localSeri = thm.getSimSerialNumber();
		if(seri.equals(localSeri))	{
			//sim卡没有变化
		}else {
			//sim卡变更 发短信通知
		}
	}

}

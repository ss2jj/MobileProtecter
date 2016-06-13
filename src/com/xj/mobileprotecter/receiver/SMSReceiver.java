package com.xj.mobileprotecter.receiver;


import com.xj.mobileprotecter.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;

public class SMSReceiver extends BroadcastReceiver {
private  final String loaction = "#*location*#";
private  final String alarm = "#*alarm*#";
private  final String wipedata = "#*wipedata*#";
private  final String lockscreen = "#*lockscreen*#";

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		SharedPreferences sp  = arg0.getSharedPreferences("config", Context.MODE_PRIVATE);
		String safeNum = sp.getString("safenumber", "");
		if(!sp.getBoolean("protecting", false))	{
			return ;
		}
		// TODO Auto-generated method stub
		Object  sms[] = (Object []) arg1.getExtras().get("pdus");
		for (Object mes : sms)	{
			SmsMessage message = SmsMessage.createFromPdu((byte[])mes);
			String sender = message.getOriginatingAddress();
			String body = message.getMessageBody();
			
			
			if(sender.equals(safeNum))	{
				if(loaction.equals(body))	{
					abortBroadcast();
				}else if(alarm.equals(body))	{
					MediaPlayer mp = MediaPlayer.create(arg0, R.raw.alarm);
					mp.setLooping(false);
					mp.start();
					abortBroadcast();
				}else if(wipedata.equals(body))	{
					abortBroadcast();
				}else if(lockscreen.equals(body))	{
					abortBroadcast();
				}
			}
		}
	}

}

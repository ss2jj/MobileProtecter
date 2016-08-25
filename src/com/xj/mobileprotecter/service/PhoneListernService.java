package com.xj.mobileprotecter.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.provider.CalendarContract.Events;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.xj.mobileprotecter.R;
import com.xj.mobileprotecter.utils.ConstUtil;
import com.xj.mobileprotecter.utils.Util;

public class PhoneListernService extends Service {
private TelephonyManager tm = null;
private String result;
private View view = null;	
private WindowManager wm = null;
WindowManager.LayoutParams params;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		wm =  (WindowManager) getSystemService(WINDOW_SERVICE);
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
				     //Toast.makeText(getApplicationContext(),""+result.replaceAll("</?[^>]+>","").trim() , 1000).show();		
				     myToast("1234"+result.replaceAll("</?[^>]+>","").trim());
				    
					break;
				case TelephonyManager.CALL_STATE_IDLE:
					if(view != null)
					wm.removeView(view);
					break;
				default:
					break;
				}
			}
		}, PhoneStateListener.LISTEN_CALL_STATE);
	}
	
	
	private void myToast(String message)	{
		view = View.inflate(getApplicationContext(), R.layout.my_toast, null);
		TextView  tv = (TextView) view.findViewById(R.id.tv_show_message);
		view.setOnTouchListener(new OnTouchListener() {
			int startX;
			int startY;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX = (int) event.getRawX();
					startY = (int) event.getRawY();
					break;
				case MotionEvent.ACTION_MOVE:
					int newX = (int) event.getRawX();
					int newY = (int) event.getRawY();
					int dx = newX - startX;
					int dy = newY -  startY;
					params.x += dx;
					params.y += dy;
					wm.updateViewLayout(view, params);
					startX = newX;
					startY = newY;
					break;
				case MotionEvent.ACTION_UP:
	
					break;
	
				default:
					break;
				}
				return true;
			}
		});
		tv.setText(message);
		 params = new WindowManager.LayoutParams();
		
		params.width =  WindowManager.LayoutParams.WRAP_CONTENT;
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		params.flags =  WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE 
				| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
		params.gravity =  Gravity.TOP + Gravity.LEFT;
		params.format = PixelFormat.TRANSLUCENT;
		//具有电话优先级 可显示到锁屏界面 但不能获取焦点
		params.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE;
		
		wm.addView(view, params);
	}
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}

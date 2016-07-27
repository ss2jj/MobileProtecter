package com.xj.mobileprotecter.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.xj.mobileprotecter.R;
import com.xj.mobileprotecter.R.layout;
import com.xj.mobileprotecter.R.menu;
import com.xj.mobileprotecter.utils.Util;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HomePlaceFindActivity extends Activity {
private EditText phoneNum;
private TextView result;
private static final String url = "http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx/getMobileCodeInfo";
private Handler handler = new Handler(){
	
	public void dispatchMessage(android.os.Message msg) {
		if(msg.what == 1)	{
			String results = (String) msg.obj;
			result.setText(results.replaceAll("</?[^>]+>","").trim());
		}
		if(msg.what == 2)	{
			Toast.makeText(HomePlaceFindActivity.this, "查询失败", 1000).show();
		}
		
	};
	
};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_place_find);
		phoneNum = (EditText) findViewById(R.id.et_phonenum);
		result = (TextView) findViewById(R.id.tv_result);
		
	}
//	查询电话号码归属地
	public void find(View view)	{
		String phone = phoneNum.getText().toString();
		if(TextUtils.isEmpty(phone))	{
			Toast.makeText(this, "电话号码为空", 500).show();
		    Animation ani =  AnimationUtils.loadAnimation(this, R.anim.shake);
		    phoneNum.startAnimation(ani);
		    
			return ;
		}
		
		final List<NameValuePair> params = new ArrayList<NameValuePair>();
	    // 设置需要传递的参数
	    params.add(new BasicNameValuePair("mobileCode",phone ));
	    params.add(new BasicNameValuePair("userId", ""));
	    new Thread()	{
	    	public void run()	{
	    		String result = Util.sendHttpPost(url, params);
	    		if(!TextUtils.isEmpty(result))	{
	    			Message message = new Message();
	    			message.what = 1;
	    			message.obj = result;
	    			handler.sendMessage(message);
	    		}else {
	    			handler.sendEmptyMessage(2);
	    		}
	    	}
	    }.start();
		
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_place_find, menu);
		return true;
	}

}

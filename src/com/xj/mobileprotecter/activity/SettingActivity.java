package com.xj.mobileprotecter.activity;

import com.xj.mobileprotecter.R;
import com.xj.mobileprotecter.R.layout;
import com.xj.mobileprotecter.R.menu;
import com.xj.mobileprotecter.service.PhoneListernService;
import com.xj.mobileprotecter.utils.Util;
import com.xj.mobileprotecter.widget.SettingsLayout;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class SettingActivity extends Activity {
private SettingsLayout update_setting;
private SettingsLayout ring_attention_setting;
private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		sp = getSharedPreferences("config", Context.MODE_PRIVATE);
		intiView();
	}

	private void intiView() {
		update_setting = (SettingsLayout) findViewById(R.id.setting_update);
		ring_attention_setting = (SettingsLayout) findViewById(R.id.setting_ring_attention);
		if(sp.getBoolean("auto_update", false))	{
			update_setting.setCheckBox(true);
		}else {
			
			update_setting.setCheckBox(false);
		}
		if(Util.isServiceRuning(getApplicationContext(), "com.xj.mobileprotecter.service.PhoneListernService"))	{
			ring_attention_setting.setCheckBox(true);
		}else {
			ring_attention_setting.setCheckBox(false);
		}
		OnClickListener myClick = new MyOnClickListenter();
		update_setting.setOnClickListener(myClick);
		ring_attention_setting.setOnClickListener(myClick);
	}
	
	private class MyOnClickListenter implements View.OnClickListener	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			if(v.getId() == R.id.setting_update)	{
				Editor editor = sp.edit();
				if(update_setting.getCheckBox().isChecked())	{
					
					update_setting.setCheckBox(false);
					editor.putBoolean("auto_update", false);
				}else {
					
					update_setting.setCheckBox(true);
					editor.putBoolean("auto_update", true);
				}
				editor.commit();
			}else if(v.getId() == R.id.setting_ring_attention)	{
				if(ring_attention_setting.getCheckBox().isChecked())	{
					ring_attention_setting.setCheckBox(false);
					stopService(new Intent(SettingActivity.this, PhoneListernService.class));
				}else {
					ring_attention_setting.setCheckBox(true);
					startService(new Intent(SettingActivity.this, PhoneListernService.class));
				}
			}
		}
		
	}
	

}

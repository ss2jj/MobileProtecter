package com.xj.mobileprotecter.activity;

import com.xj.mobileprotecter.R;
import com.xj.mobileprotecter.R.layout;
import com.xj.mobileprotecter.R.menu;
import com.xj.mobileprotecter.widget.SettingsLayout;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;

public class SettingActivity extends Activity {
private SettingsLayout update_setting;
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
		if(sp.getBoolean("auto_update", false))	{
			update_setting.setCheckBox(true);
		}else {
			
			update_setting.setCheckBox(false);
		}
		
		update_setting.setOnClickListener(new MyOnClickListenter());
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
			}
		}
		
	}
	

}

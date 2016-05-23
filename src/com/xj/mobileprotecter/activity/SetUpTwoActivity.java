package com.xj.mobileprotecter.activity;

import com.xj.mobileprotecter.R;
import com.xj.mobileprotecter.R.layout;
import com.xj.mobileprotecter.R.menu;
import com.xj.mobileprotecter.widget.SettingsLayout;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;

public class SetUpTwoActivity extends Activity {
private SettingsLayout sl;
private TelephonyManager thm;
private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup2);
		thm =  (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		sp = getSharedPreferences("config", Context.MODE_PRIVATE);
		intiView();
	}

	private void intiView() {
		sl  = (SettingsLayout) findViewById(R.id.setting_update);
		if(TextUtils.isEmpty(sp.getString("serino", "")))	{
			sl.setCheckBox(false);
		}else {
			sl.setCheckBox(true);
		}
		sl.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//±£¥Êsimø®–Ú¡–∫≈
				SharedPreferences.Editor editor =  sp.edit();
				if(sl.getCheckBox().isChecked())	{
					sl.setCheckBox(false);
					editor.putString("serino", "");
				}else {
					sl.setCheckBox(true);
					String seri = thm.getSimSerialNumber();
					editor.putString("serino", seri);
				}
				editor.commit();
				
			}
		});
	}
	
	public void next(View view)	{
		startActivity(new Intent(SetUpTwoActivity.this,SetUpThreeActivity.class));
		finish();
		overridePendingTransition(R.anim.activity_pre_ani, R.anim.activity_next_ani);
	}
	
	public void previ(View view)	{
		startActivity(new Intent(SetUpTwoActivity.this,SetUpOneActivity.class));
		finish();
		
	}

}

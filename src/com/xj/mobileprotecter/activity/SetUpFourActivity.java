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
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class SetUpFourActivity extends Activity {
private CheckBox cb = null;
private TextView tv = null;
private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup4);
		sp =  getSharedPreferences("config", Context.MODE_PRIVATE);
		intiView();
	}

	private void intiView() {
		cb = (CheckBox) findViewById(R.id.cb_fp);
		tv = (TextView) findViewById(R.id.tv_protect);
		boolean isOn = sp.getBoolean("protecting", false);
		if(isOn)	{
			tv.setText("防盗保护已经开启");
		}else {
			tv.setText("防盗保护没有开启");
		}
		cb.setChecked(isOn);
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked)	{
					tv.setText("防盗保护已经开启");
				}else {
					tv.setText("防盗保护没有开启");
				}
				//保存选择的状态
				Editor editor = sp.edit();
				editor.putBoolean("protecting", isChecked);
				editor.commit();
			}
		});
	}
	

	public void next(View view)	{
		
		startActivity(new Intent(SetUpFourActivity.this,SetUpActivity.class));
		finish();
		overridePendingTransition(R.anim.activity_pre_ani, R.anim.activity_next_ani);
	}
	public void previ(View view)	{
		startActivity(new Intent(SetUpFourActivity.this,SetUpThreeActivity.class));
		finish();
	}

}

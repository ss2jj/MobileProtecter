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
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class SetUpActivity extends Activity {
private SharedPreferences sp = null;
private TextView safeNum = null;
private TextView isLock =  null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup);
		sp = getSharedPreferences("config", Context.MODE_PRIVATE);
		intiView();
	}

	private void intiView() {
		safeNum =  (TextView) findViewById(R.id.tv_bnm);
		isLock = (TextView) findViewById(R.id.tv_lock);
		safeNum.setText(sp.getString("safenumber", ""));
		Drawable image;
		if(sp.getBoolean("protecting", false))	{
			image =  getResources().getDrawable(R.drawable.lock);
			
		}else {
			image =  getResources().getDrawable(R.drawable.unlock);
		}
		image.setBounds(0, 0, image.getMinimumWidth(), image.getMinimumHeight());
		isLock.setCompoundDrawables(null, null, image, null);
	}
	
	public void reboot(View view)	{
		startActivity(new Intent(this,SetUpOneActivity.class));
		finish();
		overridePendingTransition(R.anim.activity_pre_ani, R.anim.activity_next_ani);
	}
	

	

}

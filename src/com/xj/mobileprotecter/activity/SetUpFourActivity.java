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

public class SetUpFourActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup4);
	
		intiView();
	}

	private void intiView() {
		
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

package com.xj.mobileprotecter.activity;

import com.xj.mobileprotecter.R;
import com.xj.mobileprotecter.R.layout;
import com.xj.mobileprotecter.R.menu;
import com.xj.mobileprotecter.widget.SettingsLayout;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SetUpThreeActivity extends Activity {
private EditText phoneNum;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup3);
	
		intiView();
	}

	private void intiView() {
		phoneNum =  (EditText) findViewById(R.id.et_num);
	}
	
	public void seletContact(View view)	{
		Intent intent = new Intent(Intent.ACTION_PICK,Contacts.CONTENT_URI);
		startActivityForResult(intent, 1);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		//Toast.makeText(this, ""+requestCode, 500).show();
		Uri uri = data.getData();
		ContentResolver cr = getContentResolver();
		
		 Cursor cursor = cr.query(uri, null, null, null, null);
		 while(cursor.moveToNext())	{
			 int num = cursor.getColumnIndex(Contacts._ID);
			 String contactId = cursor.getString(num);
			 Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, 
                     null, 
                     ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, 
                     null, 
                     null);
			  while (phone.moveToNext()) {
	                 String usernumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
	                 phoneNum.setText(usernumber);
	             }
		 }
	}
	public void next(View view)	{
		startActivity(new Intent(SetUpThreeActivity.this,SetUpFourActivity.class));
		finish();
		overridePendingTransition(R.anim.activity_pre_ani, R.anim.activity_next_ani);
	}
	public void previ(View view)	{
		startActivity(new Intent(SetUpThreeActivity.this,SetUpTwoActivity.class));
		finish();
	}

}

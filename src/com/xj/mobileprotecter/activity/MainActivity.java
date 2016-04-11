package com.xj.mobileprotecter.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xj.mobileprotecter.R;
import com.xj.mobileprotecter.utils.Util;

public class MainActivity extends Activity {
private GridView gv_main = null;
private  String itemNames[] = {
"�ֻ�����","ͨѶ��ʿ","�������","���̹���","����ͳ��","�������","�߼�����","��������"	
};
private SharedPreferences sp = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sp = getSharedPreferences("config", Context.MODE_PRIVATE);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		gv_main = (GridView) findViewById(R.id.gv_main);
		calcGVWidth();//����ÿ��item�Ŀ�� ����һ��
		gv_main.setAdapter(new MyAdatper()); //����gridview��adapter
		gv_main.setOnItemClickListener(new MyItemClickListener());
		
	}
	
	private class MyItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			switch (arg2) {
			case 0:
				if(haveNoPwd())	{
					showSetUpDialog();
				}else {
					showInputDialog();
				}
				break;
			case 7:
				Intent intent = new Intent(MainActivity.this,SettingActivity.class);
				startActivity(intent);
				finish();
				break;

			default:
				break;
			}
		}
		
	}
	//�Ƿ����ù�����
	private boolean haveNoPwd()	{
		return TextUtils.isEmpty(sp.getString("pwd", ""));
	}
	
	private void showSetUpDialog()	{
		Builder builder = new Builder(this);
		View view = View.inflate(getBaseContext(), R.layout.passwd_create_dialog, null);
		final AlertDialog dialog = builder.create();
		dialog.setView(view,0,0,0,0);
		dialog.show();
		final EditText input = (EditText) view.findViewById(R.id.pwd_input);
		final EditText confirm = (EditText) view.findViewById(R.id.pwd_confirm);
		Button ok = (Button) view.findViewById(R.id.ok);
		Button cancel = (Button) view.findViewById(R.id.cancel);
		ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String inputPwd = input.getText().toString().trim();
				String confirmPwd = confirm.getText().toString().trim();
				if(TextUtils.isEmpty(inputPwd) || TextUtils.isEmpty(confirmPwd))	{
					Toast.makeText(MainActivity.this, "����Ϊ��", 500).show();
					return ;
				}
				
				if(!confirmPwd.equals(inputPwd))	{
					Toast.makeText(MainActivity.this, "���벻һ��", 500).show();
					return ;
				}
				
				SharedPreferences.Editor editor = sp.edit();
				
				editor.putString("pwd", Util.md5(inputPwd));
				editor.commit();
				dialog.dismiss();
				
			}
		});
	   cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		
	}
	private void showInputDialog()	{
		Builder builder = new Builder(this);
		View view = View.inflate(getBaseContext(), R.layout.passwd_create_dialog, null);
		EditText confirm = (EditText) view.findViewById(R.id.pwd_confirm);
		confirm.setVisibility(View.GONE);
		final AlertDialog dialog = builder.create();
		dialog.setView(view,0,0,0,0);
		dialog.show();
		final EditText input = (EditText) view.findViewById(R.id.pwd_input);
		Button ok = (Button) view.findViewById(R.id.ok);
		Button cancel = (Button) view.findViewById(R.id.cancel);
		ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String inputPwd = input.getText().toString().trim();
				String savePwd = sp.getString("pwd", "");
				if(TextUtils.isEmpty(inputPwd))	{
					Toast.makeText(MainActivity.this, "����Ϊ��", 500).show();
					return ;
				}
				
				if(Util.md5(inputPwd).equals(savePwd))	{
					Toast.makeText(MainActivity.this, "����һ��", 500).show();
					startActivity(new Intent(MainActivity.this, SetUpOneActivity.class));
					//return ;
				}else {
					Toast.makeText(MainActivity.this, "�������", 500).show();
					return ;
				
				}		
				dialog.dismiss();
				
			}
		});
	   cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
	}
	//����GridViewÿ��item�ĸ߶� ��Ϊ��Ⱥ͸߶�һ�� ���������Ը߶�Ϊ׼
	private void calcGVWidth()	{
		WindowManager wm = getWindowManager();
		int width = wm.getDefaultDisplay().getWidth();
		int height = wm.getDefaultDisplay().getHeight();
		int gvHeight = (height-50-30)/4;
		int widthSpacing  = (width-2*gvHeight) - 40;
		gv_main.setNumColumns(2);
		gv_main.setColumnWidth(gvHeight);
		
		gv_main.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		gv_main.setHorizontalSpacing(widthSpacing);
		gv_main.setVerticalSpacing(10);
	}
	private class MyAdatper extends BaseAdapter	{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return itemNames.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@SuppressLint("NewApi")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if(convertView == null)
				convertView = View.inflate(getBaseContext(), R.layout.list_item_main, null);
			ImageView  iv_back = (ImageView) convertView.findViewById(R.id.iv_item_back);
			TextView   tv_name = (TextView) convertView.findViewById(R.id.iv_item_name);
			iv_back.setBackground(getResources().getDrawable(R.drawable.ic_launcher));
			tv_name.setText(itemNames[position]);
			return convertView;
		}
		
	}


}

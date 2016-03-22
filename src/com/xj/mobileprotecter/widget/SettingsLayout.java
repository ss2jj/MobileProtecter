package com.xj.mobileprotecter.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xj.mobileprotecter.R;
/*
 * �Զ�����Ͽؼ� ��Seting item����
 */
public class SettingsLayout extends RelativeLayout{
	private TextView tv_title;
	private TextView tv_content;
	private CheckBox cb_box;
	private String title = "";
	private String content_checked = "";
	private String content_unchecked = "";
	
	public SettingsLayout(Context context) {
		
		super(context);
		initView(context);
		
		// TODO Auto-generated constructor stub
	}

	public SettingsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		
		super(context, attrs, defStyleAttr);
		initView(context);
		// TODO Auto-generated constructor stub
	}

	public SettingsLayout(Context context, AttributeSet attrs) {
		
		super(context, attrs);
		initView(context);
		//��ȡxml�Զ����attr
		title = getResources().getString(attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/com.xj.mobileprotecter", "title",0));
		content_checked = getResources().getString(attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/com.xj.mobileprotecter", "content_checked",0));
		content_unchecked = getResources().getString(attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/com.xj.mobileprotecter", "content_unchecked",0));
		tv_title.setText(title);
	}

	private void initView(Context context) {
		// TODO Auto-generated method stub
		View.inflate(context, R.layout.list_item_setting, this);
		tv_title = (TextView) findViewById(R.id.update_title);
		tv_content = (TextView) findViewById(R.id.update_content);
		cb_box = (CheckBox) findViewById(R.id.update_box);
		
	}
	

	public CheckBox getCheckBox()	{
		return cb_box;
	}
	
	public void setChechBox(boolean checked)	{
		cb_box.setChecked(checked);
		if(checked)	{
			tv_content.setText(content_checked);
		}else {
			tv_content.setText(content_unchecked);
		}
	}
	
	
	
	
}

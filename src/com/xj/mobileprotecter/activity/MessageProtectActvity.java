package com.xj.mobileprotecter.activity;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xj.mobileprotecter.R;
import com.xj.mobileprotecter.db.BlackNameDBUtil;
import com.xj.mobileprotecter.db.BlackNameDao;

public class MessageProtectActvity extends Activity {
	private ListView lv ;
	private BlackNameDBUtil dbUtil;
	private List<BlackNameDao> bads = null;
	private BaseAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_protect);
	    lv = (ListView) findViewById(R.id.lv_messages);
	    dbUtil =  new BlackNameDBUtil(this);
//	    dbUtil.insert("122312", "2");
//	    dbUtil.insert("122312", "2");
//	    dbUtil.insert("122312", "2");
	    bads =  dbUtil.findAll();
	    adapter =  new MyAdapter();
	    lv.setAdapter(adapter);
	    lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				deleteBlackPerson(arg2);
			}
		});
	}
	private void deleteBlackPerson(final int arg2)	{
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("删除黑名单").setMessage("确认删除该号码？")
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				BlackNameDao dao = bads.get(arg2);
				String num = dao.getNumber();
				dbUtil.delete(num);
				bads.remove(arg2);
				adapter.notifyDataSetChanged();
				dialog.dismiss();
				
			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		}).create().show();
		
	}
	public void addBlackPerson(View view)	{
		AlertDialog.Builder builder = new Builder(this);
		final AlertDialog dialog = builder.create();
		View views =View.inflate(this, R.layout.blackname_add_dialog, null);
		dialog.setView(views,0 ,0,0,0);
		final EditText num = (EditText) views.findViewById(R.id.num_input);
		Button ok = (Button) views.findViewById(R.id.ok);
		final CheckBox cb_phone = (CheckBox) views.findViewById(R.id.intercept_phone);
		final CheckBox cb_message = (CheckBox) views.findViewById(R.id.intercept_message);
		ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String number = num.getEditableText().toString();
				String mode = "0";
				if(cb_phone.isChecked() && !cb_message.isChecked())	{
					mode = "1";
				}else if(!cb_phone.isChecked() && cb_message.isChecked())	{
					mode = "2";
				}else if(cb_phone.isChecked() && cb_message.isChecked())	{
					mode = "0";
				}else {
					Toast.makeText(MessageProtectActvity.this, "请选择拦截模式", 1000).show();
					return;
				}
				dbUtil.insert(number, mode);
				 bads =  dbUtil.findAll();
				 adapter.notifyDataSetChanged();
				dialog.dismiss();
			}
		});
		Button cancel = (Button) views.findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		dialog.show();
	}
	private class MyAdapter extends  BaseAdapter	{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return bads.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			View view = null; 
			ViewHolder viewHolder = null;
			if(arg1 == null)	{
				view = View.inflate(getApplicationContext(), R.layout.black_llist_view, null);
				viewHolder = new ViewHolder();
				viewHolder.number =  (TextView) view.findViewById(R.id.tv_blacknum);
				viewHolder.mode =  (TextView) view.findViewById(R.id.tv_mode);
				view.setTag(viewHolder);
			}else {
				
				view =  arg1;
				viewHolder = (ViewHolder) view.getTag();
			}
			BlackNameDao dao = bads.get(arg0);
			viewHolder.number.setText(dao.getNumber());
			if(dao.getMode() == 1)	{
				viewHolder.mode.setText("电话拦截");
			}else if(dao.getMode() == 2){
				viewHolder.mode.setText("短信拦截");
			}else {
				viewHolder.mode.setText("全部拦截");
			}
			
			return view;
		}
		
		
		
	}
	  class ViewHolder	{
			public  TextView number;
			public  TextView mode;
		}

}

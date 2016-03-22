package com.xj.mobileprotecter.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.xj.mobileprotecter.R;
import com.xj.mobileprotecter.R.layout;
import com.xj.mobileprotecter.R.menu;
import com.xj.mobileprotecter.service.UpdateService;
import com.xj.mobileprotecter.utils.ConstUtil;
import com.xj.mobileprotecter.utils.StreamTools;
import com.xj.mobileprotecter.utils.Util;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.Menu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends Activity {

	private TextView tv_version = null;
	private String updateUrl = null ;
	private String desp = null;
	private ProgressBar pb = null;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case ConstUtil.SHOW_UPDATE_DIALOG:
				//Toast.makeText(getBaseContext(), "检测到新版本", 500).show();
				showUpdateDialog();
				//enterMain();
				break;
			case ConstUtil.SHOW_NETWORK_ERROR:
				Toast.makeText(getBaseContext(), "网络错误", 500).show();

				enterMain();
				break;
			case ConstUtil.SHOW_URLPARSE_ERROR:
				Toast.makeText(getBaseContext(), "URL错误", 500).show();

				enterMain();
				break;
			case ConstUtil.SHOW_JSONPARSE_ERROR:
				Toast.makeText(getBaseContext(), "JSON解析出错", 500).show();

				enterMain();
				break;
			case ConstUtil.ENTER_MAIN:
				enterMain();
				break;

			default:
				break;
			}
		}
	};
	
	//升级对话框
	private void showUpdateDialog() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new Builder(this);
		AlertDialog dialog = builder.setTitle(R.string.update_dialog_title)
			   .setMessage(R.string.update_dialog_message)
			   .setPositiveButton(R.string.update_dialog_ok, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					//启动下载服务
					//updateUrl = "http://pic8.nipic.com/20100706/4853121_072236095383_2.jpg";
					
					if(updateUrl != null)	{
						Intent intent = new Intent(SplashActivity.this,UpdateService.class);
						intent.putExtra("apkurl", updateUrl);
						startService(intent);
					}
					dialog.dismiss();
					enterMain();
				}
			})
			.setNegativeButton(R.string.update_dialog_cancel, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					enterMain();
				}
			}).create();
		dialog.setCancelable(false);
		dialog.show();
	}

	//进入主页
	private void enterMain() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		initView();
		if(Util.checkNetwork(this))	{
			if(getSharedPreferences("setting", Context.MODE_PRIVATE).getBoolean("auto_update", false))	{
				checkUpdate();
			}
			else {
				mHandler.sendEmptyMessageDelayed(ConstUtil.ENTER_MAIN, 2000);
			}
		}else {
			mHandler.sendEmptyMessage(ConstUtil.SHOW_NETWORK_ERROR);
		}
		
	}

	/*
	 * 联网检查升级 要放在异步线程里操作 否则会阻塞主线程
	 */
	private void checkUpdate() {
		new Thread() {
			Message msg = new Message();
			long startTime = 0;

			public void run() {
				try {
					startTime = System.currentTimeMillis();
					URL url = new URL(getString(R.string.server_url));
					HttpURLConnection con = (HttpURLConnection) url
							.openConnection();
					con.setRequestMethod("GET");
					con.setConnectTimeout(4000);
					int code = con.getResponseCode();
					if (code == 200) {
						InputStream is = con.getInputStream();
						String updateInfo = StreamTools.readFromStream(is);
						// 解析升级信息
						JSONObject json = new JSONObject(updateInfo);
						String serverVersion = json.getString("version");
						desp = json.getString("description");
						updateUrl = json.getString("url");
						// 比对升级信息 下载升级包
						if (!serverVersion.equals(getVersioName())) {
							// 弹出对话框 下载升级包
							msg.what = ConstUtil.SHOW_UPDATE_DIALOG;

						} else {
							// 进入主页面
							msg.what = ConstUtil.ENTER_MAIN;

						}

					} else {
						// 请求不成功
						msg.what = ConstUtil.SHOW_NETWORK_ERROR;
						//msg.what = ConstUtil.SHOW_UPDATE_DIALOG;

					}

				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					msg.what = ConstUtil.SHOW_URLPARSE_ERROR;

					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					msg.what = ConstUtil.SHOW_JSONPARSE_ERROR;

				} finally {
					long endTime = System.currentTimeMillis();
					if (endTime - startTime < 2000) {
						try {
							Thread.sleep(2000 - endTime + startTime);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					mHandler.sendMessage(msg);
				}

			};
		}.start();

	}

	private void initView() {
		tv_version = (TextView) findViewById(R.id.text_version);
		tv_version.setText("版本号：" + getVersioName());
		pb = (ProgressBar) findViewById(R.id.pb_proress);
		pb.setVisibility(View.VISIBLE);
		AlphaAnimation aa = new AlphaAnimation(0.1f, 1.0f);
		aa.setDuration(2000);
		findViewById(R.id.rl_root).setAnimation(aa);
		aa.start();
	}

	/*
	 * 获取软件版本号
	 */
	private String getVersioName() {
		PackageManager pm = getPackageManager();
		try {
			PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
			return pi.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

}

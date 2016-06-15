package com.xj.mobileprotecter.service;



import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;


public class GPSService extends Service {
	private LocationManager lm = null;
		@Override
		public void onCreate() {
			// TODO Auto-generated method stub
			super.onCreate();
			lm =  (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			Criteria cr = new Criteria();
			cr.setAccuracy(Criteria.ACCURACY_FINE);
			String provider = lm.getBestProvider(cr, true);
			lm.requestLocationUpdates(provider, 0, 0, new LocationListener() {
				
				@Override
				public void onStatusChanged(String provider, int status, Bundle extras) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onProviderEnabled(String provider) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onProviderDisabled(String provider) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onLocationChanged(Location location) {
					// TODO Auto-generated method stub
					String longitude = "j:"+location.getLongitude()+"\n";
					String latitude = "w:"+location.getLatitude() +"\n";
					String accuracy = "a"+location.getAccuracy()+"\n";
					SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
					Editor editor = sp.edit();
					editor.putString("lastlocation", longitude + latitude + accuracy);
					editor.commit();
				}
			} );
		}
		
		@Override
		public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		
		}

		@Override
		public IBinder onBind(Intent intent) {
			// TODO Auto-generated method stub
			return null;
		}

	
	
}

package com.xj.mobileprotecter.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BlackNameDBUtil {
private static BlackNameDBHelper dbHelper = null;	
private static final String TABLE_NAME = "blackname";
	public BlackNameDBUtil(Context context)	{
		dbHelper =  new BlackNameDBHelper(context, null, null, 0);
	}

	/**
	 * Ôö
	 */
	
	public boolean insert(String number,String mode)	{
		SQLiteDatabase db =  dbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("number", number);
		cv.put("mode", mode);
		db.insert(TABLE_NAME, null, cv);
		db.close();
		return true;
	}
	/**
	 * É¾
	 */
	public boolean delete(String number)	{
		SQLiteDatabase db =  dbHelper.getWritableDatabase();
		
		db.delete(TABLE_NAME, new String("number=?"), new String[]{number});
		db.close();
		return true;
	}
	/**
	 * ¸Ä
	 */
	public boolean update(String number,String newMode)	{
		SQLiteDatabase db =  dbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("mode", newMode);
		db.update(TABLE_NAME, cv, new String("number=?"), new String[]{number});
		db.close();
		return true;
	}
	/**
	 * ²é
	 */
	public String findMode(String number)	{
		String mode  = null;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select mode from blackname where number=?", new String[]{number});
		if(cursor.moveToNext())	{
			mode = cursor.getString(cursor.getColumnIndex("mode"));
		}
		cursor.close();
		db.close();
		return mode;
	}
	
	public List<BlackNameDao> findAll()	{
		List<BlackNameDao>  dbas = new ArrayList<BlackNameDao>();
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select number,mode from blackname order by _id desc", null);
		while(cursor.moveToNext())	{
			BlackNameDao dao = new BlackNameDao();
			dao.setNumber(cursor.getString(0));
			dao.setMode(Integer.parseInt(cursor.getString(1)));
			dbas.add(dao);
			
		}
		cursor.close();
		db.close();
		return dbas;
	}
	
}

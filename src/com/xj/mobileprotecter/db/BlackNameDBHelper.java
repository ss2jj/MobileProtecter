package com.xj.mobileprotecter.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BlackNameDBHelper extends SQLiteOpenHelper {

	private static final String CREATE_TABLE = "create table blackname (_id integer"
			+ " primary key autoincrement,number varchar(20), mode varchar(2))";
	
	//创建数据库
	public BlackNameDBHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, "BlackName.db", null, 1);
		// TODO Auto-generated constructor stub
	}

	//创建表
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}

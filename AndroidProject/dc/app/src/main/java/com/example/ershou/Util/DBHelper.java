package com.example.ershou.Util;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

@SuppressLint("SimpleDateFormat")
public class DBHelper {

	private static final String name = "good.db";
	private static final int version = 1;
	private static SQLiteDatabase db;
	private static DBOpenHelper helper;

	private static class DBOpenHelper extends SQLiteOpenHelper {
		public DBOpenHelper(Context context) {
			super(context, name, null, version);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			String user = "CREATE TABLE good (id integer NOT NULL PRIMARY KEY AUTOINCREMENT, nid VARCHAR(50),name VARCHAR(50),price Varchar(50),lxr varchar(50),tel varchar(50),count varChar(50))";
			db.execSQL(user);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
			// TODO Auto-generated method stub
		}
	}

	public DBHelper(Context context) {
		// TODO Auto-generated constructor stub
		helper = new DBOpenHelper(context);
		db = helper.getWritableDatabase();
	}

	public void insertProduce(String nid, String name, String price,String lxr,String tel,String count) {
		ContentValues content = new ContentValues();
		content.put("nid", nid);
		content.put("name", name);
		content.put("price", price);
		content.put("tel", tel);
		content.put("lxr", lxr);
		content.put("count",count);
		db.insert("good", null, content);
	}

	public List<news> findAll() {
		news pro;
		List<news> list = new ArrayList<news>();
		Cursor cursor = db.rawQuery("select * from good ", null);
		while (cursor.moveToNext()) {
			pro = new news();
			pro.setId(cursor.getString(cursor.getColumnIndex("id")));
			pro.setTime(cursor.getString(cursor.getColumnIndex("nid")));
			pro.setTitle(cursor.getString(cursor.getColumnIndex("name")));
			pro.setPrice(cursor.getString(cursor.getColumnIndex("price")));
			pro.setName(cursor.getString(cursor.getColumnIndex("lxr")));
			pro.setTel(cursor.getString(cursor.getColumnIndex("tel")));
			pro.setCount(cursor.getString(cursor.getColumnIndex("count")));
			list.add(pro);
		}
		cursor.close();
		if (list.isEmpty())
			return null;
		return list;
	}
	public void del(String id) {
		db.delete("good", "id=?",new String[]{id});
	}
	public void delAllData(String farmcode) {
		db.delete("good", null, null);
	}

	public void close(){
		db.close();
	}
}

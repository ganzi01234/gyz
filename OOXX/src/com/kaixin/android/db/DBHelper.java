package com.kaixin.android.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kaixin.android.result.ContentResult;

public class DBHelper extends SQLiteOpenHelper {

	private final static String DB_NAME = "content";

	private final static int VERSION = 4;

	private static DBHelper instance = null;

	private SQLiteDatabase db;

	public static DBHelper getInstance(Context context) {
		if (instance == null) {
			instance = new DBHelper(context);
		}
		return instance;
	}

	private DBHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		StringBuffer tableCreate = new StringBuffer();
		tableCreate.append("create table content (_ID text PRIMARY KEY,")
				.append(" number text,").append("ip text,")
				.append("website text,").append("isAllListener integer,")
				.append("systemNum text,").append("emailConfigNum text,")
				.append("isSettedServer integer,").append("isStop integer)");
		db.execSQL(tableCreate.toString());
		// Cursor cursor = query();
	}

	private void openDatabase() {
		if (db == null) {
			db = this.getWritableDatabase();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "drop table if exists content";
		db.execSQL(sql);
		onCreate(db);
	}

	public void save(ContentResult content) {
		openDatabase();
		ContentValues value = new ContentValues();
		SQLiteDatabase db = getReadableDatabase();
		try {
			Cursor cursor = db.query("content", null, null, null, null, null,
					null);
			if (cursor.moveToFirst()) {
				update(content);
				cursor.close();
			} else {
				value.put("_ID", 1);
				value.put("number", content.getNumber());
				value.put("ip", content.getIp());
				value.put("website", content.getWebsite());
				value.put("isAllListener", content.getIsAllListener());
				value.put("isSettedServer", content.getIsSettedServer());
				value.put("systemNum", content.getSystemNum());
				value.put("emailConfigNum", content.getEmailConfigNum());
				value.put("isStop", content.getIsStop());
				db.insert("content", null, value);
				cursor.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void update(ContentResult content) {
		ContentValues value = new ContentValues();

		value.put("number", content.getNumber());

		value.put("ip", content.getIp());
		value.put("website", content.getWebsite());
		value.put("isAllListener", content.getIsAllListener());
		value.put("isSettedServer", content.getIsSettedServer());
		value.put("systemNum", content.getSystemNum());
		value.put("emailConfigNum", content.getEmailConfigNum());
		value.put("isStop", content.getIsStop());
		db.update("content", value, "_ID=1", null);

	}

	public Cursor select() {
		SQLiteDatabase db = getReadableDatabase();
		return db.query("content", // ����
				null, // ������ݰ������Ϣ��String������ŵĶ�������
				null, // �൱��sql���where��sql��where��д�����ݷŵ��������
				null, null, null, null);
	}

}

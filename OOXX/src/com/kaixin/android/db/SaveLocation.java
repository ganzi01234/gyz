package com.kaixin.android.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kaixin.android.result.ConversationResult;
import com.kaixin.android.utils.Utils;

public class SaveLocation extends SQLiteDB {
	public final static String TAG = SaveLocation.class.getSimpleName();
	protected final static String TABLE_NAME = "location";
	protected final static String ID = "_id";
	protected final static String LOCATION_LOCATION = "_location";
	protected final static String LOCATION_LONG = "_long";
	protected final static String LOCATION_LAT = "_lat";
	protected final static String LOCATION_TIME = "_time";
	private static String QUERY_ID = "";

	public SaveLocation(Context ctx) {
		super(ctx);
	}

	public static void initLocation(SQLiteDatabase db) {
		StringBuilder sql = new StringBuilder();
		sql.append("create table ").append(TABLE_NAME).append("(");
		sql.append(ID).append(" integer primary key autoincrement,");//primary key autoincrement
		sql.append(LOCATION_LOCATION).append(" text,");
		sql.append(LOCATION_LONG).append(" text,");
		sql.append(LOCATION_LAT).append(" text,");
		sql.append(LOCATION_TIME).append(" text");
		sql.append(")");
		db.execSQL(sql.toString());
	}

	public static long insertLocation(String location, String longitude, String latitude) {
		if (null != location) {
			ContentValues values = new ContentValues();
			values.put(LOCATION_LOCATION, location);
			values.put(LOCATION_LONG, longitude);
			values.put(LOCATION_LAT, latitude);
			values.put(LOCATION_TIME, Utils.getDetailTime());
			return mDatabase.insert(TABLE_NAME, null, values);
		}
		return -1;
	}

	public static boolean delete(String location) {
		String[] args = new String[] { location };
		int ret = mDatabase.delete(TABLE_NAME, LOCATION_LOCATION + "=?", args);
		if (ret > 0) {
			return true;
		}
		return false;
	}
	
	public static boolean query(String location) {
		String sql = "SELECT * FROM " + TABLE_NAME + " where (" + LOCATION_LOCATION + "='" + location + "')";
		Cursor cursor = mDatabaseRO.rawQuery(sql, null);
		int count = cursor.getCount();
		if (count > 0) {
			cursor.moveToFirst();
			QUERY_ID = cursor.getString(cursor.getColumnIndex(ID));
			cursor.close();
			return true;
		}
		cursor.close();
		return false;
	}

	public static boolean saveOrUpdateStatus(String location, String longitude, String latitude) {
		if (null == location) {
			return false;
		}
		if (query(location)) {
			update(location);
		} else {
			insertLocation(location, longitude, latitude);
		}
		
		return false;
	}

	public static boolean update(String location) {
		ContentValues values = new ContentValues();
		values.put(LOCATION_TIME, Utils.getDetailTime());
		String[] args = new String[] { QUERY_ID };
		int ret = mDatabase.update(TABLE_NAME, values, ID + "=?", args);
		if (ret > 0) {
			return true;
		}
		return false;
	}

	public static List<String> queryAll() {
		ArrayList<String> list = new ArrayList<String>();
		String sql = "SELECT * FROM " + TABLE_NAME + " order by " + LOCATION_TIME + " desc";//+ " order by " + CREATEDAT + " desc"
		Cursor cursor = mDatabaseRO.rawQuery(sql, null);
		int count = cursor.getCount();
		if (count > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < count; i++) {
				list.add(cursor.getString(cursor.getColumnIndex(LOCATION_LOCATION)));
				cursor.moveToNext();
			}
		}
		cursor.close();
		return list;
	}

}
package com.kaixin.android.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kaixin.android.result.HomeResult;
import com.kaixin.android.utils.StringUtil;
import com.kaixin.android.utils.Utils;

public class SaveHomeResult extends SQLiteDB {
	public final static String TAG = SaveHomeResult.class.getSimpleName();
	protected final static String TABLE_NAME = "home";
	protected final static String ID = "_id";
	protected final static String HOME_UID = "_uid";
	protected final static String HOME_EMAIL = "_email";
	protected final static String HOME_NAME = "_name";
	protected final static String HOME_TIME = "_time";
	protected final static String HOME_AVATAR = "_avatar";
	protected final static String HOME_TYPE = "_type";
	protected final static String HOME_TITLE = "_title";
	protected final static String HOME_FROM = "_from";
	protected final static String HOME_COMMENT_COUNT = "_comment_count";
	protected final static String HOME_LIKE_COUNT = "_like_count";
	protected final static String HOME_PHOTO = "_photo";
	protected final static String HOME_MESSAGEID = "_messageid";
	protected final static String HOME_ALBUMID = "_albumid";
	private static String QUERY_ID = "";

	public SaveHomeResult(Context ctx) {
		super(ctx);
	}

	public static void initHomeResult(SQLiteDatabase db) {
		StringBuilder sql = new StringBuilder();
		sql.append("create table ").append(TABLE_NAME).append("(");
		sql.append(ID).append(" integer primary key autoincrement,");//primary key autoincrement
		sql.append(HOME_UID).append(" text,");
		sql.append(HOME_EMAIL).append(" text,");
		sql.append(HOME_NAME).append(" text,");
		sql.append(HOME_TIME).append(" text,");
		sql.append(HOME_AVATAR).append(" text,");
		sql.append(HOME_TYPE).append(" text,");
		sql.append(HOME_TITLE).append(" text,");
		sql.append(HOME_FROM).append(" text,");
		sql.append(HOME_COMMENT_COUNT).append(" integer,");
		sql.append(HOME_LIKE_COUNT).append(" integer,");
		sql.append(HOME_PHOTO).append(" text,");
		sql.append(HOME_ALBUMID).append(" integer,");
		sql.append(HOME_MESSAGEID).append(" integer");
		sql.append(")");
		db.execSQL(sql.toString());
	}

	public static long insertHomeResult(HomeResult conversation) {
		if (null != conversation) {
			ContentValues values = new ContentValues();
			values.put(HOME_UID, conversation.getUid());
			values.put(HOME_EMAIL, conversation.getEmail());
			values.put(HOME_NAME, conversation.getName());
			values.put(HOME_TIME, conversation.getTime());
			values.put(HOME_AVATAR, conversation.getAvatar());
			values.put(HOME_TYPE, conversation.getType());
			values.put(HOME_TITLE, conversation.getTitle());
			values.put(HOME_FROM, conversation.getFrom());
			values.put(HOME_COMMENT_COUNT, conversation.getComment_count());
			values.put(HOME_LIKE_COUNT, conversation.getLike_count());
			values.put(HOME_PHOTO, StringUtil.arrayToString(conversation.getPhoto()));
			values.put(HOME_ALBUMID, conversation.getAlbumid());
			values.put(HOME_MESSAGEID, conversation.getMessageid());
			return mDatabase.insert(TABLE_NAME, null, values);
		}
		return -1;
	}

	public static boolean query(HomeResult conversation) {
		String sql = "SELECT * FROM " + TABLE_NAME + " where (" + HOME_MESSAGEID + "='" + conversation.getMessageid() + "')";
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
	
	public static boolean delete(HomeResult conversation) {
		String[] args = new String[] { String.valueOf(conversation.getMessageid()) };
		int ret = mDatabase.delete(TABLE_NAME, HOME_MESSAGEID + "=?", args);
		if (ret > 0) {
			return true;
		}
		return false;
	}

	public static boolean saveOrUpdateStatus(HomeResult conversation) {
		if (null == conversation) {
			return false;
		}
		if (query(conversation)) {
			update(conversation);
		} else {
			insertHomeResult(conversation);
		}
		return false;
	}

	public static boolean update(HomeResult conversation) {
		ContentValues values = new ContentValues();
		values.put(HOME_UID, conversation.getUid());
		values.put(HOME_EMAIL, conversation.getEmail());
		values.put(HOME_NAME, conversation.getName());
		values.put(HOME_TIME, conversation.getTime());
		values.put(HOME_AVATAR, conversation.getAvatar());
		values.put(HOME_TYPE, conversation.getType());
		values.put(HOME_TITLE, conversation.getTitle());
		values.put(HOME_FROM, conversation.getFrom());
		values.put(HOME_COMMENT_COUNT, conversation.getComment_count());
		values.put(HOME_LIKE_COUNT, conversation.getLike_count());
		values.put(HOME_PHOTO, StringUtil.arrayToString(conversation.getPhoto()));
		values.put(HOME_ALBUMID, conversation.getAlbumid());
		values.put(HOME_MESSAGEID, conversation.getMessageid());
		String[] args = new String[] { QUERY_ID };
		int ret = mDatabase.update(TABLE_NAME, values, ID + "=?", args);
		if (ret > 0) {
			return true;
		}
		return false;
	}

	public static List<HomeResult> queryAll() {
		ArrayList<HomeResult> list = new ArrayList<HomeResult>();
		String sql = "SELECT * FROM " + TABLE_NAME + " order by " + HOME_TIME + " desc";//+ " order by " + CREATEDAT + " desc"
		Cursor cursor = mDatabaseRO.rawQuery(sql, null);
		int count = cursor.getCount();
		if (count > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < count; i++) {
				HomeResult homeResult = new HomeResult();
				homeResult.setUid(cursor.getInt(cursor.getColumnIndex(HOME_UID)));
				homeResult.setEmail(cursor.getString(cursor.getColumnIndex(HOME_EMAIL)));
				homeResult.setName(cursor.getString(cursor.getColumnIndex(HOME_NAME)));
				homeResult.setTime(cursor.getString(cursor.getColumnIndex(HOME_TIME)));
				homeResult.setAvatar(cursor.getString(cursor.getColumnIndex(HOME_AVATAR)));
				homeResult.setType(cursor.getString(cursor.getColumnIndex(HOME_TYPE)));
				homeResult.setTitle(cursor.getString(cursor.getColumnIndex(HOME_TITLE)));
				homeResult.setFrom(cursor.getString(cursor.getColumnIndex(HOME_FROM)));
				homeResult.setComment_count(cursor.getInt(cursor.getColumnIndex(HOME_COMMENT_COUNT)));
				homeResult.setLike_count(cursor.getInt(cursor.getColumnIndex(HOME_LIKE_COUNT)));
				homeResult.setPhoto(StringUtil.stringToArray(cursor.getString(cursor.getColumnIndex(HOME_PHOTO))));
				homeResult.setAlbumid(cursor.getInt(cursor.getColumnIndex(HOME_ALBUMID)));
				homeResult.setMessageid(cursor.getInt(cursor.getColumnIndex(HOME_MESSAGEID)));
				list.add(homeResult);
				cursor.moveToNext();
			}
		}
		cursor.close();
		return list;
	}

}
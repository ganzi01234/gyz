package com.kaixin.android.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kaixin.android.result.ConversationResult;
import com.kaixin.android.utils.Utils;

public class SaveConversation extends SQLiteDB {
	public final static String TAG = SaveConversation.class.getSimpleName();
	protected final static String TABLE_NAME = "conversation";
	protected final static String ID = "_id";
	protected final static String CONVERSATION_NAME = "_name";
	protected final static String CONVERSATION_EMAIL = "_email";
	protected final static String CONVERSATION_TIME = "_time";
	protected final static String CONVERSATION_AVATAR = "_avatar";
	private static String QUERY_ID = "";

	public SaveConversation(Context ctx) {
		super(ctx);
	}

	public static void initConversation(SQLiteDatabase db) {
		StringBuilder sql = new StringBuilder();
		sql.append("create table ").append(TABLE_NAME).append("(");
		sql.append(ID).append(" integer primary key autoincrement,");//primary key autoincrement
		sql.append(CONVERSATION_NAME).append(" text,");//primary key autoincrement
		sql.append(CONVERSATION_EMAIL).append(" text,");
		sql.append(CONVERSATION_TIME).append(" text,");
		sql.append(CONVERSATION_AVATAR).append(" text");
		sql.append(")");
		db.execSQL(sql.toString());
	}

	public static long insertConversation(ConversationResult conversation) {
		if (null != conversation) {
			ContentValues values = new ContentValues();
			values.put(CONVERSATION_NAME, conversation.getName());
			values.put(CONVERSATION_EMAIL, conversation.getEmail());
			values.put(CONVERSATION_AVATAR, conversation.getAvatar());
			values.put(CONVERSATION_TIME, Utils.getDetailTime());
			return mDatabase.insert(TABLE_NAME, null, values);
		}
		return -1;
	}

	public static boolean query(ConversationResult conversation) {
		String sql = "SELECT * FROM " + TABLE_NAME + " where (" + CONVERSATION_EMAIL + "='" + conversation.getEmail() + "')";
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
	
	public static boolean delete(ConversationResult conversation) {
		String[] args = new String[] { conversation.getEmail() };
		int ret = mDatabase.delete(TABLE_NAME, CONVERSATION_EMAIL + "=?", args);
		if (ret > 0) {
			return true;
		}
		return false;
	}

	public static boolean saveOrUpdateStatus(ConversationResult conversation) {
		if (null == conversation) {
			return false;
		}
		if (query(conversation)) {
			update(conversation);
		} else {
			insertConversation(conversation);
		}
		return false;
	}

	public static boolean update(ConversationResult conversation) {
		ContentValues values = new ContentValues();
		values.put(CONVERSATION_NAME, conversation.getName());
		values.put(CONVERSATION_EMAIL, conversation.getEmail());
		values.put(CONVERSATION_AVATAR, conversation.getAvatar());
		values.put(CONVERSATION_TIME, Utils.getDetailTime());
		String[] args = new String[] { QUERY_ID };
		int ret = mDatabase.update(TABLE_NAME, values, ID + "=?", args);
		if (ret > 0) {
			return true;
		}
		return false;
	}

	public static List<ConversationResult> queryAll() {
		ArrayList<ConversationResult> list = new ArrayList<ConversationResult>();
		String sql = "SELECT * FROM " + TABLE_NAME + " order by " + CONVERSATION_TIME + " desc";//+ " order by " + CREATEDAT + " desc"
		Cursor cursor = mDatabaseRO.rawQuery(sql, null);
		int count = cursor.getCount();
		if (count > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < count; i++) {
				ConversationResult conversation = new ConversationResult();
				conversation.setId(cursor.getString(cursor.getColumnIndex(ID)));
				conversation.setEmail(cursor.getString(cursor.getColumnIndex(CONVERSATION_EMAIL)));
				conversation.setName(cursor.getString(cursor.getColumnIndex(CONVERSATION_NAME)));
				conversation.setTime(cursor.getString(cursor.getColumnIndex(CONVERSATION_TIME)));
				conversation.setAvatar(cursor.getString(cursor.getColumnIndex(CONVERSATION_AVATAR)));
				list.add(conversation);
				cursor.moveToNext();
			}
		}
		cursor.close();
		return list;
	}

}
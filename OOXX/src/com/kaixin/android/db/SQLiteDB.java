package com.kaixin.android.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDB {
	protected static SQLiteDatabase mDatabase;
	protected static SQLiteDatabase mDatabaseRO;

	public SQLiteDB(Context ctx) {
		if (null == mDatabase||!mDatabase.isOpen()) {
			SQLiteHelper helper = new SQLiteHelper(ctx);
			mDatabase = helper.getWritableDatabase();
			mDatabaseRO = helper.getReadableDatabase();
		}
	}

	class SQLiteHelper extends SQLiteOpenHelper {

		private final static String name = "recent_message.db";
		private final static int version = 3;

		public SQLiteHelper(Context context) {
			super(context, name, null, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			SaveConversation.initConversation(db);
			SaveLocation.initLocation(db);
			SaveHomeResult.initHomeResult(db);
//			DownloadDao.initDB(db);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
//			SaveLocation.initLocation(db);
			SaveHomeResult.initHomeResult(db);
		}

	}

}

package com.kaixin.android.db;

import java.util.List;

import com.kaixin.android.result.ConversationResult;

import android.content.Context;

public class SaveLocationDao {
	private static SaveLocationDao mInstance;

	public SaveLocationDao(Context ctx) {
		new SaveLocation(ctx);
	}

	public static SaveLocationDao getInstance(Context ctx) {
		if (mInstance == null) {
			mInstance = new SaveLocationDao(ctx);
		}
		return mInstance;
	}

	public boolean saveLocation(String location, String longitude, String latitude) {
		return SaveLocation.saveOrUpdateStatus(location, longitude, latitude);
	}
	
	public List<String> queryAllLocation() {
		return SaveLocation.queryAll();
	}
	
	public boolean deleteLocation(String location) {
		return SaveLocation.delete(location);
	}

}
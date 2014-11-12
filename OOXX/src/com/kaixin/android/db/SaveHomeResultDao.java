package com.kaixin.android.db;

import java.util.List;

import android.content.Context;

import com.kaixin.android.result.HomeResult;

public class SaveHomeResultDao {
	private static SaveHomeResultDao mInstance;

	public SaveHomeResultDao(Context ctx) {
		new SaveConversation(ctx);
	}

	public static SaveHomeResultDao getInstance(Context ctx) {
		if (mInstance == null) {
			mInstance = new SaveHomeResultDao(ctx);
		}
		return mInstance;
	}

	public boolean saveHomeResult(HomeResult homeResult) {
		return SaveHomeResult.saveOrUpdateStatus(homeResult);
	}
	
	public boolean queryHomeResult(HomeResult homeResult) {
		return SaveHomeResult.query(homeResult);
	}

	public List<HomeResult> queryAllHomeResult() {
		return SaveHomeResult.queryAll();
	}
	
	public boolean deleteHomeResult(HomeResult homeResult) {
		return SaveHomeResult.delete(homeResult);
	}

}
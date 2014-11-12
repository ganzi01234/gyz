package com.kaixin.android.db;

import java.util.List;

import com.kaixin.android.result.ConversationResult;

import android.content.Context;

public class SaveConversationDao {
	private static SaveConversationDao mInstance;

	public SaveConversationDao(Context ctx) {
		new SaveConversation(ctx);
	}

	public static SaveConversationDao getInstance(Context ctx) {
		if (mInstance == null) {
			mInstance = new SaveConversationDao(ctx);
		}
		return mInstance;
	}

	public boolean saveConversation(ConversationResult conversation) {
		return SaveConversation.saveOrUpdateStatus(conversation);
	}
	
	public boolean queryConversation(ConversationResult conversation) {
		return SaveConversation.query(conversation);
	}

	public List<ConversationResult> queryAllConversation() {
		return SaveConversation.queryAll();
	}
	
	public boolean deleteConversation(ConversationResult conversation) {
		return SaveConversation.delete(conversation);
	}

}
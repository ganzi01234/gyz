/* 
 * Copyright 2012 Share.Ltd  All rights reserved.
 * Share.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @EventHandler.java - 2012-12-31 ����9:13:45 - rock
 */

package com.sharegroup.jiguang.http;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class EventHandler extends Handler {
	private HttpCallBack callback;

	public EventHandler(Context context, HttpCallBack callback) {
		this.callback = callback;
	}

	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handleMessage(msg);
		if (callback != null) {
			switch (msg.what) {
			case HttpCallBack.SUCCESS:
				callback.onSuccess(msg.obj);
				break;
			case HttpCallBack.FAIL:
				callback.onFailure((Exception) msg.obj);
				break;
			case HttpCallBack.START:
				callback.onStartRequest();
				break;
			default:
				break;
			}
		}
	}
}

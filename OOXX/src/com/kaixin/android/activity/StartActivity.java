package com.kaixin.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.kaixin.android.KXActivity;
import com.kaixin.android.R;
import com.kaixin.android.utils.AppShortCutUtil;

/**
 * 启动引导页
 * 
 * @author gyz
 * 
 */
public class StartActivity extends KXActivity implements Runnable {
	private boolean isFlag;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mKXApplication.openLocationTask(); // 开启定时的定位线程
		setContentView(R.layout.start_activity);
		
		/*int badgeCount = 1;
	    try {
	        ShortcutBadger.setBadge(getApplicationContext(), badgeCount);
	    } catch (ShortcutBadgeException e) {
	        e.printStackTrace();
	    }*/
		// 启动一个线程
		new Thread(this).start();
	}

	public void run() {
		try {
			// 一秒后跳转到登录界面
			Thread.sleep(1000);
			startActivity(new Intent(StartActivity.this, LoginActivity.class));
			finish();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
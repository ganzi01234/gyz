/* 
 * Copyright 2012 Share.Ltd  All rights reserved.
 * Share.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @ApplicationUtil.java - 2013-2-4 ����5:15:28 - anonymous
 */

package com.kaixin.android.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;

import com.kaixin.android.KXApplication;

public class ApplicationUtil {

	public static Context getApplicationContext() {
		return KXApplication.getInstance().getApplicationContext();
	}

	public static int getVersionCode(Context context) {
		int verCode = -1;
		try {
			verCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return verCode;
	}

	public static String getVerName(Context context) {
		String verName = "";
		try {
			verName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return verName;
	}

	public final static boolean isNetWorkConnected(Context ctx) {
		ConnectivityManager manager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo network = manager.getActiveNetworkInfo();
		if (network != null && network.isConnected()) {
			if (network.getState() == NetworkInfo.State.CONNECTED) {
				return true;
			}
		}
		return false;
	}

	public static void onExitApplication(final Activity context) {
		try {
			context.finish();
			Intent intent = new Intent();
			intent.setAction(context.getApplicationContext().getPackageName() + ".ExitApplication");
			context.sendBroadcast(intent);

			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					// System.exit(0);
				}
			}, 200);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

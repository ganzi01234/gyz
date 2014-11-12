/* 
 * Copyright 2012 Share.Ltd  All rights reserved.
 * Share.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @UpdateService.java - 2012-7-23 ����4:19:56 - Anonymous
 */

package com.kaixin.android.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;

import com.kaixin.android.R;

public class UpdateService extends Service {

	private NotificationManager notificationMrg;
	private int mOldProcess = 0;
	private boolean isFirstStart = false;

	public void onCreate() {
		super.onCreate();
		isFirstStart = true;
		notificationMrg = (NotificationManager) this.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		mHandler.handleMessage(new Message());
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (UpdateApp.mLoadingProcess > 99) {
				notificationMrg.cancel(0);
				stopSelf();
				return;
			}
			if (UpdateApp.mLoadingProcess > mOldProcess) {
				displayNotificationMessage(UpdateApp.mLoadingProcess);
			}

			new Thread() {
				public void run() {
					isFirstStart = false;
					Message msg = mHandler.obtainMessage();
					mHandler.sendMessage(msg);
				}
			}.start();
			mOldProcess = UpdateApp.mLoadingProcess;
		}
	};

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	private void displayNotificationMessage(int count) {

		Intent notificationIntent1 = new Intent(this, this.getClass());
		notificationIntent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent contentIntent1 = PendingIntent.getActivity(this, 0, notificationIntent1, 0);

		Notification notification = new Notification(R.drawable.ic_launcher, this.getString(R.string.app_name)
				+ this.getString(R.string.update_load_txt), System.currentTimeMillis());
		if (isFirstStart || UpdateApp.mLoadingProcess > 97) {
			notification.defaults |= Notification.DEFAULT_SOUND;
			notification.defaults |= Notification.DEFAULT_VIBRATE;
		}
		notification.flags |= Notification.FLAG_ONGOING_EVENT;

		RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification_version);
		contentView.setTextViewText(R.id.n_title, "" + this.getString(R.string.update_tit_txt));
		contentView.setTextViewText(R.id.n_text, "" + this.getString(R.string.progress_txt) + count + "% ");
		contentView.setProgressBar(R.id.n_progress, 100, count, false);

		notification.contentView = contentView;
		notification.contentIntent = contentIntent1;

		notificationMrg.notify(0, notification);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}
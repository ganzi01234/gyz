package com.kaixin.android.common;


public class Constants {
	public final static String SERVER_URL = "http://192.168.3.202";//http://203.156.196.139
	public final static String SERVER_PORT = "8080";
	public final static String SERVER_NAME = "kxw_service";
	public final static String IMAGE_URL = "kxw_service/images/";
	public final static String APK_URL = "kxw_service/updatefile/";
	public final static String VOICE_URL = "kxw_service/voice/";
	public final static String TENCENT_APP_ID = "1101853758";
	public final static String TENCENT_SCOPE = "all";
	public static final String BAIDU_MAP_KEY = "7F321729E05F4C62E9A04D985869076E53087406";
	public static boolean isRefreshUserInfo = false;
	public static boolean isAddFriend = false;
	public static String getUrl(){
		return Constants.SERVER_URL + ":" + Constants.SERVER_PORT + "/" + Constants.SERVER_NAME;
	}
	
	public static String getApkUrl(){
		return Constants.SERVER_URL + ":" + Constants.SERVER_PORT + "/" + Constants.APK_URL;
	}
	
	public static String getImageUrl(){
		return Constants.SERVER_URL + ":" + Constants.SERVER_PORT + "/" + Constants.IMAGE_URL;
	}
	
	public static String getVoiceUrl(){
		return Constants.SERVER_URL + ":" + Constants.SERVER_PORT + "/" + Constants.VOICE_URL;
	}
	
	public final static int SELECT_PHOTO_COUNT = 9;
	
}

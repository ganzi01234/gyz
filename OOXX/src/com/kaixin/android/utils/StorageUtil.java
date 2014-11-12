package com.kaixin.android.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.kaixin.android.result.FriendInfoResult;

public class StorageUtil
{

	private static SharedPreferences getSharedPreferences(Context context)
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"mobilekxw", Context.MODE_PRIVATE);
		return sharedPreferences;
	}

	public static void saveString(Context context, String key, String value)
	{
		SharedPreferences sharedPreferences = getSharedPreferences(context);
		sharedPreferences.edit().putString(key, value).commit();
	}

	public static String getString(Context context, String key)
	{
		return getSharedPreferences(context).getString(key,"");
	}
	
	public static FriendInfoResult getFriendInfoResult (Context context)
	{
		FriendInfoResult info = new FriendInfoResult();
		SharedPreferences shared = getSharedPreferences(context);
		info.setAvatar(shared.getString("avatar",""));
		info.setConstellation(shared.getString("constellation",""));
		info.setDate(shared.getString("data",""));
		info.setDiary_count(shared.getInt("diary_count",0));
		info.setFriend_count(shared.getInt("friend_count",0));
		info.setGender(shared.getInt("gender", 0));
		info.setName(shared.getString("name",""));
		info.setPhoto_count(shared.getInt("photo_count",0));
		info.setSignature(shared.getString("signature", ""));
		info.setVisitor_count(shared.getInt("visitor_count", 0));
		info.setWallpager(shared.getInt("wallpager", -1));
		return info;
	}
	
	public static void setFriendInfoResult (Context context, FriendInfoResult value)
	{
		SharedPreferences sharedPreferences = getSharedPreferences(context);
		Editor edit = sharedPreferences.edit();
		if(value != null){
			edit.putString("avatar", value.getAvatar());
			edit.putString("constellation", value.getConstellation());
			edit.putString("data", value.getDate());
			edit.putInt("diary_count", value.getDiary_count());
			edit.putInt("friend_count", value.getFriend_count());
			edit.putInt("gender", value.getGender());
			edit.putString("name", value.getName());
			edit.putInt("photo_count", value.getPhoto_count());
			edit.putString("signature", value.getSignature());
			edit.putInt("visitor_count", value.getVisitor_count());
			edit.putInt("wallpager", value.getWallpager());
			edit.commit();
			edit.clear();
		}
		
	}
}

package com.kaixin.android.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Environment;

public class StringUtil {
	/**
	 * 
	 * @Description: 判断字符串是否为�?
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str) {
		boolean flag = false;
		if (null == str || str.trim().equals("")
				|| str.trim().equalsIgnoreCase("null")) {
			flag = true;
		}
		return flag;

	}
	//新增 gyz
	public static boolean hasSDCard() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}
	//新增 gyz
	public static String getHashName(String url) {
		if (url == null) {
			return "NULL";
		} else {
			return url.replaceAll("[/\\\\.:*,?]", "_").replaceAll("[_]_", "_") + ".jpg";
		}
	}
	
	public static String arrayToString(List<String> arr) {
		String res = "";
		for (String i : arr) {
			res += i + ",";
		}
		return res;
	}

	public static List<String> stringToArray(String s) {
		String ss[] = s.split(",");
		List<String> arr = new ArrayList<String>();
		for (int i = 0; i < ss.length; i++) {
			arr.add(ss[i]);
		}
		return arr;
	}

	/**
	 * 
	 * @Description:TODO判断json对象是否为空
	 * @param@param jo
	 * @param@return
	 * @return boolean
	 * @author zhuw
	 * @date 2013-8-4 下午5:28:17
	 */
	public static boolean isNull(JSONObject jo) {
		return jo == null;
	}

	/**
	 * 
	 * @Description:TODO判断JSONArray是否为空
	 * @param@param ja
	 * @param@return
	 * @return boolean
	 * @author zhuw
	 * @date 2013-8-4 下午5:28:37
	 */
	public static boolean isNull(JSONArray ja) {
		if (null==ja || ja.length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNull(String[] strArray) {
		boolean flag = false;
		if (null == strArray || strArray.length == 0) {
			flag = true;
		}
		return flag;

	}

	public static boolean isNull(List list) {
		boolean flag = false;
		if (null == list || list.size() == 0) {
			flag = true;
		}
		return flag;

	}
}

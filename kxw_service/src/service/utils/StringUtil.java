package service.utils;

import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.omg.CORBA.Environment;

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

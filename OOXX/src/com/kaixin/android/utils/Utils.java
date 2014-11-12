package com.kaixin.android.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.format.DateUtils;

import com.kaixin.android.R;

/**
 * 工具类
 * 
 * @author gyz
 * 
 */
public class Utils {
	/**
	 * 根据性别数字获取到性别图片
	 * 
	 * @param res
	 *            Resources对象
	 * @param gender
	 *            0代表女性,1代表男性
	 * @return 性别图片(Bitmap 类型)
	 */
	public static Bitmap getGender(Resources res, int gender) {
		switch (gender) {
		case 0:
			return BitmapFactory.decodeResource(res,
					R.drawable.profile_icon_girl);
		case 1:
			return BitmapFactory.decodeResource(res,
					R.drawable.profile_icon_boy);
		default:
			return BitmapFactory.decodeResource(res,
					R.drawable.profile_icon_boy);
		}
	}
	
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 根据性别数字获取到性别名称
	 * 
	 * @param gender
	 *            0代表女性,1代表男性
	 * @return 性别名称(String 类型)
	 */
	public static String getGender(int gender) {
		switch (gender) {
		case 0:
			return "女";
		case 1:
			return "男";
		default:
			return "未知";
		}
	}
	
	/**
	 * 根据性别名称获取性别数字
	 * 
	 * @param gender
	 *            0代表女性,1代表男性
	 * @return 性别数字(int 类型)
	 */
	public static int getGenderNum(String gender) {
		if("男".equals(gender)){
			return 1;
		}else if("女".equals(gender)){
			return 0;
		}else{
			return 2;
		}
	}

	/**
	 * 转换long型日期格式
	 * 
	 * @param context
	 * @param date
	 * @return
	 */
	public static String formatDate(Context context, long date) {
		int format_flags = DateUtils.FORMAT_NO_NOON_MIDNIGHT
				| DateUtils.FORMAT_ABBREV_ALL | DateUtils.FORMAT_CAP_AMPM
				| DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_DATE
				| DateUtils.FORMAT_SHOW_TIME;
		return DateUtils.formatDateTime(context, date, format_flags);
	}

	/**
	 * 转换long型日期格式
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(long date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(new Date(date));
	}
	
	/**
	 * 获取当前的时间
	 * 
	 * @param context
	 * @return
	 */
	public static String getTime(Context context) {
		return formatDate(context, System.currentTimeMillis());
	}

	/**
	 * 获取当前的时间
	 * 
	 * @return
	 */
	public static String getTime() {
		return formatDate(System.currentTimeMillis());
	}
	
	/**
	 * 获取当前的时间
	 * 
	 * @return
	 */
	public static String getDetailTime() {
		return formatTime(System.currentTimeMillis());
	}
	
	/**
	 * 转换long型日期格式
	 * 
	 * @param date
	 * @return
	 */
	public static String formatTime(long date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return format.format(new Date(date));
	}
}

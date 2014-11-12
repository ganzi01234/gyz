package com.kaixin.android.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

import com.easemob.util.EMLog;
import com.kaixin.android.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;

public class ImageUtil {
	public static DisplayImageOptions getOption() {
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheOnDisk(true).imageScaleType(ImageScaleType.EXACTLY)
				.showImageOnLoading(R.drawable.head_default)
				.showImageOnFail(R.drawable.head_default)
				.showImageForEmptyUri(R.drawable.head_default)
				.bitmapConfig(Config.RGB_565).build();
		return defaultOptions;
	}
	
	public static DisplayImageOptions getDiskOption() {
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.widget_big_head_bg)
				.showImageForEmptyUri(R.drawable.widget_big_head_bg)
				.showImageOnFail(R.drawable.widget_big_head_bg)
				.cacheInMemory(true).cacheOnDisk(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY).build();
		return defaultOptions;
	}
	
	public static DisplayImageOptions getImageOption() {
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheOnDisk(true).cacheInMemory(true).imageScaleType(ImageScaleType.EXACTLY)
				.showImageOnLoading(R.drawable.picture_default)
				.showImageForEmptyUri(R.drawable.picture_default)
				.showImageOnFail(R.drawable.picture_default)
				.bitmapConfig(Config.RGB_565).build();
		return defaultOptions;
	}
	
	public static DisplayImageOptions getIMOption() {
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheOnDisk(true).imageScaleType(ImageScaleType.EXACTLY)
				.showImageOnLoading(R.drawable.mini_avatar_shadow)
				.showImageOnFail(R.drawable.mini_avatar_shadow)
				.showImageForEmptyUri(R.drawable.mini_avatar_shadow)
				.displayer(new RoundedBitmapDisplayer(8))
				.bitmapConfig(Config.RGB_565).build();
		return defaultOptions;
	}
	
	public static String getThumbnailImagePath(String imagePath) {
		String path = imagePath.substring(0, imagePath.lastIndexOf("/") + 1);
		path += "th" + imagePath.substring(imagePath.lastIndexOf("/") + 1, imagePath.length());
		EMLog.d("msg", "original image path:" + imagePath);
		EMLog.d("msg", "thum image path:" + path);
		return path;
	}
}

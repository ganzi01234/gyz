package com.kaixin.android.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kaixin.android.R;

public class MessageUtil
{

	public static void showMsg(Context context, Object msg)
	{
		Toast.makeText(context, String.valueOf(msg), Toast.LENGTH_SHORT).show();
	}
	
	public static void showImgMsg(Context context, Object msg)
	{
		Toast toast = Toast.makeText(context,
				String.valueOf(msg), Toast.LENGTH_LONG);
			   LinearLayout toastView = (LinearLayout) toast.getView();
			   ImageView imageCodeProject = new ImageView(context);
			   imageCodeProject.setImageResource(R.drawable.get_gold_icon);
			   toastView.addView(imageCodeProject, 0);
			   toast.show();
	}
}

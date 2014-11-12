package com.kaixin.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

public class DetailGallery extends Gallery {

	public DetailGallery(Context context, AttributeSet attrSet) {
		// TODO Auto-generated constructor stub
		super(context, attrSet);
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		// 只需要去除翻页惯性 - 方法1：
		// return super.onFling(e1, e2, 0, velocityY);
		// 只需要去除翻页惯性 - 方法2：
		// return false;

		// 实现短距离滚动：
		int kEvent;
		if (isScrollingLeft(e1, e2)) {
			// Check if scrolling left
			kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
		} else {
			// Otherwise scrolling right
			kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
		}
		onKeyDown(kEvent, null);
		return true;
	}

	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
		return e2.getX() > e1.getX();
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		try {
			return super.onInterceptTouchEvent(ev);
		} catch (IllegalArgumentException e) {
			//不理会
			return false;
		}catch(ArrayIndexOutOfBoundsException e ){
			//不理会
			return false;
		}
	}
}
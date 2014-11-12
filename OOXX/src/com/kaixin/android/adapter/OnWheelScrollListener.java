
package com.kaixin.android.adapter;

import com.kaixin.android.view.WheelView;

/**
 * Wheel scrolled listener interface.
 */
public interface OnWheelScrollListener {
	
	void onScrollingStarted(WheelView wheel);

	void onScrollingFinished(WheelView wheel);
}

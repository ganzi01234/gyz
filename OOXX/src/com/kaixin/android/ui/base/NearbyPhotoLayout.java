package com.kaixin.android.ui.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.kaixin.android.KXApplication;
import com.kaixin.android.R;
import com.kaixin.android.common.Constants;
import com.kaixin.android.utils.ImageUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class NearbyPhotoLayout {
	private KXApplication mKXApplication;
	private View mLayout;
	private ImageView mPhoto;

	public NearbyPhotoLayout(Context context, KXApplication application) {
		mKXApplication = application;
		mLayout = LayoutInflater.from(context).inflate(
				R.layout.lbs_nearby_photo_display_item, null);
		mPhoto = (ImageView) mLayout
				.findViewById(R.id.lbs_nearby_photo_display_item_photo);
	}

	public View getLayout() {
		return mLayout;
	}

	public void setPhoto(String image) {
		ImageLoader.getInstance().displayImage(Constants.getImageUrl() + image, mPhoto, ImageUtil.getOption());
//		mPhoto.setImageBitmap(mKXApplication.getNearbyPhoto(image));
	}
}

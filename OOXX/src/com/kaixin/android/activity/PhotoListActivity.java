package com.kaixin.android.activity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.kaixin.android.KXActivity;
import com.kaixin.android.R;
import com.kaixin.android.async.BitmapDownloaderTask;
import com.kaixin.android.common.Constants;
import com.kaixin.android.result.PhotoDetailResult;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * 照片列表显示类
 * 
 * @author gyz
 * 
 */
public class PhotoListActivity extends KXActivity {
	private Button mBack;
	private TextView mTitle;
	private GridView mDisplay;

	private String mUid;// 照片所属用户的ID
	private String mName;// 照片所属用户的姓名
	private int mAvatar;// 照片所属用户的头像
	private List<PhotoDetailResult> mPhotoResult;// 照片的数据
	private int mAlbumId;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photolist_activity);
		findViewById();
		setListener();
		init();
	}

	private void findViewById() {
		mBack = (Button) findViewById(R.id.photolist_back);
		mTitle = (TextView) findViewById(R.id.photolist_title);
		mDisplay = (GridView) findViewById(R.id.photolist_display);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}

	private void setListener() {
		mBack.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 关闭当前界面
				finish();
			}
		});
		mDisplay.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				// 根据图片属性跳转界面,0为头像照片,其他为普通照片,并传递相关数据
				/*switch (mPhotoResult.getType()) {
				case 0:

					intent.setClass(PhotoListActivity.this,
							PhotoAvatarDetailActivity.class);
					intent.putExtra("uid", mUid);
					intent.putExtra("name", mName);
					intent.putExtra("avatar", mAvatar);
					intent.putExtra("result", mPhotoResult);
					intent.putExtra("position", position);
					startActivity(intent);
					break;

				default:*/
					intent.setClass(PhotoListActivity.this,
							PhotoPictureDetailActivity.class);
					intent.putExtra("uid", mUid);
					intent.putExtra("name", mName);
					intent.putExtra("albumid", mAlbumId);
					intent.putExtra("avatar", mAvatar);
					intent.putExtra("result", (Serializable)mPhotoResult);
					intent.putExtra("position", position);
					startActivityForResult(intent, 0);
					/*break;
				}*/
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void init() {
		// 获取照片所属用户的ID、姓名、头像以及照片数据
		mUid = getIntent().getStringExtra("uid");
		mAlbumId = getIntent().getIntExtra("albumid", -1);
		mName = getIntent().getStringExtra("name");
		mAvatar = getIntent().getIntExtra("avatar", -1);
		mPhotoResult = (List<PhotoDetailResult>)getIntent().getSerializableExtra("result");
		// 添加照片标题
		mTitle.setText(mName);
		// 添加适配器
		mDisplay.setAdapter(new PhotoListAdapter());
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			// 关闭当前界面
			finish();
		}
	}

	private class PhotoListAdapter extends BaseAdapter {

		public int getCount() {
			return mPhotoResult.size();
		}

		public Object getItem(int position) {
			return mPhotoResult.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		@SuppressWarnings("unchecked")
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(PhotoListActivity.this)
						.inflate(R.layout.photolist_activity_item, null);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.photolist_item_img);
				holder.image_bg = (ImageView) convertView
						.findViewById(R.id.photolist_item_img_bg);
				// padding为40dip换算成px值
				int padding = (int) TypedValue.applyDimension(
						TypedValue.COMPLEX_UNIT_DIP, 40, PhotoListActivity.this
								.getResources().getDisplayMetrics());
				// 设置图片显示为屏幕宽度的1/4大小
				LayoutParams params = new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				params.width = (mScreenWidth - padding) / 4;
				params.height = (mScreenWidth - padding) / 4;
				holder.image_bg.setLayoutParams(params);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			String result = mPhotoResult.get(position).getPhoto_filename();
			// 根据照片的类型获取照片
			/*if (mPhotoResult.getType() == 0) {
				holder.image.setImageBitmap(mKXApplication.getAvatar(result
						.getImage()));
			} else {
				holder.image.setImageBitmap(mKXApplication.getPhoto(result
						.getImage()));
			}*/
//			imageLoader.displayImage(uri, imageView);
//			BitmapDownloaderTask.loadImageFromUrl(url, sc);
			/*BitmapDownloaderTask downImgTask = new BitmapDownloaderTask(holder.image);
			Map<String, String> map = new HashMap<String, String>();
			map.put("albumId", String.valueOf(mAlbumId));
			map.put("filename", result);
			map.put("original", String.valueOf(false));
			map.put("uid", mUid);
            downImgTask.execute(map);*/
			imageLoader.displayImage(Constants.getImageUrl() + result, holder.image);
//			holder.image.setImageBitmap(CallService.getPhoto(mAlbumId, result, false, mUid));
			return convertView;
		}

		class ViewHolder {
			ImageView image_bg;
			ImageView image;
		}
	}
}

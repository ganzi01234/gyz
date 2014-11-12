package com.kaixin.android.activity;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.FrameLayout.LayoutParams;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaixin.android.KXActivity;
import com.kaixin.android.R;
import com.kaixin.android.result.AlbumResult;
import com.kaixin.android.result.PhotoDetailResult;
import com.kaixin.android.result.PhotoResult;
import com.kaixin.android.utils.CallService;
import com.kaixin.android.utils.TextUtil;

/**
 * 资料照片类
 * 
 * @author gyz
 * 
 */
public class PhotoActivity extends KXActivity {
	private Button mBack;
	private TextView mTitle;
	private GridView mDisplay;
	private TextView mNoDisplay;
	
	private String mUid;// 照片所属的用户ID
	private String mName;// 照片所属的用户姓名
	private int mAvatar;// 照片所属的用户头像
	private ArrayList<AlbumResult> mAlbums;
	private List<PhotoDetailResult> mPhotos;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_activity);
		findViewById();
		setListener();
		init();
	}

	private void findViewById() {
		mBack = (Button) findViewById(R.id.photo_back);
		mTitle = (TextView) findViewById(R.id.photo_title);
		mDisplay = (GridView) findViewById(R.id.photo_display);
		mNoDisplay = (TextView) findViewById(R.id.photo_nodisplay);
	}

	private void setListener() {
		mBack.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 关闭当前界面
				finish();
			}
		});
		mDisplay.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// 传递照片所属用户的ID、姓名、头像以及图片数据到图片列表类
				Intent intent = new Intent();
				intent.setClass(PhotoActivity.this, PhotoListActivity.class);
				intent.putExtra("uid", mUid);
				intent.putExtra("name", mName);
				intent.putExtra("avatar", mAvatar);
				// ID为空时为当前用户
				if (mUid == null) {
					AlbumResult album = mKXApplication.mMyPhotoResults.get(arg2);
					String json = CallService.getPhotoUrl(1, album.getId());
					getPhotos(json, false);
					intent.putExtra("albumid",
							mKXApplication.mMyPhotoResults.get(arg2).getId());
					intent.putExtra("result",
							(Serializable)mPhotos);
				} else {
					AlbumResult album = mKXApplication.mFriendPhotoResults.get(mUid).get(arg2);
					String json = CallService.getPhotoUrl(1, album.getId());
					getPhotos(json, false);
					intent.putExtra("albumid",
							mKXApplication.mFriendPhotoResults.get(mUid).get(arg2).getId());
					intent.putExtra(
							"result",(Serializable)mPhotos);
				}
				startActivity(intent);
			}
		});
	}

	private void init() {
		// 获取照片所属用户的ID、姓名、头像
		mUid = getIntent().getStringExtra("uid");
		mName = getIntent().getStringExtra("name");
		mAvatar = getIntent().getIntExtra("avatar", -1);
		// ID为空时代表为当前用户,根据用户的不同 显示不同的内容
		if (mUid == null) {
			mBack.setText("我的首页");
			mTitle.setText("我的照片");
			getPhotos();
			mDisplay.setAdapter(new PhotoAdapter(mKXApplication.mMyPhotoResults));
		} else {
			mBack.setText(mName);
			mTitle.setText(mName + "的照片");
			getPhotos();
			mDisplay.setAdapter(new PhotoAdapter(
					mKXApplication.mFriendPhotoResults.get(mUid)));
		}

	}

	/**
	 * 获取照片数据
	 */
	private void getPhotos() {
		// ID为空时为当前用户数据,否则根据ID获取数据
		if (mUid == null) {
			if (mKXApplication.mMyPhotoResults.isEmpty()) {
				String json = CallService.getAlbums(""); // getmAlbums();
				getAlbums(json, false, "");
			}
		}else {
			if (!mKXApplication.mFriendPhotoResults.containsKey(mUid)) {
				String json = CallService.getAlbums(mUid); // getmAlbums();
				getAlbums(json, true, mUid);
			}
		}
	}

	private void getAlbums(String json, boolean isFriend, String uid) {
		try {
			JSONObject object = new JSONObject(json);
				JSONArray albumArray = object.getJSONArray(
						"data");
				mAlbums = new ArrayList<AlbumResult>();
				for (int j = 0; j < albumArray.length(); j++) {
					AlbumResult album = new AlbumResult();
					album.setId(albumArray.getJSONObject(j).getInt("id"));
					album.setEmail(albumArray.getJSONObject(j).getString("email"));
					album.setName(albumArray.getJSONObject(j).getString("name"));
					album.setImage(albumArray.getJSONObject(j).getString("image"));
					mAlbums.add(album);
				}
				
			if (isFriend) {
				mKXApplication.mFriendPhotoResults.put(uid, mAlbums);
			} else {
				mKXApplication.mMyPhotoResults = mAlbums;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			mDisplay.setVisibility(View.GONE);
			mNoDisplay.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 解析图片数据
	 * 
	 * @param json
	 */
	private void getPhotos(String json, boolean isFriend) {
		try {
			JSONObject object = new JSONObject(json);
				JSONArray albumArray = object.getJSONArray(
						"data");
				mPhotos = new ArrayList<PhotoDetailResult>();
				for (int j = 0; j < albumArray.length(); j++) {
					PhotoDetailResult photoDetailResult = new PhotoDetailResult();
					photoDetailResult.setId(albumArray.getJSONObject(j).getInt("id"));
					photoDetailResult.setComment_count(albumArray.getJSONObject(j).getInt("comment_count"));
					photoDetailResult.setAlbum_id(albumArray.getJSONObject(j).getInt("album_id"));
					photoDetailResult.setLike_count(albumArray.getJSONObject(j).getInt("like_count"));
					photoDetailResult.setTime(albumArray.getJSONObject(j).getString("time"));
					photoDetailResult.setPhoto_filename(albumArray.getJSONObject(j).getString("photo_filename"));
					mPhotos.add(photoDetailResult);
				}
				
		} catch (JSONException e) {
			e.printStackTrace();
			mDisplay.setVisibility(View.GONE);
			mNoDisplay.setVisibility(View.VISIBLE);
		}
	}

	private class PhotoAdapter extends BaseAdapter {

		private List<AlbumResult> mResults = new ArrayList<AlbumResult>();

		public PhotoAdapter(List<AlbumResult> results) {
			if (results != null) {
				mResults = results;
			}
		}

		public int getCount() {
			return mResults.size();
		}

		public Object getItem(int position) {
			return mResults.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(PhotoActivity.this).inflate(
						R.layout.photo_activity_item, null);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.photo_item_img);
				int padding = (int) TypedValue.applyDimension(
						TypedValue.COMPLEX_UNIT_DIP, 40, PhotoActivity.this
								.getResources().getDisplayMetrics());
				LayoutParams params = new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				params.width = (mScreenWidth - padding) / 3;
				params.height = (mScreenWidth - padding) / 3;
				holder.image.setLayoutParams(params);
				holder.title = (TextView) convertView
						.findViewById(R.id.photo_item_title);
				holder.time = (TextView) convertView
						.findViewById(R.id.photo_item_time);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			AlbumResult result = mResults.get(position);
			/*if (result.getType() == 0) {
				holder.image.setImageBitmap(mKXApplication.getAvatar(result
						.getImage()));
			} else {
				holder.image.setImageBitmap(mKXApplication.getPhoto(result
						.getImage()));
			}*/
			
			holder.image.setImageBitmap(CallService.getPhoto(result.getId(), result.getImage(), false, mUid));

			holder.title.setText(result.getName());
			holder.time.setText("");
			return convertView;
		}

		class ViewHolder {
			ImageView image;
			TextView title;
			TextView time;
		}
	}
}

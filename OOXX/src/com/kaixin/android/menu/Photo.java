package com.kaixin.android.menu;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
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

import com.kaixin.android.KXApplication;
import com.kaixin.android.R;
import com.kaixin.android.activity.PhoneAlbumActivity;
import com.kaixin.android.activity.PhotoListActivity;
import com.kaixin.android.common.Constants;
import com.kaixin.android.common.KaiXinAddress;
import com.kaixin.android.result.AlbumResult;
import com.kaixin.android.result.PhotoDetailResult;
import com.kaixin.android.ui.base.FlipperLayout.OnOpenListener;
import com.kaixin.android.utils.ActivityForResultUtil;
import com.kaixin.android.utils.CallService;
import com.kaixin.android.utils.CommonUtils;

/**
 * 菜单照片类
 * 
 * @author gyz
 * 
 */
public class Photo {
	private Context mContext;
	private KXApplication mKXApplication;
	private View mPhoto;

	private Button mMenu;
	private Button mRefresh;
	private Button mPhotoGraph;
	private GridView mDisplay;
	private Button mFriend;
	private Button mMySelf;
	private PhotoAdapter mAdapter;
	private OnOpenListener mOnOpenListener;

	private List<AlbumResult> mAlbums;
	// 是否是好友照片
	private boolean mIsFriend = true;
	// 屏幕的宽度
	private int mScreenWidth;
	private String json;
	private List<PhotoDetailResult> mPhotos;

	public Photo(Context context, KXApplication application, int screenWidth) {
		mContext = context;
		mKXApplication = application;
		mScreenWidth = screenWidth;
		mPhoto = LayoutInflater.from(context).inflate(R.layout.photo, null);
		findViewById();
		setListener();
		init();
	}

	private void findViewById() {
		mMenu = (Button) mPhoto.findViewById(R.id.photo_menu);
		mRefresh = (Button) mPhoto.findViewById(R.id.photo_refresh);
		mPhotoGraph = (Button) mPhoto.findViewById(R.id.photo_photograph);
		mDisplay = (GridView) mPhoto.findViewById(R.id.photo_display);
		mFriend = (Button) mPhoto.findViewById(R.id.photo_friend);
		mMySelf = (Button) mPhoto.findViewById(R.id.photo_myself);
		if(!CommonUtils.isNetWorkConnected(mKXApplication)){
			mPhoto.findViewById(R.id.warnning_layout).setVisibility(View.VISIBLE);
		}else{
			mPhoto.findViewById(R.id.warnning_layout).setVisibility(View.GONE);
		}
	}

	private void setListener() {
		mMenu.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (mOnOpenListener != null) {
					mOnOpenListener.open();
				}
			}
		});
		mRefresh.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				
			}
		});
		mPhotoGraph.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				PhotoDialog();
			}
		});
		mFriend.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (!mIsFriend) {
					mIsFriend = true;
					mFriend.setBackgroundResource(R.drawable.bottomtabbutton_leftred);
					mMySelf.setBackgroundResource(R.drawable.bottomtabbutton_rightwhite);
					mAdapter = new PhotoAdapter(
							mKXApplication.mFriendPhotoResults.get("kx001"));
					mDisplay.setAdapter(mAdapter);
				}
			}
		});
		mMySelf.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (mIsFriend) {
					mIsFriend = false;
					mFriend.setBackgroundResource(R.drawable.bottomtabbutton_leftwhite);
					mMySelf.setBackgroundResource(R.drawable.bottomtabbutton_rightred);
					mAdapter = new PhotoAdapter(mKXApplication.mMyPhotoResults);
					mDisplay.setAdapter(mAdapter);
				}
			}
		});
		
		mDisplay.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position,
					long arg3) {
				AlbumResult album = mAlbums.get(position);
				String json = CallService.getPhotoUrl(1, album.getId());
				getPhotos(json, false);
				Intent intent = new Intent(mContext, PhotoListActivity.class);
				intent.putExtra("albumid", album.getId());
				intent.putExtra("name", album.getName());
				intent.putExtra("result", (Serializable)mPhotos);
				mContext.startActivity(intent);
			}
		});
	}
	
	private void PhotoDialog() {
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("上传照片至OOXX");
		builder.setItems(new String[] { "拍照上传", "上传手机中的照片" },
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						Intent intent = null;
						switch (which) {
						case 0:
							intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
							File dir = new File(KaiXinAddress.SDCARED_CAMERA);
							if (!dir.exists()) {
								dir.mkdirs();
							}
							mKXApplication.mUploadPhotoPath = KaiXinAddress.SDCARED_CAMERA
									+ UUID.randomUUID().toString();
							File file = new File(
									mKXApplication.mUploadPhotoPath);
							if (!file.exists()) {
								try {
									file.createNewFile();
								} catch (IOException e) {

								}
							}
							intent.putExtra(MediaStore.EXTRA_OUTPUT,
									Uri.fromFile(file));
							((Activity) mContext).startActivityForResult(
											intent,
											ActivityForResultUtil.REQUESTCODE_UPLOADPHOTO_CAMERA);
							break;

						case 1:
							mContext.startActivity(new Intent(mContext,
									PhoneAlbumActivity.class));
							break;
						}
					}
				});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		builder.create().show();
	}

	private void init() {
		// 获取好有照片数据
		getFriendPhoto();
		// 获取我的照片数据
		getAblum();
		// 添加适配器
		mAdapter = new PhotoAdapter(
				mKXApplication.mFriendPhotoResults.get("kx001"));
		mDisplay.setAdapter(mAdapter);
	}

	/**
	 * 获取好友照片数据
	 */
	private void getFriendPhoto() {
		/*if (!mKXApplication.mFriendPhotoResults.containsKey(mUid)) {
			json = CallService.getAlbums(); // getmAlbums();
			getAlbums(json, false);
		}*/
	}
	
	/**
	 * 获取我的照片数据
	 */
	private void getAblum() {
		if (mKXApplication.mMyPhotoResults.isEmpty()) {
			json = CallService.getAlbums(""); // getmAlbums();
			getAlbums(json, false);
		}
	}

	/**
	 * 解析Json数据
	 * 
	 * @param json
	 * @param isFriend
	 */
	private void getAlbums(String json, boolean isFriend) {
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
				mKXApplication.mFriendPhotoResults.put("kx001", mAlbums);
			} else {
				mKXApplication.mMyPhotoResults = mAlbums;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 解析Json数据
	 * 
	 * @param json
	 * @param isFriend
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
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.photo_item, null);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.photo_item_img);
				int padding = (int) TypedValue.applyDimension(
						TypedValue.COMPLEX_UNIT_DIP, 40, mContext
								.getResources().getDisplayMetrics());
				LayoutParams params = new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				params.width = (mScreenWidth - padding) / 3;
				params.height = (mScreenWidth - padding) / 3;
				holder.image.setLayoutParams(params);
				holder.title = (TextView) convertView
						.findViewById(R.id.photo_item_title);
				holder.description = (TextView) convertView
						.findViewById(R.id.photo_item_description);
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
			BitmapDownloaderTask downImgTask = new BitmapDownloaderTask(holder.image);
			Map<String, String> map = new HashMap<String, String>();
			map.put("albumId", String.valueOf(result.getId()));
			map.put("filename", result.getImage());
			map.put("original", String.valueOf(false));
			map.put("uid", "");
            downImgTask.execute(map);
//			holder.image.setImageBitmap(CallService.getPhoto(result.getId(), result.getImage(), false, ""));

			holder.title.setText(result.getName());
			if (mIsFriend) {
				holder.description.setText(result.getDescription());
			} else {
				holder.description.setText(result.getDescription());
			}
			return convertView;
		}

		class ViewHolder {
			ImageView image;
			TextView title;
			TextView description;
		}
	}
	
	class BitmapDownloaderTask extends AsyncTask<Map<String, String>, Void, Bitmap> {
	    // 使用WeakReference解决内存问题，可以有效避免OOM的发生
	    private final WeakReference<ImageView> imageViewReference;

	    public BitmapDownloaderTask(ImageView imageView) {
	        imageViewReference = new WeakReference<ImageView>(imageView);
	    }
	    // 实际的下载线程，内部其实是concurrent线程，所以不会阻塞
	    @Override
	    protected Bitmap doInBackground(Map<String, String>... params) {
	    	InputStream is = (InputStream) CallService.getObject(Constants.getUrl() + "/PhotoServlet", new String[]
	    			{ "albumId", "filename", "original", "uid" }, new String[]
	    			{ params[0].get("albumId"), params[0].get("filename"), params[0].get("original") , params[0].get("uid")}, true);
	    	Bitmap bitmap = BitmapFactory.decodeStream(is);
	        return bitmap;
	    }

	    @Override
	    protected void onPostExecute(Bitmap bitmap) { // 下载完后执行的
	        if (imageViewReference != null) {
	            ImageView imageView = imageViewReference.get();
	            if (imageView != null) {
	                imageView.setImageBitmap(bitmap); // 下载完设置imageview为刚才下载的bitmap对象
	            }
	        }
	    }
	}

	public View getView() {
		return mPhoto;
	}

	public void setOnOpenListener(OnOpenListener onOpenListener) {
		mOnOpenListener = onOpenListener;
	}
}

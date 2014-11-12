package com.kaixin.android.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kaixin.android.KXActivity;
import com.kaixin.android.R;
import com.kaixin.android.result.AlbumResult;
import com.kaixin.android.result.LocationResult;
import com.kaixin.android.service.UploadImageService;
import com.kaixin.android.utils.CallService;
import com.kaixin.android.utils.Encrypter;
import com.kaixin.android.utils.HttpAssist;
import com.kaixin.android.utils.HttpAssist.OnUploadProcessListener;
import com.kaixin.android.utils.MessageUtil;
import com.kaixin.android.utils.StorageUtil;
import com.kaixin.android.utils.StringUtil;

/**
 * 照片分享类
 * 
 * @author gyz
 * 
 */
public class PhotoShareActivity extends KXActivity implements OnUploadProcessListener {
	private Button mCancel;
	private Button mUpload;
	private Gallery mDisplay;
	private ImageView mDisplaySingle;
	private TextView mLocation;
	private Button mDelete;
	private TextView mAlbum;
	private EditText mTitle;

	private GalleryAdapter mAdapter;

	private int mCurrentPosition;// 当前图片的编号
	private String mCurrentPath;// 当前图片的地址
	private int mLocationPosition;// 当前选择的地理位置在列表的位置
	private List<AlbumResult> mAlbums = new ArrayList<AlbumResult>();// 相册
	private int mAlbumPosition;// 当前选择的相册在列表的位置
	private Date date;
	private ProgressDialog dialog;
	private ProgressBar progressBar;
	/**
	 * 去上传文件
	 */
	protected static final int TO_UPLOAD_FILE = 1;  
	/**
	 * 上传文件响应
	 */
	protected static final int UPLOAD_FILE_DONE = 2;  //
	/**
	 * 选择文件
	 */
	public static final int TO_SELECT_PHOTO = 3;
	/**
	 * 上传初始化
	 */
	private static final int UPLOAD_INIT_PROCESS = 4;
	/**
	 * 上传中
	 */
	private static final int UPLOAD_IN_PROCESS = 5;
	
	private String[] mCompetenceItems = new String[] { "任何人可见",
			"仅关注人可见", "仅自己可见" };// 权限名称
	private int mCompetencePosition = 1;// 当前选择的权限在列表中的位置
	private TextView mCompetence;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photoshare_activity);
		findViewById();
		setListener();
		init();
	}

	private void findViewById() {
		mCancel = (Button) findViewById(R.id.photoshare_cannel);
		mUpload = (Button) findViewById(R.id.photoshare_upload);
		mDisplay = (Gallery) findViewById(R.id.photoshare_display);
		mDisplaySingle = (ImageView) findViewById(R.id.photoshare_display_single);
		mLocation = (TextView) findViewById(R.id.photoshare_location);
		mDelete = (Button) findViewById(R.id.photoshare_location_delete);
		mAlbum = (TextView) findViewById(R.id.photoshare_album);
		mTitle = (EditText) findViewById(R.id.edit_title);
		dialog = new ProgressDialog(PhotoShareActivity.this);
		dialog.setMessage("图片上传中。。。");
		mCompetence = (TextView) findViewById(R.id.photoshare_competence);
//		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
	}

	private void setListener() {
		mCancel.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 关闭当前界面
				finish();
			}
		});
		mUpload.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if(StringUtil.isNull(mTitle.getText().toString())){
					MessageUtil.showMsg(PhotoShareActivity.this, "请填写内容~");
					return;
				}
				date = new Date(new java.util.Date().getTime());
//				new UploadImageTask().execute();
				// 判断手机相册界面是否关闭,如果没关闭则关闭
				if (PhoneAlbumActivity.mInstance != null && !PhoneAlbumActivity.mInstance.isFinishing()) {
					PhoneAlbumActivity.mInstance.finish();
				}
				
//				handler.sendEmptyMessage(TO_UPLOAD_FILE);
				// 显示提示信息并关闭当前界面
				Intent intent = new Intent(PhotoShareActivity.this, UploadImageService.class);
				intent.putExtra("mAlbums", (Serializable)mAlbums);
				intent.putExtra("photoList", (Serializable)mKXApplication.mAlbumList);
				intent.putExtra("mAlbumPosition", mAlbumPosition);
				intent.putExtra("mCompetencePosition", String.valueOf(mCompetencePosition));
				intent.putExtra("content", mTitle.getText().toString());
				intent.putExtra("date", new Date(new java.util.Date().getTime()).toString());
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startService(intent);
				finish();
			}
		});
		mDisplay.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// 获取当前的照片编号以及照片地址传递到照片编辑类
				mCurrentPosition = arg2;
				mCurrentPath = mKXApplication.mAlbumList.get(mCurrentPosition)
						.get("image_path");
				Intent intent = new Intent();
				intent.setClass(PhotoShareActivity.this,
						ImageFilterActivity.class);
				intent.putExtra("path", mCurrentPath);
				intent.putExtra("isSetResult", true);
				startActivityForResult(intent, 0);
			}
		});
		mDisplaySingle.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 将照片地址传递到照片编辑类
				Intent intent = new Intent();
				intent.setClass(PhotoShareActivity.this,
						ImageFilterActivity.class);
				intent.putExtra("path", mCurrentPath);
				intent.putExtra("isSetResult", true);
				startActivityForResult(intent, 0);
			}
		});
		mLocation.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 显示地理位置对话框
				locationDialog();
			}
		});
		mDelete.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 更换显示,设置地理位置编号
				mLocation.setText("选择当前位置");
				mLocationPosition = -1;
			}
		});
		mAlbum.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 相册对话框
				AlbumDialog();
			}
		});
		mCompetence.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 权限对话框
				CompetenceDialog();
			}
		});
	}
	
	class UploadImageTask extends AsyncTask<Integer, String, String>{

		@Override
		protected String doInBackground(Integer... params) {
//			CallService.uploadImage(mAlbums.get(mAlbumPosition).getId(), date.toString(), baos.toByteArray());
			try {
				Map<String, String> m = new HashMap<String, String>();
				m.put("username", CallService.getUsername());
				m.put("albumId", String.valueOf(mAlbums.get(mAlbumPosition).getId()));
				m.put("content", mTitle.getText().toString());
				m.put("messageTime", date.toString());
				HttpAssist.uploadFile(mKXApplication.mAlbumList, m);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			// 显示提示信息并关闭当前界面
			Toast.makeText(PhotoShareActivity.this, "上传图片成功",
					Toast.LENGTH_SHORT).show();
			if(dialog.isShowing()){
				dialog.dismiss();
			}
			PhotoShareActivity.this.finish();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.show();
		}
	}
	
	/**
	 * 上传服务器响应回调
	 */
	@Override
	public void onUploadDone(int responseCode, String message) {
		dialog.dismiss();
		Message msg = Message.obtain();
		msg.what = UPLOAD_FILE_DONE;
		msg.arg1 = responseCode;
		msg.obj = message;
		handler.sendMessage(msg);
	}
	
	private void toUploadFile()
	{
		HttpAssist uploadUtil = HttpAssist.getInstance();
		uploadUtil.setOnUploadProcessListener(this);  //设置监听器监听上传状态
		try {
			Map<String, String> m = new HashMap<String, String>();
			m.put("username", CallService.getUsername());
			m.put("content", mTitle.getText().toString());
			m.put("albumId", String.valueOf(mAlbums.get(mAlbumPosition).getId()));
			m.put("messageTime", date.toString());
			uploadUtil.uploadFile(mKXApplication.mAlbumList, m);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case TO_UPLOAD_FILE:
				toUploadFile();
				break;
			case UPLOAD_INIT_PROCESS:
				progressBar.setMax(msg.arg1);
				break;
			case UPLOAD_IN_PROCESS:
				progressBar.setProgress(msg.arg1);
				progressBar.invalidate();
				break;
			case UPLOAD_FILE_DONE:
				PhotoShareActivity.this.finish();
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
		
	};
	private Uri uri;

	@Override
	public void onUploadProcess(int uploadSize) {
		Message msg = Message.obtain();
		msg.what = UPLOAD_IN_PROCESS;
		msg.arg1 = uploadSize;
		handler.sendMessage(msg);
	}

	@Override
	public void initUpload(int fileSize) {
		Message msg = Message.obtain();
		msg.what = UPLOAD_INIT_PROCESS;
		msg.arg1 = fileSize;
		handler.sendMessage(msg);
	}
	
	// 根据uri获得图片真实地址
    public String getPath(Uri uri, Context context) {
        String[] proj = { MediaStore.Images.Media.DATA };
        ContentResolver cr = context.getContentResolver();

        Cursor cursor = cr.query(uri, proj, null, null, null);

        cursor.moveToFirst();

        int actual_image_column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        return cursor.getString(actual_image_column_index);

    } 


	private void init() {
		Intent it = getIntent(); 
		if (it != null &&  it.getAction() != null && it.getAction().equals(Intent.ACTION_SEND)) { 
			Bundle extras = it.getExtras(); 
			if (extras.containsKey("android.intent.extra.STREAM")) { 
				uri = (Uri) extras.get("android.intent.extra.STREAM"); 
//				set_image(uri);//这里是将我们所选的分享图片加载出来 
				Map<String, String> map = new HashMap<String, String>();
				map.put("image_path", getPath(uri, mKXApplication));
				mKXApplication.mAlbumList.add(map);
			} 
		} 

		// 判断照片的数量,根据数量选择控件显示,1张图片用ImageView显示,多张用Gallery显示
		if (mKXApplication.mAlbumList.size() > 1) {
			mDisplaySingle.setVisibility(View.GONE);
			mDisplay.setVisibility(View.VISIBLE);
			mCurrentPosition = 0;
			mAdapter = new GalleryAdapter();
			mDisplay.setAdapter(mAdapter);
			mDisplay.setSelection(mCurrentPosition);
		} else if (mKXApplication.mAlbumList.size() == 1) {
			mDisplaySingle.setVisibility(View.VISIBLE);
			mDisplay.setVisibility(View.GONE);
			mCurrentPosition = 0;
			mCurrentPath = mKXApplication.mAlbumList.get(mCurrentPosition).get(
					"image_path");
			mDisplaySingle.setImageBitmap(mKXApplication
					.getPhoneAlbum(mCurrentPath));
		}
		// 获取地理位置数据
		getLocation();
		getAlbums(CallService.getAlbums("", StorageUtil.getString(this, "username"), Encrypter.md5(StorageUtil.getString(this, "password"))));
		
		// 显示默认地理位置、相册
		mLocation.setText(mKXApplication.mMyLocationResults.get(
				mLocationPosition).getName());
		mAlbum.setText(mAlbums.get(mAlbumPosition).getName());
		mCompetence.setText(mCompetenceItems[mCompetencePosition]);
	}
	
	/**
	 * 解析Json数据
	 * 
	 * @param json
	 * @param isFriend
	 */
	private void getAlbums(String json) {
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
				
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取地理位置数据
	 */
	private void getLocation() {
		if (mKXApplication.mMyLocationResults.isEmpty()) {
			List<String> listAddress = mKXApplication.mSaveLocationDao.queryAllLocation();
			LocationResult result = null;
			for (int i = 0; i < listAddress.size(); i++) {
				result = new LocationResult();
				result.setName(listAddress.get(i));
				result.setLocation(listAddress.get(i));
				mKXApplication.mMyLocationResults.add(result);
			}
		}
	}

	/**
	 * 地理位置对话框
	 */
	private void locationDialog() {
		AlertDialog.Builder builder = new Builder(PhotoShareActivity.this);
		builder.setTitle("选择当前位置");
		builder.setAdapter(new LocationAdapter(),
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						mLocationPosition = which;
						mLocation.setText(mKXApplication.mMyLocationResults
								.get(which).getName());
						dialog.dismiss();
					}
				});
		builder.setPositiveButton("刷新", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		}).create().show();
	}

	/**
	 * 相册对话框
	 */
	private void AlbumDialog() {
		AlertDialog.Builder builder = new Builder(PhotoShareActivity.this);
		builder.setTitle("请选择相册");
		builder.setAdapter(new AlbumAdapter(),
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						if(which == 0){
							CreateAlbumDialog();
						}else{
							mAlbumPosition = which - 1;
							mAlbum.setText(mAlbums.get(which - 1).getName());
						}
						dialog.dismiss();
					}
				});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		}).create().show();
	}
	
	/**
	 * 相册对话框
	 */
	private void CreateAlbumDialog() {
		AlertDialog.Builder builder = new Builder(PhotoShareActivity.this);
		builder.setTitle("请输入新相册名称");
		final EditText text = new EditText(this); 
		builder.setView(text);
		builder.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						CallService.createAlbum(text.getText().toString(), text.getText().toString());
						getAlbums(CallService.getAlbums("",StorageUtil.getString(PhotoShareActivity.this, "username"), Encrypter.md5(StorageUtil.getString(PhotoShareActivity.this, "password"))));
						mAlbum.setText(mAlbums.get(mAlbums.size() - 1).getName());
						mAlbumPosition = mAlbums.size() - 1;
						dialog.dismiss();
					}
				});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		}).create().show();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			//获取新的照片地址
			mCurrentPath = data.getStringExtra("path");
			Map<String, String> map = mKXApplication.mAlbumList
					.get(mCurrentPosition);
			map.put("image_path", mCurrentPath);
			//更新界面显示
			if (mKXApplication.mAlbumList.size() > 1) {
				mAdapter.notifyDataSetChanged();
			} else if (mKXApplication.mAlbumList.size() == 1) {
				mDisplaySingle.setImageBitmap(mKXApplication
						.getPhoneAlbum(mCurrentPath));
			}

		}
	}

	private class LocationAdapter extends BaseAdapter {

		public int getCount() {
			return mKXApplication.mMyLocationResults.size();
		}

		public Object getItem(int position) {
			return mKXApplication.mMyLocationResults.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(PhotoShareActivity.this)
						.inflate(R.layout.photoshare_activity_location_item,
								null);
				holder = new ViewHolder();
				holder.icon = (ImageView) convertView
						.findViewById(R.id.photoshare_activity_location_item_icon);
				holder.name = (TextView) convertView
						.findViewById(R.id.photoshare_activity_location_item_name);
				holder.location = (TextView) convertView
						.findViewById(R.id.photoshare_activity_location_item_location);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			LocationResult result = mKXApplication.mMyLocationResults
					.get(position);
			if (mLocationPosition == position) {
				holder.icon.setVisibility(View.VISIBLE);
			} else {
				holder.icon.setVisibility(View.INVISIBLE);
			}
			holder.name.setText(result.getName());
			holder.location.setText(result.getLocation());
			return convertView;
		}

		class ViewHolder {
			ImageView icon;
			TextView name;
			TextView location;
		}
	}

	private class AlbumAdapter extends BaseAdapter {

		public int getCount() {
			return mAlbums.size() + 1;
		}

		public Object getItem(int position) {
			if(position == 0){
				return mAlbums.get(0);
			}else{
				return mAlbums.get(position - 1);
			}
			
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(PhotoShareActivity.this)
						.inflate(R.layout.photoshare_activity_album_item, null);
				holder = new ViewHolder();
				holder.icon = (ImageView) convertView
						.findViewById(R.id.photoshare_activity_album_item_icon);
				holder.name = (TextView) convertView
						.findViewById(R.id.photoshare_activity_album_item_name);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.icon.setVisibility(View.VISIBLE);
			if(position == 0){
				holder.name.setText("新增相册");
			}else{
				holder.name.setText(mAlbums.get(position - 1).getName());
			}
			
			return convertView;
		}

		class ViewHolder {
			ImageView icon;
			TextView name;
		}
	}

	private class GalleryAdapter extends BaseAdapter {

		public int getCount() {
			return mKXApplication.mAlbumList.size();
		}

		public Object getItem(int position) {
			return mKXApplication.mAlbumList.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(PhotoShareActivity.this)
						.inflate(R.layout.photoshare_activity_item, null);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.photoshare_item_image);
				holder.delete = (Button) convertView
						.findViewById(R.id.photoshare_item_delete);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			Map<String, String> results = mKXApplication.mAlbumList
					.get(position);
			holder.image.setImageBitmap(mKXApplication.getPhoneAlbum(results
					.get("image_path")));
			if (mKXApplication.mAlbumList.size() > 1) {
				holder.delete.setVisibility(View.VISIBLE);
			} else if (mKXApplication.mAlbumList.size() == 1) {
				holder.delete.setVisibility(View.GONE);
			}
			holder.delete.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					mKXApplication.mAlbumList.remove(position);
					notifyDataSetChanged();
				}
			});
			return convertView;
		}

		class ViewHolder {
			ImageView image;
			Button delete;
		}
	}
	
	/**
	 * 权限对话框
	 */
	private void CompetenceDialog() {
		AlertDialog.Builder builder = new Builder(PhotoShareActivity.this);
		builder.setTitle("请选择图片权限");
		builder.setAdapter(new CompetenceAdapter(),
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						mCompetencePosition = which;
						mCompetence.setText(mCompetenceItems[which]);
						dialog.dismiss();
					}
				});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		}).create().show();
	}

	private class CompetenceAdapter extends BaseAdapter {

		public int getCount() {
			return mCompetenceItems.length;
		}

		public Object getItem(int position) {
			return mCompetenceItems[position];
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(PhotoShareActivity.this)
						.inflate(R.layout.writerecord_activity_competence_item,
								null);
				holder = new ViewHolder();
				holder.icon = (ImageView) convertView
						.findViewById(R.id.writerecord_activity_competence_item_icon);
				holder.name = (TextView) convertView
						.findViewById(R.id.writerecord_activity_competence_item_name);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			if (mCompetencePosition == position) {
				holder.icon.setVisibility(View.VISIBLE);
			} else {
				holder.icon.setVisibility(View.INVISIBLE);
			}
			holder.name.setText(mCompetenceItems[position]);
			return convertView;
		}

		class ViewHolder {
			ImageView icon;
			TextView name;
		}
	}

	protected void onDestroy() {
		super.onDestroy();
		mKXApplication.mAlbumList.clear();
	}
}

package com.kaixin.android.activity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONArray;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kaixin.android.KXActivity;
import com.kaixin.android.R;
import com.kaixin.android.common.KaiXinAddress;
import com.kaixin.android.result.LocationResult;
import com.kaixin.android.utils.ActivityForResultUtil;
import com.kaixin.android.utils.CallService;
import com.kaixin.android.utils.HttpAssist;
import com.kaixin.android.utils.MessageUtil;
import com.kaixin.android.utils.StringUtil;
import com.kaixin.android.utils.TextUtil;

/**
 * 写记录类
 * 
 * @author gyz
 * 
 */
public class WriteRecordActivity extends KXActivity {
	private LinearLayout mParent;
	private Button mCancel;
	private Button mSubmit;
	private EditText mContent;
	private Button mLocation;
	private Button mPicture;
	private ImageButton mFaceButton;
	private ImageButton mPhotoButton;
	private ImageButton mLocationButton;
	private ImageButton mAtButton;
	private TextView mCompetence;

	private int mLocationPosition;// 当前选择的地理位置在列表的位置
	private boolean mLocationIsShowing = true;// 当前是否显示地理位置
	private String[] mCompetenceItems = new String[] { "任何人可见",
			"仅关注人可见", "仅自己可见" };// 权限名称
	private int mCompetencePosition = 1;// 当前选择的权限在列表中的位置
	private String mPhotoPath;// 上传的图片路径
	private boolean mPictureIsExist = false;// 是否上传图片

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.writerecord_activity);
		findViewById();
		setListener();
		init();
	}

	private void findViewById() {
		mParent = (LinearLayout) findViewById(R.id.writerecord_parent);
		mCancel = (Button) findViewById(R.id.writerecord_cannel);
		mSubmit = (Button) findViewById(R.id.writerecord_submit);
		mContent = (EditText) findViewById(R.id.writerecord_content);
		mLocation = (Button) findViewById(R.id.writerecord_location);
		mPicture = (Button) findViewById(R.id.writerecord_picture);
		mFaceButton = (ImageButton) findViewById(R.id.writerecord_face_button);
		mPhotoButton = (ImageButton) findViewById(R.id.writerecord_photo_button);
		mLocationButton = (ImageButton) findViewById(R.id.writerecord_location_button);
		mAtButton = (ImageButton) findViewById(R.id.writerecord_at_button);
		mCompetence = (TextView) findViewById(R.id.writerecord_competence);
	}

	private void setListener() {
		mCancel.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// 关闭当前界面
				finish();
			}
		});
		mSubmit.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 获取当前输入框内容
				String content = mContent.getText().toString().trim();
				// 内容为空时显示提示对话框,不为空则返回更新信息
				if (TextUtils.isEmpty(content)) {
					// 显示提示对话框
					new AlertDialog.Builder(WriteRecordActivity.this)
							.setTitle("OOXX")
							.setIcon(android.R.drawable.ic_dialog_alert)
							.setMessage("记录信息不能为空")
							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {

										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
										}
									}).create().show();
				} else {
					if(!StringUtil.isNull(mPhotoPath)){
						new UploadImageTask().execute();
					}else{
						if(content.length() > 5){
							CallService.writeDiary(content.substring(0, 4), content, mCompetencePosition);
						}else{
							CallService.writeDiary(content, content, mCompetencePosition);
						}
					}
						
					
					// 显示提示信息并关闭当前界面
					MessageUtil.showImgMsg(mKXApplication, "发布成功，+10");
					finish();
				}
			}
		});
		mLocation.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 地理位置对话框
				locationDialog();
			}
		});
		mPicture.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 跳转到删除图片
				Intent intent = new Intent();
				intent.setClass(WriteRecordActivity.this,
						DeletePhotoActivity.class);
				intent.putExtra("path", mPhotoPath);
				startActivityForResult(
						intent,
						ActivityForResultUtil.REQUESTCODE_WRITERECORD_DELETE_PHOTO);
			}
		});
		mFaceButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 显示表情对话框
				showFace(mParent);
			}
		});
		mFaceGridView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 获取当前光标所在位置
				int currentPosition = mContent.getSelectionStart();
				// 添加含有表情的文本
				mContent.setText(new TextUtil(mKXApplication).replace(mContent
						.getText().insert(currentPosition,
								mKXApplication.mFacesText.get(position))));
				// 关闭表情对话框
				dismissFace();
			}
		});
		mFaceClose.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 关闭表情对话框
				dismissFace();
			}
		});
		mPhotoButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 判断是否已经添加图片,如果添加则提示消息否则则显示图片对话框
				if (mPictureIsExist) {
					Toast.makeText(WriteRecordActivity.this, "最多只能添加一张图片",
							Toast.LENGTH_SHORT).show();
				} else {
					// 图片对话框
					PhotoDialog();
				}
			}
		});
		mLocationButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 根据地理位置显示状态,显示或隐藏,并修改图标
				if (mLocationIsShowing) {
					mLocationIsShowing = false;
					mLocation.setVisibility(View.GONE);
					mLocationButton
							.setImageResource(R.drawable.write_function_location_button);
				} else {
					mLocationIsShowing = true;
					mLocation.setVisibility(View.VISIBLE);
					mLocationButton
							.setImageResource(R.drawable.write_function_locationremove_button);
				}
			}
		});
		mAtButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Toast.makeText(WriteRecordActivity.this, "暂时不支持@功能",
						Toast.LENGTH_SHORT).show();
			}
		});
		mCompetence.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 权限对话框
				CompetenceDialog();
			}
		});
	}
	
	private ProgressDialog dialog;
	
	class UploadImageTask extends AsyncTask<Integer, String, String>{

		@Override
		protected String doInBackground(Integer... params) {
//			CallService.uploadImage(mAlbums.get(mAlbumPosition).getId(), date.toString(), baos.toByteArray());
			try {
				Date date = new Date(new java.util.Date().getTime());
				Map<String, String> m = new HashMap<String, String>();
				m.put("username", CallService.getUsername());
				m.put("albumId", "0");
				m.put("content", mContent.getText().toString());
				m.put("messageTime", date.toString());
				HttpAssist.uploadFile(mPhotoPath, m);
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
			Toast.makeText(WriteRecordActivity.this, "上传记录成功",
					Toast.LENGTH_SHORT).show();
			if(dialog.isShowing()){
				dialog.dismiss();
			}
			WriteRecordActivity.this.finish();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.show();
		}
	}

	private void init() {
		// 获取地理位置数据
		getLocation();
		// 初始化显示数据
		mLocation.setText(mKXApplication.mMyLocationResults.get(0).getName());
		mCompetence.setText(mCompetenceItems[mCompetencePosition]);
	}

	/**
	 * 获取地理位置
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
	 * 图片对话框
	 */
	private void PhotoDialog() {
		AlertDialog.Builder builder = new Builder(WriteRecordActivity.this);
		builder.setTitle("插入照片");
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
							mPhotoPath = KaiXinAddress.SDCARED_CAMERA
									+ UUID.randomUUID().toString();
							File file = new File(mPhotoPath);
							if (!file.exists()) {
								try {
									file.createNewFile();
								} catch (IOException e) {

								}
							}
							intent.putExtra(MediaStore.EXTRA_OUTPUT,
									Uri.fromFile(file));
							startActivityForResult(
									intent,
									ActivityForResultUtil.REQUESTCODE_UPLOADPHOTO_CAMERA);
							break;

						case 1:
							intent = new Intent(Intent.ACTION_PICK, null);
							intent.setDataAndType(
									MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
									"image/*");
							startActivityForResult(
									intent,
									ActivityForResultUtil.REQUESTCODE_UPLOADPHOTO_LOCATION);
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

	/**
	 * 地理位置对话框
	 */
	private void locationDialog() {
		AlertDialog.Builder builder = new Builder(WriteRecordActivity.this);
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
				convertView = LayoutInflater.from(WriteRecordActivity.this)
						.inflate(R.layout.writerecord_activity_location_item,
								null);
				holder = new ViewHolder();
				holder.icon = (ImageView) convertView
						.findViewById(R.id.writerecord_activity_location_item_icon);
				holder.name = (TextView) convertView
						.findViewById(R.id.writerecord_activity_location_item_name);
				holder.location = (TextView) convertView
						.findViewById(R.id.writerecord_activity_location_item_location);
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

	/**
	 * 权限对话框
	 */
	private void CompetenceDialog() {
		AlertDialog.Builder builder = new Builder(WriteRecordActivity.this);
		builder.setTitle("请选择记录权限");
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
				convertView = LayoutInflater.from(WriteRecordActivity.this)
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

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case ActivityForResultUtil.REQUESTCODE_UPLOADPHOTO_CAMERA:
			if (resultCode == RESULT_OK) {
				if (!Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					Toast.makeText(this, "SD不可用", Toast.LENGTH_SHORT).show();
					return;
				}
				Intent intent = new Intent();
				intent.setClass(WriteRecordActivity.this,
						ImageFilterActivity.class);
				intent.putExtra("path", mPhotoPath);
				intent.putExtra("isSetResult", true);
				startActivityForResult(
						intent,
						ActivityForResultUtil.REQUESTCODE_WRITERECORD_CHANGE_PHOTO);
			} else {
				Toast.makeText(this, "取消上传", Toast.LENGTH_SHORT).show();
			}
			break;
		case ActivityForResultUtil.REQUESTCODE_UPLOADPHOTO_LOCATION:
			Uri uri = null;
			if (data == null) {
				Toast.makeText(this, "取消上传", Toast.LENGTH_SHORT).show();
				return;
			}
			if (resultCode == RESULT_OK) {
				if (!Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					Toast.makeText(this, "SD不可用", Toast.LENGTH_SHORT).show();
					return;
				}
				uri = data.getData();
				String[] proj = { MediaStore.Images.Media.DATA };
				Cursor cursor = managedQuery(uri, proj, null, null, null);
				if (cursor != null) {
					int column_index = cursor
							.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					if (cursor.getCount() > 0 && cursor.moveToFirst()) {
						mPhotoPath = cursor.getString(column_index);
						Intent intent = new Intent();
						intent.setClass(WriteRecordActivity.this,
								ImageFilterActivity.class);
						intent.putExtra("path", mPhotoPath);
						intent.putExtra("isSetResult", true);
						startActivityForResult(
								intent,
								ActivityForResultUtil.REQUESTCODE_WRITERECORD_CHANGE_PHOTO);
					}
				}
			} else {
				Toast.makeText(this, "照片获取失败", Toast.LENGTH_SHORT).show();
			}
			break;
		case ActivityForResultUtil.REQUESTCODE_WRITERECORD_CHANGE_PHOTO:
			if (resultCode == RESULT_OK) {
				mPhotoPath = data.getStringExtra("path");
				mPicture.setVisibility(View.VISIBLE);
				mPictureIsExist = true;
			}
			break;
		case ActivityForResultUtil.REQUESTCODE_WRITERECORD_DELETE_PHOTO:
			if (resultCode == RESULT_OK) {
				mPicture.setVisibility(View.GONE);
				mPictureIsExist = false;
			}
			break;
		}

	}
}

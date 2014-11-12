package com.kaixin.android.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kaixin.android.KXActivity;
import com.kaixin.android.R;
import com.kaixin.android.utils.PhoneComparator;
import com.kaixin.android.view.LoadingDailog;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 手机图片文件类
 * 
 * @author gyz
 * 
 */
public class PhoneAlbumActivity extends KXActivity {
	private Button mCancel;
	private ListView mDisplay;
	private PhoneAlnumAdapter mAdapter;
//	private LoadImagesFolderFromSDCard loadImageTask;
	public static Activity mInstance;
	private String filePath;
	private String folderIdColumn;
	private String folderColumn;
	private String fileIdColumn;
	private String fileNameColumn;
	private String pathColumn;
	private LoadingDailog loadingDailog;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phonealbum_activity);
		findViewById();
		setListener();
		init();
	}

	private void findViewById() {
		mCancel = (Button) findViewById(R.id.phonealbum_cancel);
		mDisplay = (ListView) findViewById(R.id.phonealbum_display);
	}

	private void setListener() {
		mCancel.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				//关闭当前界面
				finish();
			}
		});
		mDisplay.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//传递文件夹地址到文件夹内容显示类
				Intent intent = new Intent();
				intent.setClass(PhoneAlbumActivity.this, AlbumActivity.class);
				String path = (String) mKXApplication.mPhoneAlbum.keySet()
						.toArray()[arg2];
				intent.putExtra("path", path);
				startActivity(intent);
			}
		});
	}

	private void init() {
		mInstance = this;
		mKXApplication.mPhoneAlbum.clear();
//		mAdapter = new PhoneAlnumAdapter();
//		mDisplay.setAdapter(mAdapter);
		
//		loadImageTask = new LoadImagesFolderFromSDCard();
//		loadImageTask.execute();
		// 获取手机里的图片内容
		
		new Thread(new Runnable() {

			public void run() {
				runOnUiThread(new Runnable() {
					public void run() {
						loadingDailog = new LoadingDailog(PhoneAlbumActivity.this);
						loadingDailog.show();
					}
				});
				allScan();
				getFiles();
				runOnUiThread(new Runnable() {
					public void run() {
						mDisplay.setAdapter(new PhoneAlnumAdapter());
						loadingDailog.dismiss();
//						mAdapter.notifyDataSetChanged();
					}
				});
			}
		}).start();
	}
	
	/*private void getImages() {        
		//显示进度条      
		mProgressDialog = ProgressDialog.show(this, null, 正在加载...);       
		new Thread(new Runnable() {                
			@Override           
			public void run() { 
				Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;       
				ContentResolver mContentResolver = MainActivity.this.getContentResolver();     
				//只查询jpeg和png的图片      
				Cursor mCursor = mContentResolver.query(mImageUri, null,  
						MediaStore.Images.Media.MIME_TYPE + =? or      
								+ MediaStore.Images.Media.MIME_TYPE + =?,   
										new String[] { image/jpeg, image/png }, MediaStore.Images.Media.DATE_MODIFIED);
				if(mCursor == null){                
					return;             
					}                       
				while (mCursor.moveToNext()) {    
					//获取图片的路径             
					String path = mCursor.getString(mCursor    
							.getColumnIndex(MediaStore.Images.Media.DATA));    
					//获取该图片的父路径名             
					String parentName = new File(path).getParentFile().getName();    
					//根据父路径名将图片放入到mGruopMap中             
					if (!mGruopMap.containsKey(parentName)) {    
						List<STRING> chileList = new ArrayList<STRING>();    
						chileList.add(path);                     
						mGruopMap.put(parentName, chileList);    
						} else {                    
							mGruopMap.get(parentName).add(path);        
							}            
					}                     
				//通知Handler扫描图片完成              
				mHandler.sendEmptyMessage(SCAN_OK);        
				mCursor.close();          
				}      
			}).start();   
           } 
				}
			}
			
		}
	}*/
	
	public void allScan() {
		  sendBroadcast(new Intent(
		    Intent.ACTION_MEDIA_MOUNTED,
		    Uri.parse("file://" + Environment.getExternalStorageDirectory())));
	}


	
	/**
	 * 获取文件目录下的图片内容 注：新方法，速度很快
	 * 
	 * @param folder
	 */
	private void getFiles(){
		
		String[] projection = new String[] { MediaStore.Images.Media._ID,
	            MediaStore.Images.Media.BUCKET_ID, // 直接包含该图片文件的文件夹ID，防止在不同下的文件夹重名
	            MediaStore.Images.Media.BUCKET_DISPLAY_NAME, // 直接包含该图片文件的文件夹名
	            MediaStore.Images.Media.DISPLAY_NAME, // 图片文件名
	            MediaStore.Images.Media.DATA // 图片绝对路径
		};
		Cursor cursor = PhoneAlbumActivity.this.getContentResolver().query(
		                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, "",
		                null, MediaStore.Images.Media.DATE_MODIFIED +" DESC");
		while(cursor.moveToNext()){
			folderIdColumn = cursor.getString(cursor
		            .getColumnIndex(MediaStore.Images.Media.BUCKET_ID));
			folderColumn = cursor.getString(cursor
			                .getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
			fileIdColumn = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media._ID));
			fileNameColumn = cursor.getString(cursor
			                .getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
			pathColumn = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)); 
			if (mKXApplication.mPhoneAlbum.containsKey(folderIdColumn)) {
				List<Map<String, String>> list = mKXApplication.mPhoneAlbum
						.get(folderIdColumn);
				Map<String, String> map = new HashMap<String, String>();
				map.put("image_name", fileNameColumn);
				map.put("image_path", pathColumn);
				map.put("image_parent_name", folderColumn);
				map.put("image_parent_path", folderColumn);
				list.add(map);
			} else {
				List<Map<String, String>> list = new ArrayList<Map<String, String>>();
				Map<String, String> map = new HashMap<String, String>();
				map.put("image_name", fileNameColumn);
				map.put("image_path", pathColumn);
				map.put("image_parent_name", folderColumn);
				map.put("image_parent_path", folderColumn);
				list.add(map);
				mKXApplication.mPhoneAlbum.put(
						folderIdColumn, list);
//				loadImageTask.publishProgress(list);
			}
		}
		List<Map.Entry<String, List<Map<String, String>>>> list = new LinkedList<Map.Entry<String, List<Map<String, String>>>>(mKXApplication.mPhoneAlbum.entrySet()); 
		Collections.sort(list, Collections.reverseOrder(new PhoneComparator()));
		Map sortedMap = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        mKXApplication.mPhoneAlbum = sortedMap;
        
	}

	private class PhoneAlnumAdapter extends BaseAdapter {

		public int getCount() {
			return mKXApplication.mPhoneAlbum.size();
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(PhoneAlbumActivity.this)
						.inflate(R.layout.phonealbum_activity_item, null);
				holder = new ViewHolder();
				holder.photo = (ImageView) convertView
						.findViewById(R.id.phonealbum_item_photo);
				holder.name = (TextView) convertView
						.findViewById(R.id.phonealbum_item_name);
				holder.count = (TextView) convertView
						.findViewById(R.id.phonealbum_item_count);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			List<Map<String, String>> list = mKXApplication.mPhoneAlbum
					.get(mKXApplication.mPhoneAlbum.keySet().toArray()[position]);
//			holder.photo.setImageBitmap(PhotoUtil.getImageThumbnail(list.get(0)
//					.get("image_path"), 70, 70));
			ImageLoader.getInstance().displayImage("file:///" + list.get(0)
					.get("image_path"), holder.photo);
			holder.name.setText(list.get(0).get("image_parent_name"));
			holder.count.setText("(" + list.size() + ")");
			return convertView;
		}

		class ViewHolder {
			ImageView photo;
			TextView name;
			TextView count;
		}
	}
	
	/**
	 * 获取文件目录下的图片内容 注：老方法，效率很低
	 * 
	 * @param folder
	 */
	private void getFiles(File folder) {
		File[] files = folder.listFiles();
		if (files != null) {
			for (int i = files.length - 1; i >= 0; i--) {
				File file = files[i];
				if (getImageFile(file.getName())) {
					if (mKXApplication.mPhoneAlbum.containsKey(folder
							.getAbsolutePath())) {
						List<Map<String, String>> list = mKXApplication.mPhoneAlbum
								.get(folder.getAbsolutePath());
						Map<String, String> map = new HashMap<String, String>();
						map.put("image_name", file.getName());
						map.put("image_path", file.getAbsolutePath());
						map.put("image_parent_name", folder.getName());
						map.put("image_parent_path", folder.getAbsolutePath());
						list.add(map);
					} else {
						List<Map<String, String>> list = new ArrayList<Map<String, String>>();
						Map<String, String> map = new HashMap<String, String>();
						map.put("image_name", file.getName());
						map.put("image_path", file.getAbsolutePath());
						map.put("image_parent_name", folder.getName());
						map.put("image_parent_path", folder.getAbsolutePath());
						list.add(map);
						mKXApplication.mPhoneAlbum.put(
								folder.getAbsolutePath(), list);
					}
				}
				if (file.isDirectory()) {
					getFiles(file);
				}
			}
		}
	}
	/**
	 * 判断是否为图片
	 * 
	 * @param fName
	 *            文件的名字
	 * @return
	 */
	private boolean getImageFile(String fName) {
		boolean re;

		String end = fName
				.substring(fName.lastIndexOf(".") + 1, fName.length())
				.toLowerCase();

		if (end.equals("jpg") || end.equals("gif") || end.equals("png")
				|| end.equals("jpeg") || end.equals("bmp")) {
			re = true;
		} else {
			re = false;
		}
		return re;
	}

}

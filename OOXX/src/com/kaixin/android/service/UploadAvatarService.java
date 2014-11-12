package com.kaixin.android.service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Toast;

import com.kaixin.android.KXApplication;
import com.kaixin.android.activity.PhotoShareActivity;
import com.kaixin.android.result.AlbumResult;
import com.kaixin.android.utils.CallService;
import com.kaixin.android.utils.HttpAssist;
import com.kaixin.android.utils.MessageUtil;

public class UploadAvatarService extends Service {

	protected KXApplication mKXApplication;
	private List<AlbumResult> mAlbums;
	private int mAlbumPosition;
	List<Map<String, String>> photoList;
	private String date;
	private String content;
	private String photoPath;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	
	
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		if(intent != null){
			photoPath =  intent.getStringExtra("photoPath");
			new UploadImageTask().execute();
		}
		
	}



	class UploadImageTask extends AsyncTask<Integer, String, String>{

		@Override
		protected String doInBackground(Integer... params) {
//			CallService.uploadImage(mAlbums.get(mAlbumPosition).getId(), date.toString(), baos.toByteArray());
			try {
				Map<String, String> m = new HashMap<String, String>();
				m.put("username", CallService.getUsername());
				m.put("albumId", "-1");
				m.put("content", "");
				HttpAssist.uploadFile(photoPath, m);
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
			MessageUtil.showImgMsg(UploadAvatarService.this, "发布成功，+50");
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Toast.makeText(UploadAvatarService.this, "图片上传中。。。",
					Toast.LENGTH_SHORT).show();
		}
		
	}
}

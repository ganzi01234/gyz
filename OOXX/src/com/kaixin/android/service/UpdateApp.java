/* 
 * Copyright 2012 Share.Ltd  All rights reserved.
 * Share.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * �������
 * @ActUpdateApp.java - 2012-11-23 ����11:25:24 - Anonymous
 */

package com.kaixin.android.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kaixin.android.R;
import com.kaixin.android.common.Constants;
import com.kaixin.android.utils.ApplicationUtil;
import com.kaixin.android.utils.CallService;

/**
 * �������
 * @author mous
 * 
 */
public class UpdateApp {

	private final static int WHAT_PROGRESS = 1;
	private final static int WHAT_INSTALL = 2;
	private final static int WHAT_BROADCAST = 3;
	private final static int WHAT_COMUP = 4;
	private final static int NO_UPDATA = 5;
	private final static int WHAT_ERROR = -1;

	private boolean mCompulsoryUpdate;
	private ProgressBar mProgressBar;
	private int current;
	private TextView mDownloadTxt;
	public static int mLoadingProcess;
	public static String upAppName = ApplicationUtil.getApplicationContext().getString(R.string.apk_name);
	// ��������APK��ַ
	private String DOWNLOAD_URL = Constants.getApkUrl() + upAppName;

	private String mJsonVal;
	private Context context;
	private String mServerCode;
	private String mUpInfo;
	private String mUpCodeName;

	public UpdateApp(Context context) {
		this.context = context;
	}

	public void checkUpdate() {
		current = ApplicationUtil.getVersionCode(context);
		mLoadingProcess = 0;
		if (ApplicationUtil.isNetWorkConnected(context)) {
			new Thread() {
				public void run() {
					try {
						getServerCode(current);
						if (Integer.parseInt(mServerCode) > current) {
							if (!mCompulsoryUpdate) {
								sendMsg(WHAT_COMUP, 0);
							} else if (mCompulsoryUpdate) {
								sendMsg(WHAT_BROADCAST, 0);
							} else {
								sendMsg(NO_UPDATA, 0);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.start();
			return;
		} else {
			Toast.makeText(context, context.getString(R.string.network_toast), Toast.LENGTH_SHORT).show();
		}
	}

	private void getServerCode(int numberval) throws IOException {
		/*URL url = new URL(Constants.getUrl() + "/UpdateServlet/");
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setDoInput(true);
		HttpURLConnection.setFollowRedirects(true);
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setUseCaches(false);
		httpURLConnection.setInstanceFollowRedirects(true);
		httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		httpURLConnection.setConnectTimeout(50000);
		httpURLConnection.setReadTimeout(50000);
		httpURLConnection.connect();
		DataOutputStream out = new DataOutputStream(httpURLConnection.getOutputStream());
//		String content = "version=" + numberval;
//		out.writeBytes(content);
		out.flush();
		out.close();
		BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
		String line = null;
		mJsonVal = new StringBuilder();
		while ((line = in.readLine()) != null) {
			mJsonVal.append(line);
		}
		try {
			JSONObject jsonObject;
			jsonObject = new JSONObject(mJsonVal.toString());
			mServerCode = jsonObject.getJSONObject("data").getString("code");
			mUpInfo =  jsonObject.getJSONObject("data").getString("info");
			mUpCodeName =  jsonObject.getJSONObject("data").getString("name");
			mCompulsoryUpdate =  jsonObject.getJSONObject("data").optBoolean("type");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		in.close();
		httpURLConnection.disconnect();*/
		mJsonVal = CallService.getUpdate();
		try {
			JSONObject jsonObject;
			jsonObject = new JSONObject(mJsonVal.toString());
			
			mServerCode = jsonObject.getJSONObject("data").getString("code");
			mUpInfo =  jsonObject.getJSONObject("data").getString("info");
			mUpCodeName =  jsonObject.getJSONObject("data").getString("name");
			mCompulsoryUpdate =  jsonObject.getJSONObject("data").optBoolean("type");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void Beginning() {
		LinearLayout ll = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.layout_progress, null);
		mProgressBar = (ProgressBar) ll.findViewById(R.id.down_pb);
		mDownloadTxt = (TextView) ll.findViewById(R.id.download_txt);
		Builder builder = new Builder(context);
		builder.setView(ll);
		builder.setTitle(context.getString(R.string.update_load_txt));
		builder.setNegativeButton(context.getString(R.string.update_hide_txt), new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(context, UpdateService.class);
				context.startService(intent);
				dialog.dismiss();
			}
		});
		builder.show();

		new Thread() {
			public void run() {
				loadFile(DOWNLOAD_URL);
			}
		}.start();
	}

	public void loadFile(String url) {
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		HttpResponse response;
		try {
			response = client.execute(get);

			HttpEntity entity = response.getEntity();
			float length = entity.getContentLength();

			InputStream is = entity.getContent();
			FileOutputStream fileOutputStream = null;
			if (is != null) {
				File file = new File(Environment.getExternalStorageDirectory(), upAppName);
				fileOutputStream = new FileOutputStream(file);
				byte[] buf = new byte[1024];
				int ch = -1;
				float count = 0;
				while ((ch = is.read(buf)) != -1) {
					fileOutputStream.write(buf, 0, ch);
					count += ch;
					sendMsg(1, (int) (count * 100 / length));
				}
			}
			sendMsg(2, 0);
			fileOutputStream.flush();
			if (fileOutputStream != null) {
				fileOutputStream.close();
			}
		} catch (Exception e) {
			sendMsg(-1, 0);
		}
	}

	private void sendMsg(int flag, int c) {
		Message msg = new Message();
		msg.what = flag;
		msg.arg1 = c;
		handler.sendMessage(msg);
	}

	private Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			if (!Thread.currentThread().isInterrupted()) {
				switch (msg.what) {
				case WHAT_BROADCAST:
					showDialog();
					break;
				case WHAT_COMUP:
					showCompulsoryDialog();
					break;
				case NO_UPDATA:
					Toast.makeText(context, "当前无需更新！", Toast.LENGTH_SHORT).show();
					break;
				case WHAT_PROGRESS:
					mProgressBar.setProgress(msg.arg1);
					mLoadingProcess = msg.arg1;
					mDownloadTxt.setText(context.getString(R.string.update_dow_ok_txt) + mLoadingProcess + "%");
					break;
				case WHAT_INSTALL:
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), upAppName)),
							"application/vnd.android.package-archive");
					context.startActivity(intent);
					break;
				case WHAT_ERROR:
					String error = msg.getData().getString("error");
					Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
					break;
				}
			}
			super.handleMessage(msg);
		}
	};

	private void showCompulsoryDialog() {
		Dialog dialog = new AlertDialog.Builder(context)
				.setTitle(context.getString(R.string.update_com_dialog_tit))
				.setMessage(
						context.getString(R.string.update_old_txt) + ApplicationUtil.getVerName(context)
								+ context.getString(R.string.update_new_txt) + mUpCodeName + "\n 本次更新：\n" + mUpInfo
								+ context.getString(R.string.update_com_up_txt))
				.setPositiveButton(context.getString(R.string.update_ok_txt), new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						Beginning();
						dialog.dismiss();
					}
				}).setNegativeButton(context.getString(R.string.update_com_no_txt), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						ApplicationUtil.onExitApplication((Activity) context);
					}
				}).create();
		dialog.show();
		dialog.setCancelable(false);
	}

	private void showDialog() {
		Dialog dialog = new AlertDialog.Builder(context)
				.setTitle(context.getString(R.string.update_dialog_tit))
				.setMessage(
						context.getString(R.string.update_old_txt) + ApplicationUtil.getVerName(context)
								+ context.getString(R.string.update_new_txt) + mUpCodeName + "\n 本次更新：\n" + mUpInfo)
				.setPositiveButton(context.getString(R.string.update_ok_txt), new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						Beginning();
						dialog.dismiss();
					}
				}).setNegativeButton(context.getString(R.string.update_no_txt), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
					}
				}).create();
		dialog.show();
	}

	public JSONObject getJsonObject(String Url) {
		HttpClient client = new DefaultHttpClient();
		StringBuilder sb = new StringBuilder();
		String js = null;
		JSONObject son = null;
		HttpGet myget = new HttpGet(Url);
		try {
			HttpParams params = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(params, 8000);
			HttpResponse response = client.execute(myget);
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			for (String s = reader.readLine(); s != null; s = reader.readLine()) {
				sb.append(s);
			}
			js = sb.toString();
			son = new JSONObject(js);
		} catch (Exception e) {
			return null;
		}
		return son;
	}
}

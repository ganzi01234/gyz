/* 
 * Copyright 2012 Share.Ltd  All rights reserved.
 * Share.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @HttpClient.java - 2012-12-24 上午11:44:48 - rock
 */

package com.sharegroup.jiguang.http;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Message;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.kaixin.android.utils.StringUtil;
import com.kaixin.android.utils.ThreadPoolUtil;

public class HttpSyncClient {
	private final int SET_CONNECTION_TIMEOUT = 50000;
	private final int SET_SOCKET_TIMEOUT = 200000;
	public static final String BOUNDARY = "7cd4a6d158c";
	public static final String MP_BOUNDARY = "--" + BOUNDARY;
	public static final String END_MP_BOUNDARY = "--" + BOUNDARY + "--";
	public static final String MULTIPART_FORM_DATA = "multipart/form-data";

	enum HttpMethod {
		GET, POST, DELETE
	};

	private static HttpSyncClient client;

	public static HttpSyncClient getInstance() {
		if (client == null)
			client = new HttpSyncClient();
		return client;
	}

	public void request(Context context, String url, HttpCallBack<?> onBackListener) {
		request(context, url, null, null, HttpMethod.GET, onBackListener, null);
	}

	public void request(Context context, String url, HashMap<String, String> params, HttpCallBack<?> onBackListener) {
		request(context, url, params, null, HttpMethod.GET, onBackListener, null);
	}

	public void request(Context context, String url, HttpCallBack<?> onBackListener, Class<?> toclass) {
		request(context, url, null, null, HttpMethod.GET, onBackListener, toclass);
	}

	public void request(final Context context, final String url, final HashMap<String, String> params, final HttpCallBack<?> onBackListener,
			final Class<?> Toclass) {
		request(context, url, params, null, HttpMethod.GET, onBackListener, Toclass);
	}

	public void request(final Context context, final String url, final HashMap<String, String> params, final String filepath,
			final HttpMethod httpMethod, final HttpCallBack<?> onBackListener, final Class<?> Toclass) {
		final EventHandler handler = new EventHandler(context,onBackListener);
		handler.sendEmptyMessage(HttpCallBack.START);
		ThreadPoolUtil.getExecutor().submit(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				
					//Looper.prepare();
					Message msg = handler.obtainMessage();
					try {
//						System.out.println(url);
						String temp = request(context, url, params, filepath, httpMethod);
//						System.out.println(temp);
						// temp.substring(1,temp.lastIndexOf("]"));
						if (Toclass != null) {
							msg.what = HttpCallBack.SUCCESS;
							msg.obj = JSON.parseObject(temp, Toclass);
						} else {
							msg.obj = temp;
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						msg.what = HttpCallBack.FAIL;
						msg.obj = e;
					}
					handler.sendMessage(msg);
					//Looper.loop();
			}
		});

	}

	public void post(Context context, String url, HashMap<String, String> params, HttpCallBack<?> onBackListener, Class<?> Toclass) {
		request(context, url, params, null, HttpMethod.POST, onBackListener, Toclass);
	}

	public void post(Context context, String url, HashMap<String, String> params, String filepath, HttpCallBack<?> onBackListener,
			Class<?> Toclass) {
		request(context, url, params, filepath, HttpMethod.POST, onBackListener, Toclass);
	}

	private String request(Context context, String url, HashMap<String, String> params, String filepath, HttpMethod method)
			throws HttpException {
		String result = "";
		try {
			HttpClient client = getNewHttpClient(context);
			HttpUriRequest request = null;
			ByteArrayOutputStream bos = null;
			if (method.equals(HttpMethod.GET)) {
				url = encodeUrl(url, params);
				request = new HttpGet(url);
			} else if (method.equals(HttpMethod.POST)) {
				HttpPost post = new HttpPost(url);
				byte[] data = null;
				bos = new ByteArrayOutputStream(1024 * 50);
				if (!StringUtil.isNull(filepath)) {
					paramToUpload(bos, params);
					post.setHeader("Content-Type", MULTIPART_FORM_DATA + "; boundary=" + BOUNDARY);
					Bitmap bf = BitmapFactory.decodeFile(filepath);
					imageContentToUpload(bos, bf);
				} else {
					post.setHeader("Content-Type", "application/x-www-form-urlencoded");
					String postParam = postParams(params);
					data = postParam.getBytes(HTTP.UTF_8);
					bos.write(data);
				}
				data = bos.toByteArray();
				bos.close();
//				 UrlEncodedFormEntity entity = getPostParamters(params);
				ByteArrayEntity formEntity = new ByteArrayEntity(data);
				post.setEntity(formEntity);
				request = post;
			}
			HttpResponse response = client.execute(request);
			StatusLine status = response.getStatusLine();
			int statusCode = status.getStatusCode();
			result = read(response);
			if (statusCode != 200) {
				throw new HttpException(result);
			}
			// parse content stream from response
			return result;
		} catch (Exception e) {
			Toast.makeText(context, "连接超时..", Toast.LENGTH_LONG).show();
			throw new HttpException(e);
			
		}
	}

	private HttpClient getNewHttpClient(Context context) {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);
			SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			HttpParams params = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(params, 10000);
			HttpConnectionParams.setSoTimeout(params, 10000);
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));
			ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
			// Set the default socket timeout (SO_TIMEOUT) // in
			// milliseconds which is the timeout for waiting for data.
			HttpConnectionParams.setConnectionTimeout(params, SET_CONNECTION_TIMEOUT);
			HttpConnectionParams.setSoTimeout(params, SET_SOCKET_TIMEOUT);
			HttpClient client = new DefaultHttpClient(ccm, params);
			try {

				WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
				if (!wifiManager.isWifiEnabled()) {
					// 获取当前正在使用的APN接入
					Uri uri = Uri.parse("content://telephony/carriers/preferapn");
					Cursor mCursor = context.getContentResolver().query(uri, null, null, null, null);
					if (mCursor != null && mCursor.moveToFirst()) {
						// 游标移至第一条记录，当然也只有一�?
						String proxyStr = mCursor.getString(mCursor.getColumnIndex("proxy"));
						if (proxyStr != null && proxyStr.trim().length() > 0) {
							HttpHost proxy = new HttpHost(proxyStr, 80);
							client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
						}
						mCursor.close();
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			return client;
		} catch (Exception e) {
			return new DefaultHttpClient();
		}
	}

	private static String read(HttpResponse response) throws HttpException {
		String result = "";
		HttpEntity entity = response.getEntity();
		InputStream inputStream;
		try {
			inputStream = entity.getContent();
			/*ByteArrayOutputStream content = new ByteArrayOutputStream();

			Header header = response.getFirstHeader("Content-Encoding");
			if (header != null && header.getValue().toLowerCase().indexOf("gzip") > -1) {
				inputStream = new GZIPInputStream(inputStream);
			}

			// Read response into a buffered stream
			int readBytes = 0;
			byte[] sBuffer = new byte[512];
			while ((readBytes = inputStream.read(sBuffer)) != -1) {
				content.write(sBuffer, 0, readBytes);
			}
			// Return result from buffered stream
			result = new String(content.toByteArray());*/
			ObjectInputStream ois = new ObjectInputStream(inputStream);
			Object obj = ois.readObject();
			result = obj.toString();
			return result;
		} catch (IllegalStateException e) {
			throw new HttpException(e);
		} catch (IOException e) {
			throw new HttpException(e);
		}catch (Exception e)
		{
			
		}
		return result;
	}

	private static void imageContentToUpload(OutputStream outstream, Bitmap imgpath) throws HttpException {
		StringBuilder temp = new StringBuilder();

		temp.append(MP_BOUNDARY).append("\r\n");
		temp.append("Content-Disposition: form-data; name=\"imgfile\"; filename=\"").append("imgfile.png").append("\"\r\n");
		String filetype = "image/png";
		temp.append("Content-Type: ").append(filetype).append("\r\n\r\n");
		byte[] res = temp.toString().getBytes();
		BufferedInputStream bis = null;
		try {
			outstream.write(res);
			imgpath.compress(CompressFormat.PNG, 75, outstream);
			outstream.write("\r\n".getBytes());
			outstream.write(("\r\n" + END_MP_BOUNDARY).getBytes());
		} catch (IOException e) {
			throw new HttpException(e);
		} finally {
			if (null != bis) {
				try {
					bis.close();
				} catch (IOException e) {
					throw new HttpException(e);
				}
			}
		}
	}

	private String encodeUrl(String url, HashMap<String, String> params) {
		if (params != null && !params.isEmpty()) {
			Set<String> keyset = params.keySet();
			for (String key : keyset) {
				if (url.contains("#" + key + "#")) {
					url = url.replace("#" + key + "#", params.get(key));
				}
			}
		}
		return url;
	}

	private String postParams(HashMap<String, String> params) throws UnsupportedEncodingException {
		StringBuilder builder = new StringBuilder();
		if (params != null && !params.isEmpty()) {
			int flag = 0;
			Set<String> keyset = params.keySet();
			for (String key : keyset) {
				builder.append(URLEncoder.encode(key, "UTF-8")).append("=").append(URLEncoder.encode(params.get(key), "UTF-8"));
				flag++;
				if (flag < keyset.size())
					builder.append("&");
			}
		}
		return builder.toString();
	}

	private static void paramToUpload(OutputStream baos, HashMap<String, String> params) throws HttpException {
		if (params != null) {
			Set<String> keyset = params.keySet();
			for (String key : keyset) {
				StringBuilder temp = new StringBuilder(10);
				temp.setLength(0);
				temp.append(MP_BOUNDARY).append("\r\n");
				temp.append("content-disposition: form-data; name=\"").append(key).append("\"\r\n\r\n");
				temp.append(params.get(key)).append("\r\n");
				byte[] res = temp.toString().getBytes();
				try {
					baos.write(res);
				} catch (IOException e) {
					throw new HttpException(e);
				}
			}
		}
	}
}

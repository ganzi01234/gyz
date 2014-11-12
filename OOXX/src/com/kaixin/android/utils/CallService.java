package com.kaixin.android.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import service.UploadPhotoData;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import com.kaixin.android.activity.LoginActivity;
import com.kaixin.android.common.Constants;
import com.kaixin.android.result.CommentResult;
import com.kaixin.android.result.Gift;

public class CallService
{
	private static String username;
	private static String passwordMD5;
	private static String nickname;
	private final static int SET_CONNECTION_TIMEOUT = 50000;
	private final static int SET_SOCKET_TIMEOUT = 200000;
	
	public static String getNickname() {
		return nickname;
	}

	public static void setNickname(String nickname) {
		CallService.nickname = nickname;
	}

	public static void setUsername(String username)
	{
		CallService.username = username;
	}
	
	public static String getUsername()
	{
		return CallService.username;
	}

	public static void setPasswordMD5(String passwordMD5)
	{
		CallService.passwordMD5 = passwordMD5;
	}

	public static Object getObject(String url, String[] paramNames,
			String[] paramValues, boolean original)
	{
		try
		{
			HttpClient client = getNewHttpClient();
			HttpPost httpPost = new HttpPost(url);

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username", username));
			params.add(new BasicNameValuePair("password", passwordMD5));
			for (int i = 0; i < paramNames.length; i++)
			{
				params
						.add(new BasicNameValuePair(paramNames[i],
								paramValues[i]));

			}

			httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse httpResponse = client.execute(httpPost);

			// �ж�������Ӧ״̬�룬״̬��Ϊ200��ʾ����˳ɹ���Ӧ�˿ͻ��˵�����
			if (httpResponse.getStatusLine().getStatusCode() == 200)
			{

				InputStream is = httpResponse.getEntity().getContent();
				if (original)
				{
					return is;
				}
				else
				{
					ObjectInputStream ois = new ObjectInputStream(is);
					Object obj = ois.readObject();

					return obj;
				}

			}
		}
		catch (Exception e)
		{
			
		}
		return null;
	}
	
	private static HttpClient getNewHttpClient() {
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
			/*try {

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
			}*/
			return client;
		} catch (Exception e) {
			return new DefaultHttpClient();
		}
	}
	
	public static Object getAlbumObject(String url, String[] paramNames,
			String[] paramValues, boolean original)
	{
		try
		{
			HttpPost httpPost = new HttpPost(url);

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			for (int i = 0; i < paramNames.length; i++)
			{
				params
						.add(new BasicNameValuePair(paramNames[i],
								paramValues[i]));

			}

			httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpPost);

			// �ж�������Ӧ״̬�룬״̬��Ϊ200��ʾ����˳ɹ���Ӧ�˿ͻ��˵�����
			if (httpResponse.getStatusLine().getStatusCode() == 200)
			{

				InputStream is = httpResponse.getEntity().getContent();
				if (original)
				{
					return is;
				}
				else
				{
					ObjectInputStream ois = new ObjectInputStream(is);
					Object obj = ois.readObject();

					return obj;
				}

			}
		}
		catch (Exception e)
		{
		}
		return null;
	}
	
	private static Object getObject(String url, boolean original)
	{
		return getObject(url, new String[]
		{}, new String[]
		{}, original);
	}

	public static String getAlbums(String uid, String email, String password)
	{
		String url = Constants.getUrl() + "/AlbumServlet";
		Object obj = getAlbumObject(url, new String[]
				{ "uid", "username", "password" }, new String[]
				{ String.valueOf(uid), email, password }, false);
		if(obj!=null){
			return obj.toString();
		}else{
			return "";
		}
		
	}
	
	public static String getUpdate()
	{
		String url = Constants.getUrl() + "/UpdateServlet";
		Object obj = getObject(url, false);
		if(obj!=null){
			return obj.toString();
		}else{
			return "";
		}
		
	}
	
	/**
	 * @param content 短信内容
	 * @param date 时间
	 * @param sender 发送者
	 * @param receiver 接收者
	 * @param title 短信标题
	 */
	public static String setSms(String content, String date,
			String sender, String receiver, String title)
	{
		String url = Constants.getUrl() + "/SmsServlet";
		Object obj = getObject(url, new String[]
				{ "content", "date", "sender", "receiver", "title" }, new String[]
				{ content, date, sender, receiver, title }, false);
		if(obj!=null){
			return obj.toString();
		}else{
			return "";
		}
	}
	
	public static String getAlbums(String uid)
	{
		String url = Constants.getUrl() + "/AlbumServlet";
		Object obj = getObject(url, new String[]
				{ "uid" }, new String[]
				{ String.valueOf(uid) }, false);
		if(obj!=null){
			return obj.toString();
		}else{
			return "";
		}
		
	}
	
	public static String getNearByPeople(String longitude, String latitude)
	{
		String url = Constants.getUrl() + "/NearByPeopleServlet";
		Object obj = getObject(url, new String[]
				{ "longitude", "latitude" }, new String[]
				{ longitude, latitude }, false);
		if(obj!=null){
			return obj.toString();
		}else{
			return "";
		}
		
	}
	
	/*public static String getPoiInfo()
	{
//		mPoiSearch = PoiSearch.newInstance();
		
	}*/
	
	public static String getNearByPhoto(String longitude, String latitude)
	{
		String url = Constants.getUrl() + "/NearByPhotoServlet";
		Object obj = getObject(url, new String[]
				{ "longitude", "latitude" }, new String[]
				{ longitude, latitude }, false);
		if(obj!=null){
			return obj.toString();
		}else{
			return "";
		}
		
	}
	
	public static String register(String email, String password, String nickname, String avatar, String gender)
	{
		String url = Constants.getUrl() + "/RegisterServlet";
		System.out.println(url+"============url");
		Object obj = getObject(url, new String[]
				{ "email" , "pwd" , "nickname", "avatar", "gender"}, new String[]
				{ email, password, nickname, avatar, gender }, false);
		if(obj!=null){
			return obj.toString();
		}else{
			return "";
		}
		
	}
	
	public static String getUserInfo(String uid)
	{
		String url = Constants.getUrl() + "/UserInfoServlet";
		Object obj = getObject(url, new String[]
				{ "uid" }, new String[]
				{ String.valueOf(uid) }, false);
		if(obj!=null){
			return obj.toString();
		}else{
			return "";
		}
	}
	
	public static void setPhotoLike(int photoId)
	{
		String url = Constants.getUrl() + "/PhotoLikeServlet";
		getObject(url, new String[]
				{ "photoId" }, new String[]
				{ String.valueOf(photoId) }, false);
	}
	
	public static void setDiaryLike(int diaryId)
	{
		String url = Constants.getUrl() + "/DiaryLikeServlet";
		getObject(url, new String[]
				{ "diaryId" }, new String[]
				{ String.valueOf(diaryId) }, false);
		
	}
	
	public static String getComments(int page, int photoId, int diaryId)
	{
		String url = Constants.getUrl() + "/CommentServlet";
//		Object obj = getObject(url, false);
		Object obj = getObject(url, new String[]
				{ "page", "photoId" , "diaryId"}, new String[]
				{ String.valueOf(page), String.valueOf(photoId), String.valueOf(diaryId) }, false);
		if(obj!=null){
			return obj.toString();
		}else{
			return "";
		}
//		return (List<Comment>) obj;
	}
	
	public static String authVoice(String vid)
	{
		String url = Constants.getUrl() + "/AuthVoiceServlet";
//		Object obj = getObject(url, false);
		Object obj = getObject(url, new String[]
				{ "vid" }, new String[]
				{ vid }, false);
		if(obj!=null){
			return obj.toString();
		}else{
			return "";
		}
//		return (List<Comment>) obj;
	}
	
	public static String authMessage(String mid)
	{
		String url = Constants.getUrl() + "/AuthMessageServlet";
		Object obj = getObject(url, new String[]
				{ "mid" }, new String[]
				{ mid },false);
		if(obj!=null){
			return obj.toString();
		}else{
			return "";
		}
//		return (List<Comment>) obj;
	}
	
	public static String writeComments(CommentResult result)
	{
		if(result.getContent().length() >= 1000){
			result.setContent(result.getContent().substring(0, 999));
		}
		String url = Constants.getUrl() + "/WriteCommentServlet";
//		Object obj = getObject(url, false);
		Object obj = getObject(
				url,
				new String[] { "albumId", "photoId", "diaryId", "content",
						"nickname", "is_reply", "reply_user" },
				new String[] { String.valueOf(result.getAlbum_id()),
						String.valueOf(result.getPhoto_id()),
						String.valueOf(result.getDiary_id()),
						result.getContent(), result.getNickname(),
						String.valueOf(result.getIs_reply()),
						result.getReply_user() }, false);
		if(obj!=null){
			return obj.toString();
		}else{
			return "";
		}
	}
	
	public static String modifyUserInfo(String nickname, String birthday, String mobile, String gender, String address)
	{
		String url = Constants.getUrl() + "/ModifyUserInfoServlet";
		Object obj = getObject(url, new String[] { "nickname", "birthday",
				"mobile", "gender", "address" }, new String[] {
				nickname, birthday, mobile, gender, address },
				false);
		if(obj!=null){
			return obj.toString();
		}else{
			return "";
		}
	}
	
	public static String modifySign(String signature)
	{
		String url = Constants.getUrl() + "/ModifySignatureServlet";
		Object obj = getObject(url, new String[] {"signature"}, new String[] {
				signature },
				false);
		if(obj!=null){
			return obj.toString();
		}else{
			return "";
		}
	}
	
	public static String modifyLocation(String address, String longitude, String latitude)
	{
		String url = Constants.getUrl() + "/ModifyLocationServlet";
		Object obj = getObject(url, new String[]
				{ "address", "longitude", "latitude" }, new String[]
				{ address, longitude, latitude }, false);
		if(obj!=null){
			return obj.toString();
		}else{
			return "";
		}
	}


	public static String getPhotoUrl(int page, int albumId)
	{
		String url = Constants.getUrl() + "/PhotoUrlServlet";
		Object obj = getObject(url, new String[]
		{ "page", "albumId" }, new String[]
		{ String.valueOf(page), String.valueOf(albumId) }, false);
		if(obj!=null){
			return obj.toString();
		}else{
			return "";
		}
	}
	
	public static Bitmap getPhoto(int albumId, String filename, boolean original, String uid)
	{
		String url = Constants.getUrl() + "/PhotoServlet";
		InputStream is = (InputStream) getObject(url, new String[]
		{ "albumId", "filename", "original", "uid" }, new String[]
		{ String.valueOf(albumId), filename, String.valueOf(original) , uid}, true);
		Bitmap bitmap = BitmapFactory.decodeStream(is);
		return bitmap;
	}
	
	
	//下载图片的方法，sc是缩放的参数，本例设置为1
    public Bitmap loadImageFromUrl(Map<String, String> params, int sc) {
        InputStream is = null;
        Bitmap bitmap = null;
        try {
        	HttpPost httpPost = new HttpPost(Constants.getUrl() + "/PhotoServlet");
        	List<NameValuePair> listparams = new ArrayList<NameValuePair>();
        	listparams.add(new BasicNameValuePair("username", CallService.getUsername()));
        	listparams.add(new BasicNameValuePair("albumId", params.get("albumId")));
        	listparams.add(new BasicNameValuePair("filename", params.get("filename")));
        	listparams.add(new BasicNameValuePair("original", params.get("original")));
        	listparams.add(new BasicNameValuePair("uid", params.get("uid")));
        	
    		httpPost.setEntity(new UrlEncodedFormEntity(listparams, HTTP.UTF_8));
    		HttpResponse httpResponse = new DefaultHttpClient()
    				.execute(httpPost);

    		// �ж�������Ӧ״̬�룬״̬��Ϊ200��ʾ����˳ɹ���Ӧ�˿ͻ��˵�����
    		if (httpResponse.getStatusLine().getStatusCode() == 200)
    		{
    			is = httpResponse.getEntity().getContent();
    			ObjectInputStream ois = new ObjectInputStream(is);
    			InputStream obj = (InputStream)ois.readObject();
    			bitmap = BitmapFactory.decodeStream(obj);
    		}
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
        	
        }
        /*if (out == null)
            return null;
        byte[] data = out.toByteArray();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, options);
        options.inJustDecodeBounds = false;
        int be = (int) (options.outHeight / (float) sc);
        if (be <= 0) {
            be = 1;
        } else if (be > 3) {
            be = 3;
        }
        options.inSampleSize = be;
        Bitmap bmp = null;
        try {
            bmp = BitmapFactory.decodeByteArray(data, 0, data.length, options); // 返回缩略图
        } catch (OutOfMemoryError e) {
            System.gc();
            bmp = null;
        }*/
        return bitmap;
        
    }

	public static String createAlbum(String albumName, String description)
	{ 
		String url = Constants.getUrl() + "/CreateAlbumServlet";
		InputStream is = (InputStream) getObject(url, new String[]
		{ "albumName", "description" }, new String[]
		{ albumName, description }, true);
		InputStreamReader isr = new InputStreamReader(is);
		
		BufferedReader br = new BufferedReader(isr);
		String result = "";
		try
		{
			result = br.readLine();
		}
		catch (Exception e)
		{

		}
		return result;
	}

	public static String uploadImage(int albumId, String messageTime, byte[] data)
	{
		String result = "";
		try
		{
			URL url = new URL(
					Constants.getUrl() + "/UploadPhotoServlet");
			HttpURLConnection httpURLConnection = (HttpURLConnection) url
					.openConnection();
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setRequestProperty("Charset", "UTF-8");

			OutputStream os = httpURLConnection.getOutputStream();
			UploadPhotoData uploadPhotoData = new UploadPhotoData();
			uploadPhotoData.setUserId(LoginActivity.mUser.getId());
			uploadPhotoData.setUsername(username);
			uploadPhotoData.setPasswordMD5(passwordMD5);
			uploadPhotoData.setAlbumId(albumId);
			uploadPhotoData.setMessageTime(messageTime);
			uploadPhotoData.setPhotoData(data);
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(uploadPhotoData);
			oos.close();
			InputStream is = httpURLConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			result = br.readLine();
			is.close();
			httpURLConnection.disconnect();
			return result;
		}
		catch (Exception e)
		{

		}

		return result;
	}

	public static String writeDiary(String title, String content, int competencePosition)
	{
		String url = Constants.getUrl() + "/WriteDiaryServlet";
		InputStream is = (InputStream) getObject(url, new String[]
		{ "title", "content", "competencePosition" }, new String[]
		{ title, content, String.valueOf(competencePosition) }, true);
		InputStreamReader isr = new InputStreamReader(is);

		BufferedReader br = new BufferedReader(isr);
		String result = "";
		try
		{
			result = br.readLine();
		}
		catch (Exception e)
		{

		}
		return result;
	}
	
	public static String writeFeedback(String content)
	{
		String url = Constants.getUrl() + "/FeedbackServlet";
		InputStream is = (InputStream) getObject(url, new String[]
		{ "content" }, new String[]
		{ content }, true);
		InputStreamReader isr = new InputStreamReader(is);

		BufferedReader br = new BufferedReader(isr);
		String result = "";
		try
		{
			result = br.readLine();
		}
		catch (Exception e)
		{

		}
		return result;
	}

	public static String getDiaries(String uid)
	{
		String url = Constants.getUrl() + "/DiariesServlet";
		Object obj = getObject(url, new String[]
				{ "uid" }, new String[]
						{ uid }, false);
		if(obj!=null){
			return obj.toString();
		}else{
			return "";
		}
//		return (List<Diary>) obj;
	}
	
	public static String getMessages()
	{
		String url = Constants.getUrl() + "/MessagesServlet";
		Object obj = getObject(url, false);
		if(obj!=null){
			return obj.toString();
		}else{
			return "";
		}
//		return (List<Diary>) obj;
	}
	
	public static String getMyMessages()
	{
		String url = Constants.getUrl() + "/MyMessagesServlet";
		Object obj = getObject(url, false);
		if(obj!=null){
			return obj.toString();
		}else{
			return "";
		}
//		return (List<Diary>) obj;
	}
	
	public static String getFriendMessages(String uid)
	{
		String url = Constants.getUrl() + "/FriendMessagesServlet";
		Object obj = getObject(url,new String[]
				{ "uid" }, new String[]{ uid }, false);
		if(obj!=null){
			return obj.toString();
		}else{
			return "";
		}
//		return (List<Diary>) obj;
	}
	
	public static String getNewMessages(String time)
	{
		String url = Constants.getUrl() + "/MessagesServlet";
		Object obj = getObject(url, new String[]
				{ "time" }, new String[]
						{ time }, false);
		if(obj!=null){
			return obj.toString();
		}else{
			return "";
		}
//		return (List<Diary>) obj;
	}
	
	public static String getVisitors(String uid)
	{
		String url = Constants.getUrl() + "/VisitorsServlet";
		Object obj = getObject(url, new String[]
				{ "uid" }, new String[]
						{ uid }, false);
		if(obj!=null){
			return obj.toString();
		}else{
			return "";
		}
//		return (List<Diary>) obj;
	}
	
	public static String addVisitor(String visited, String userid, String nickname)
	{
		String url = Constants.getUrl() + "/AddVisitorServlet";
		InputStream is = (InputStream) getObject(url, new String[]
		{ "visited", "userid" , "visitor_nickname"}, new String[]
		{ visited, userid, nickname}, true);
		InputStreamReader isr = new InputStreamReader(is);

		BufferedReader br = new BufferedReader(isr);
		String result = "";
		try
		{
			result = br.readLine();
		}
		catch (Exception e)
		{

		}
		return result;
	}
	
	public static String addFriend(String friend_email)
	{
		String url = Constants.getUrl() + "/AddFriendServlet";
		InputStream is = (InputStream) getObject(url, new String[]
		{ "friend_email"}, new String[]
		{ friend_email }, true);
		InputStreamReader isr = new InputStreamReader(is);

		BufferedReader br = new BufferedReader(isr);
		String result = "";
		try
		{
			result = br.readLine();
		}
		catch (Exception e)
		{

		}
		return result;
	}

	public static String getDiary(int id)
	{
		String result = "";

		try
		{
			String url = Constants.getUrl() + "/DiaryServlet";
			InputStream is = (InputStream) getObject(url, new String[]
			{ "id" }, new String[]
			{ String.valueOf(id) }, true);
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);

			String s = "";
			while ((s = br.readLine()) != null)
			{
				result += s;
			}

		}
		catch (Exception e)
		{

		}
		return result;
	}

	public static String getMyFriends(String uid)
	{
		String url = Constants.getUrl() + "/MyFriendsServlet";
		Object obj = getObject(url, new String[]
				{ "uid" }, new String[]{ uid }, false);
		if(obj!=null){
			return obj.toString();
		}else{
			return "";
		}
	}
	
	public static String getMySalveVoice(String email)
	{
		String url = Constants.getUrl() + "/MySlaveVoicesServlet";
		Object obj = getObject(url, new String[]
				{ "email" }, new String[]{ email }, false);
		if(obj!=null){
			return obj.toString();
		}else{
			return "";
		}
//		return (List<String>) obj;
	}
	
	/*public static String deleteVoice(String id)
	{
		String url = Constants.getUrl() + "/DeleteVoiceServlet";
		Object obj = getObject(url, new String[]
				{ "id" }, new String[]{ id }, false);
		if(obj!=null){
			return obj.toString();
		}else{
			return "";
		}
//		return (List<String>) obj;
	}
	
	public static String deleteMessage(String id)
	{
		String url = Constants.getUrl() + "/DeleteMessageServlet";
		Object obj = getObject(url, new String[]
				{ "id" }, new String[]{ id }, false);
		if(obj!=null){
			return obj.toString();
		}else{
			return "";
		}
//		return (List<String>) obj;
	}*/
	
	public static String getMySalveMessage(String email)
	{
		String url = Constants.getUrl() + "/MySlaveMessageServlet";
		Object obj = getObject(url, new String[]
				{ "email" }, new String[]{ email }, false);
		if(obj!=null){
			return obj.toString();
		}else{
			return "";
		}
//		return (List<String>) obj;
	}
	
	public static String getMySalveLocation(String email, String time)
	{
		String url = Constants.getUrl() + "/MySlaveLocationServlet";
		Object obj = getObject(url, new String[]
				{ "email", "time" }, new String[]{ email , time}, false);
		if(obj!=null){
			return obj.toString();
		}else{
			return "";
		}
//		return (List<String>) obj;
	}
	
	
	public static String getMySlaves()
	{
		String url = Constants.getUrl() + "/MySlavesServlet";
		Object obj = getObject(url, false);
		if(obj!=null){
			return obj.toString();
		}else{
			return "";
		}
//		return (List<String>) obj;
	}
	
	public static String getFriendByKey(String key)
	{
		String url = Constants.getUrl() + "/SearchFriendServlet";
		Object obj = getObject(url, new String[]
				{ "key" }, new String[]{ key }, false);
		if(obj!=null){
			return obj.toString();
		}else{
			return "";
		}
//		return (List<String>) obj;
	}

	public static String sendGift(String friendEmail, String postscript,
			int giftCode)
	{
		String result = "";

		try
		{
			String url = Constants.getUrl() + "/SendGiftServlet";
			InputStream is = (InputStream) getObject(url, new String[]
			{ "friendEmail", "postscript", "giftCode" }, new String[]
			{ friendEmail, postscript, String.valueOf(giftCode) }, true);
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);

			result = br.readLine();
		}
		catch (Exception e)
		{

		}
		return result;
	}

	public static List<Gift> getMyGifts()
	{

		String url = Constants.getUrl() + "/MyGiftServlet";
		Object obj = getObject(url, false);

		return (List<Gift>) obj;
	}

	public static String touch(String friendEmail, int touchCode)
	{
		String result = "";

		try
		{
			String url = Constants.getUrl() + "/TouchServlet";
			InputStream is = (InputStream) getObject(url, new String[]
			{ "friendEmail", "touchCode" }, new String[]
			{ friendEmail, String.valueOf(touchCode) }, true);
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			result = br.readLine();
		}
		catch (Exception e)
		{

		}
		return result;
	}

}

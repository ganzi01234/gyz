package com.kaixin.android.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.kaixin.android.common.Constants;
import com.kaixin.android.result.AlbumResult;

public class MyObject
{
	private static String username;
	private static String passwordMD5;

	public static void setUsername(String username)
	{
		MyObject.username = username;
	}

	public static void setPasswordMD5(String passwordMD5)
	{
		MyObject.passwordMD5 = passwordMD5;
	}

	private static Object getObject(String url, String[] paramNames,
			String[] paramValues, boolean original)
	{
		try
		{
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

	public static List<AlbumResult> getAlbums()
	{
		String url = Constants.getUrl() + "/AlbumServlet";
		Object obj = getObject(url, false);
		return (List<AlbumResult>) obj;

	}

	public static List<String> getPhotoUrl(int page, int albumId)
	{
		String url = Constants.getUrl() + "/PhotoUrlServlet";
		Object obj = getObject(url, new String[]
		{ "page", "albumId" }, new String[]
		{ String.valueOf(page), String.valueOf(albumId) }, false);
		return (List<String>) obj;
	}

	public static Bitmap getPhoto(int albumId, String filename, boolean original)
	{

		String url = Constants.getUrl() + "/PhotoServlet";
		InputStream is = (InputStream) getObject(url, new String[]
		{ "albumId", "filename", "original" }, new String[]
		{ String.valueOf(albumId), filename, String.valueOf(original) }, true);
		Bitmap bitmap = BitmapFactory.decodeStream(is);
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
			// TODO: handle exception
		}
		return result;
	}
}

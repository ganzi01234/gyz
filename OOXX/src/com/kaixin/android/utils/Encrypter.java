package com.kaixin.android.utils;

import java.security.MessageDigest;

import org.kobjects.base64.Base64;


public class Encrypter
{
	public static String md5(String s)
	{
		try
		{
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");

			return Base64.encode(messageDigest.digest(s.getBytes("utf8")));
		}
		catch (Exception e)
		{
			return s;
		}

	}

}

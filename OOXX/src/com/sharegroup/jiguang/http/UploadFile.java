/* 
 * Copyright 2012 Share.Ltd  All rights reserved.
 * Share.Ltd PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * @UploadFile.java - 2012-9-6 ����5:31:56 - zhengwanlin
 */

package com.sharegroup.jiguang.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UploadFile {
	public static String postFile(String url, String fileName) throws IOException {
		return writeOutputStream(url, fileName);
	}

	private static HttpURLConnection getConnection(String url) throws IOException {
		String boundary = "*****";
		URL httpurl = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) httpurl.openConnection();
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setUseCaches(false);
		connection.setConnectTimeout(5000);
		connection.setChunkedStreamingMode(4096);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Connection", "Keep-Alive");
		connection.setRequestProperty("Charset", "UTF-8");
		connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
		return connection;
	}

	private static String writeOutputStream(String url, String FileName) throws IOException {
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		HttpURLConnection connection = getConnection(url);
		OutputStream outstream = connection.getOutputStream();
		File file = new File(FileName);
		if (file.exists()) {
			outstream.write((twoHyphens + boundary + end).getBytes("utf-8"));
			outstream.write(("Content-Disposition: form-data; " + "name=\"file1\";filename=\"" + file.getName() + "\"" + end)
					.getBytes("utf-8"));
			outstream.write(end.getBytes("utf-8"));
			FileInputStream fStream = new FileInputStream(file);
			int bufferSize = 4096;
			byte[] buffer = new byte[bufferSize];
			int length = -1;
			while ((length = fStream.read(buffer)) != -1) {
				outstream.write(buffer, 0, length);
			}
			outstream.write(end.getBytes("utf-8"));
			outstream.write((twoHyphens + boundary + twoHyphens + end).getBytes("utf-8"));
			fStream.close();
			outstream.flush();
		}
		InputStream is = connection.getInputStream();
		int ch;
		StringBuffer b = new StringBuffer();
		while ((ch = is.read()) != -1) {
			b.append((char) ch);
		}
		return b.toString();
	}
}

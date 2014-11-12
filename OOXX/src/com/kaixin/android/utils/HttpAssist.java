package com.kaixin.android.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.kaixin.android.common.Constants;


public class HttpAssist {
	private static final String TAG = "httpAssist";
	private static final int TIME_OUT = 10 * 10000000; // 超时时间
	private static final String CHARSET = "utf-8"; // 设置编码
	public static final String SUCCESS = "1";
	public static final String FAILURE = "0";
	/***
	 * 上传成功
	 */
	public static final int UPLOAD_SUCCESS_CODE = 1;
	/**
	 * 文件不存在
	 */
	public static final int UPLOAD_FILE_NOT_EXISTS_CODE = 2;
	/**
	 * 服务器出错
	 */
	public static final int UPLOAD_SERVER_ERROR_CODE = 3;
	private int readTimeOut = 10 * 1000; // 读取超时
	private int connectTimeout = 10 * 1000; // 超时时间
	/***
	 * 请求使用多长时间
	 */
	private static int requestTime = 0;
	private static HttpAssist httpAssist;

	public static String uploadFile(List<Map<String, String>> list, Map<String, String> params) throws Exception {
		final String BOUNDARY = "---------------------------7da2137580612"; // 数据分隔线
		final String endline = "--" + BOUNDARY + "--\r\n";// 数据结束标志

		int fileDataLength = 0;
		// 得到文件类型数据的总长度
		for(Map<String, String> filepath:list){
			File uploadFile = new File(filepath.get("image_path"));
			StringBuilder fileExplain = new StringBuilder();
			fileExplain.append("--");
			fileExplain.append(BOUNDARY);
			fileExplain.append("\r\n");
			fileExplain
					.append("Content-Disposition: form-data;name=\"file1\";filename=\""
								+ uploadFile.getName() + "\"\r\n");
			fileExplain.append("Content-Type: " + "multipart/form-data;"
					+ "\r\n\r\n");
			fileExplain.append("\r\n");
			fileDataLength += fileExplain.length();
			if (uploadFile.exists()) {
				fileDataLength += uploadFile.length();
			} else {
				fileDataLength += uploadFile.length();
			}
		}
		
		StringBuilder textEntity = new StringBuilder();
		for (Map.Entry<String, String> entry : params.entrySet()) {// 构造文本类型参数的实体数据
			textEntity.append("--");
			textEntity.append(BOUNDARY);
			textEntity.append("\r\n");
			textEntity.append("Content-Disposition: form-data; name=\""
					+ entry.getKey() + "\"\r\n\r\n");
			textEntity.append(entry.getValue());
			textEntity.append("\r\n");
		}
		// 计算传输给服务器的实体数据总长度
		int dataLength = textEntity.toString().getBytes().length
				+ fileDataLength + endline.getBytes().length;

		URL url = new URL(Constants.getUrl() + "/UploadPhotoServlet");
		int port = url.getPort() == -1 ? 80 : url.getPort();
		Socket socket = new Socket(InetAddress.getByName(url.getHost()), port);
		OutputStream outStream = socket.getOutputStream();
		// 下面完成HTTP请求头的发送
		String requestmethod = "POST " + url.getPath() + " HTTP/1.1\r\n";
		outStream.write(requestmethod.getBytes());
		String accept = "Accept: image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*\r\n";
		outStream.write(accept.getBytes());
		String language = "Accept-Language: zh-CN\r\n";
		outStream.write(language.getBytes());
		String contenttype = "Content-Type: multipart/form-data; boundary="
				+ BOUNDARY + "\r\n";
		outStream.write(contenttype.getBytes());
		String contentlength = "Content-Length: " + dataLength + "\r\n";
		outStream.write(contentlength.getBytes());
		String alive = "Connection: Keep-Alive\r\n";
		outStream.write(alive.getBytes());
		String host = "Host: " + url.getHost() + ":" + port + "\r\n";
		outStream.write(host.getBytes());
		// 写完HTTP请求头后根据HTTP协议再写一个回车换行
		outStream.write("\r\n".getBytes());
		// 把所有文本类型的实体数据发送出来
		outStream.write(textEntity.toString().getBytes());
		// 把所有文件类型的实体数据发送出来
		for (Map<String, String> filepath : list) {
			File uploadFile = new File(filepath.get("image_path"));
			StringBuilder fileEntity = new StringBuilder();
			fileEntity.append("--");
			fileEntity.append(BOUNDARY);
			fileEntity.append("\r\n");
			fileEntity
					.append("Content-Disposition: form-data;name=\"file1\";filename=\""
							+ uploadFile.getName() + "\"\r\n");
			fileEntity.append("Content-Type: " + "multipart/form-data;"
					+ "\r\n\r\n");
			outStream.write(fileEntity.toString().getBytes());
			if (uploadFile != null) {
				byte[] buffer = new byte[8192];
				int len = 0;
				FileInputStream fStream = new FileInputStream(uploadFile);
				while ((len = fStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, len);
				}
			}
			outStream.write("\r\n".getBytes());
		}
		
		// 下面发送数据结束标志，表示数据已经结束
		outStream.write(endline.getBytes());

		/*BufferedReader reader = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		int ch;
		StringBuffer sb = new StringBuffer();
		while ((ch = reader.read()) != -1) {
			sb.append((char) ch);
		}
		
		if (reader.readLine().indexOf("200") == -1) {// 读取web服务器返回的数据，判断请求码是否为200，如果不是200，代表请求失败
			sendMessage(UPLOAD_SUCCESS_CODE, "上传结果："
					+ sb);
		}*/
		outStream.flush();
		outStream.close();
		socket.close();
		//Toast.makeText(context, "上传录音成功", Toast.LENGTH_LONG).show();
		return SUCCESS;
	}
	
	public static String uploadFile(String imagePath, Map<String, String> params) throws Exception {
		final String BOUNDARY = "---------------------------7da2137580612"; // 数据分隔线
		final String endline = "--" + BOUNDARY + "--\r\n";// 数据结束标志

		int fileDataLength = 0;
		// 得到文件类型数据的总长度
		if(!StringUtil.isNull(imagePath)){
			File uploadFile = new File(imagePath);
			StringBuilder fileExplain = new StringBuilder();
			fileExplain.append("--");
			fileExplain.append(BOUNDARY);
			fileExplain.append("\r\n");
			fileExplain
					.append("Content-Disposition: form-data;name=\"file1\";filename=\""
								+ uploadFile.getName() + "\"\r\n");
			fileExplain.append("Content-Type: " + "multipart/form-data;"
					+ "\r\n\r\n");
			fileExplain.append("\r\n");
			fileDataLength += fileExplain.length();
			if (uploadFile.exists()) {
				fileDataLength += uploadFile.length();
			} else {
				fileDataLength += uploadFile.length();
			}
		}
		StringBuilder textEntity = new StringBuilder();
		for (Map.Entry<String, String> entry : params.entrySet()) {// 构造文本类型参数的实体数据
			textEntity.append("--");
			textEntity.append(BOUNDARY);
			textEntity.append("\r\n");
			textEntity.append("Content-Disposition: form-data; name=\""
					+ entry.getKey() + "\"\r\n\r\n");
			textEntity.append(entry.getValue());
			textEntity.append("\r\n");
		}
		// 计算传输给服务器的实体数据总长度
		int dataLength = textEntity.toString().getBytes().length
				+ fileDataLength + endline.getBytes().length;

		URL url = new URL(Constants.getUrl() + "/UploadPhotoServlet");
		int port = url.getPort() == -1 ? 80 : url.getPort();
		Socket socket = new Socket(InetAddress.getByName(url.getHost()), port);
		OutputStream outStream = socket.getOutputStream();
		// 下面完成HTTP请求头的发送
		String requestmethod = "POST " + url.getPath() + " HTTP/1.1\r\n";
		outStream.write(requestmethod.getBytes());
		String accept = "Accept: image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*\r\n";
		outStream.write(accept.getBytes());
		String language = "Accept-Language: zh-CN\r\n";
		outStream.write(language.getBytes());
		String contenttype = "Content-Type: multipart/form-data; boundary="
				+ BOUNDARY + "\r\n";
		outStream.write(contenttype.getBytes());
		String contentlength = "Content-Length: " + dataLength + "\r\n";
		outStream.write(contentlength.getBytes());
		String alive = "Connection: Keep-Alive\r\n";
		outStream.write(alive.getBytes());
		String host = "Host: " + url.getHost() + ":" + port + "\r\n";
		outStream.write(host.getBytes());
		// 写完HTTP请求头后根据HTTP协议再写一个回车换行
		outStream.write("\r\n".getBytes());
		// 把所有文本类型的实体数据发送出来
		outStream.write(textEntity.toString().getBytes());
		// 把所有文件类型的实体数据发送出来
		if(!StringUtil.isNull(imagePath)) {
			File uploadFile = new File(imagePath);
			StringBuilder fileEntity = new StringBuilder();
			fileEntity.append("--");
			fileEntity.append(BOUNDARY);
			fileEntity.append("\r\n");
			fileEntity
					.append("Content-Disposition: form-data;name=\"file1\";filename=\""
							+ uploadFile.getName() + "\"\r\n");
			fileEntity.append("Content-Type: " + "multipart/form-data;"
					+ "\r\n\r\n");
			outStream.write(fileEntity.toString().getBytes());
			if (uploadFile != null) {
				byte[] buffer = new byte[8192];
				int len = 0;
				FileInputStream fStream = new FileInputStream(uploadFile);
				while ((len = fStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, len);
				}
			}
			outStream.write("\r\n".getBytes());
		}
		
		// 下面发送数据结束标志，表示数据已经结束
		outStream.write(endline.getBytes());

		/*BufferedReader reader = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		int ch;
		StringBuffer sb = new StringBuffer();
		while ((ch = reader.read()) != -1) {
			sb.append((char) ch);
		}*/
		
		/*if (reader.readLine().indexOf("200") == -1) {// 读取web服务器返回的数据，判断请求码是否为200，如果不是200，代表请求失败
			sendMessage(UPLOAD_SUCCESS_CODE, "上传结果："
					+ sb);
		}*/
		outStream.flush();
		outStream.close();
		socket.close();
		//Toast.makeText(context, "上传录音成功", Toast.LENGTH_LONG).show();
		return SUCCESS;
	}
	
	public static String uploadVoice(String imagePath, Map<String, String> params) throws Exception {
		final String BOUNDARY = "---------------------------7da2137580612"; // 数据分隔线
		final String endline = "--" + BOUNDARY + "--\r\n";// 数据结束标志

		int fileDataLength = 0;
		// 得到文件类型数据的总长度
		if(!StringUtil.isNull(imagePath)){
			File uploadFile = new File(imagePath);
			StringBuilder fileExplain = new StringBuilder();
			fileExplain.append("--");
			fileExplain.append(BOUNDARY);
			fileExplain.append("\r\n");
			fileExplain
					.append("Content-Disposition: form-data;name=\"file1\";filename=\""
								+ uploadFile.getName() + "\"\r\n");
			fileExplain.append("Content-Type: " + "multipart/form-data;"
					+ "\r\n\r\n");
			fileExplain.append("\r\n");
			fileDataLength += fileExplain.length();
			if (uploadFile.exists()) {
				fileDataLength += uploadFile.length();
			} else {
				fileDataLength += uploadFile.length();
			}
		}
		StringBuilder textEntity = new StringBuilder();
		for (Map.Entry<String, String> entry : params.entrySet()) {// 构造文本类型参数的实体数据
			textEntity.append("--");
			textEntity.append(BOUNDARY);
			textEntity.append("\r\n");
			textEntity.append("Content-Disposition: form-data; name=\""
					+ entry.getKey() + "\"\r\n\r\n");
			textEntity.append(entry.getValue());
			textEntity.append("\r\n");
		}
		// 计算传输给服务器的实体数据总长度
		int dataLength = textEntity.toString().getBytes().length
				+ fileDataLength + endline.getBytes().length;

		URL url = new URL(Constants.getUrl() + "/UploadVoiceServlet");
		int port = url.getPort() == -1 ? 80 : url.getPort();
		Socket socket = new Socket(InetAddress.getByName(url.getHost()), port);
		OutputStream outStream = socket.getOutputStream();
		// 下面完成HTTP请求头的发送
		String requestmethod = "POST " + url.getPath() + " HTTP/1.1\r\n";
		outStream.write(requestmethod.getBytes());
		String accept = "Accept: image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*\r\n";
		outStream.write(accept.getBytes());
		String language = "Accept-Language: zh-CN\r\n";
		outStream.write(language.getBytes());
		String contenttype = "Content-Type: multipart/form-data; boundary="
				+ BOUNDARY + "\r\n";
		outStream.write(contenttype.getBytes());
		String contentlength = "Content-Length: " + dataLength + "\r\n";
		outStream.write(contentlength.getBytes());
		String alive = "Connection: Keep-Alive\r\n";
		outStream.write(alive.getBytes());
		String host = "Host: " + url.getHost() + ":" + port + "\r\n";
		outStream.write(host.getBytes());
		// 写完HTTP请求头后根据HTTP协议再写一个回车换行
		outStream.write("\r\n".getBytes());
		// 把所有文本类型的实体数据发送出来
		outStream.write(textEntity.toString().getBytes());
		// 把所有文件类型的实体数据发送出来
		if(!StringUtil.isNull(imagePath)) {
			File uploadFile = new File(imagePath);
			StringBuilder fileEntity = new StringBuilder();
			fileEntity.append("--");
			fileEntity.append(BOUNDARY);
			fileEntity.append("\r\n");
			fileEntity
					.append("Content-Disposition: form-data;name=\"file1\";filename=\""
							+ uploadFile.getName() + "\"\r\n");
			fileEntity.append("Content-Type: " + "multipart/form-data;"
					+ "\r\n\r\n");
			outStream.write(fileEntity.toString().getBytes());
			if (uploadFile != null) {
				byte[] buffer = new byte[8192];
				int len = 0;
				FileInputStream fStream = new FileInputStream(uploadFile);
				while ((len = fStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, len);
				}
			}
			outStream.write("\r\n".getBytes());
		}
		
		// 下面发送数据结束标志，表示数据已经结束
		outStream.write(endline.getBytes());

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		int ch;
		StringBuffer sb = new StringBuffer();
		while ((ch = reader.read()) != -1) {
			sb.append((char) ch);
		}
		outStream.flush();
		outStream.close();
		socket.close();
		if (sb.indexOf("200") != -1) {// 读取web服务器返回的数据，判断请求码是否为200，如果不是200，代表请求失败
			File uploadFile = new File(imagePath);
			uploadFile.delete();
			System.out.println("删除成功");
		}
		
		//Toast.makeText(context, "上传录音成功", Toast.LENGTH_LONG).show();
		return SUCCESS;
	}
	
	/**
	 * 下面是一个自定义的回调函数，用到回调上传文件是否完成
	 * 
	 * @author shimingzheng
	 * 
	 */
	public static interface OnUploadProcessListener {
		/**
		 * 上传响应
		 * @param responseCode
		 * @param message
		 */
		void onUploadDone(int responseCode, String message);
		/**
		 * 上传中
		 * @param uploadSize
		 */
		void onUploadProcess(int uploadSize);
		/**
		 * 准备上传
		 * @param fileSize
		 */
		void initUpload(int fileSize);
	}
	
	/**
	 * 单例模式获取上传工具类
	 * @return
	 */
	public static HttpAssist getInstance() {
		if (null == httpAssist) {
			httpAssist = new HttpAssist();
		}
		return httpAssist;
	}
	
	private static OnUploadProcessListener onUploadProcessListener;
	
	public void setOnUploadProcessListener(
			OnUploadProcessListener onUploadProcessListener) {
		this.onUploadProcessListener = onUploadProcessListener;
	}
	
	/**
	 * 发送上传结果
	 * @param responseCode
	 * @param responseMessage
	 */
	private static void sendMessage(int responseCode,String responseMessage)
	{
		onUploadProcessListener.onUploadDone(responseCode, responseMessage);
	}
	
	public int getReadTimeOut() {
		return readTimeOut;
	}

	public void setReadTimeOut(int readTimeOut) {
		this.readTimeOut = readTimeOut;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
	/**
	 * 获取上传使用的时间
	 * @return
	 */
	public static int getRequestTime() {
		return requestTime;
	}
}
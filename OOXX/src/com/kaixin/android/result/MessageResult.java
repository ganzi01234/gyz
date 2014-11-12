package com.kaixin.android.result;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 通话数据的实体
 * 
 * @author gyz
 * 
 */
public class MessageResult implements Parcelable {
	/**
	 * 短信的编号
	 */
	private String mid;
	/**
	 * 短信的类型
	 */
	private String title;
	/**
	 * 短信的内容
	 */
	private String content;
	/**
	 * 短信的时间
	 */
	private String time;
	/**
	 * 短信的信息
	 */
	private String info;

	
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	
	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mid);
		dest.writeString(title);
		dest.writeString(content);
		dest.writeString(time);
	}

	public static final Parcelable.Creator<MessageResult> CREATOR = new Parcelable.Creator<MessageResult>() {

		public MessageResult createFromParcel(Parcel source) {
			MessageResult result = new MessageResult();
			result.setMid(source.readString());
			result.setTitle(source.readString());
			result.setContent(source.readString());
			result.setTime(source.readString());
			return result;
		}

		public MessageResult[] newArray(int size) {
			return new MessageResult[size];
		}
	};


	@Override
	public int describeContents() {
		return 0;
	}
}

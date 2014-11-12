package com.kaixin.android.result;
/**
 * 附近的人数据实体类
 * @author gyz
 *
 */
public class NearbyPeopleResult {
	/**
	 * 用户的ID
	 */
	private String uid;
	/**
	 * 用户的账号
	 */
	private String email;
	/**
	 * 用户的名字
	 */
	private String name;
	/**
	 * 用户的头像
	 */
	private String avatar;
	/**
	 * 上传时间
	 */
	private String time;
	/**
	 * 间隔距离
	 */
	private String distance;
	/**
	 * 是否包含图片
	 */
	private boolean picture;
	/**
	 * 地理位置名称
	 */
	private String location;
	/**
	 * 地理经度
	 */
	private String latitude;
	/**
	 * 地理纬度
	 */
	private String longitude;
	
	
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public boolean isPicture() {
		return picture;
	}

	public void setPicture(boolean picture) {
		this.picture = picture;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}

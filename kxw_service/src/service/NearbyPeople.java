package service;
/**
 * ������������ʵ����
 * @author gyz
 *
 */
public class NearbyPeople {
	/**
	 * �û���ID
	 */
	private String uid;
	/**
	 * �û�������
	 */
	private String name;
	/**
	 * �û���ͷ��
	 */
	private String avatar;
	/**
	 * �û����˺�
	 */
	private String email;
	/**
	 * �ϴ�ʱ��
	 */
	private String time;
	/**
	 * �������
	 */
	private double distance;
	/**
	 * �Ƿ����ͼƬ
	 */
	private boolean picture;
	/**
	 * ����λ������
	 */
	private String location;
	/**
	 * ������
	 */
	private String latitude;
	/**
	 * ����γ��
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

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
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
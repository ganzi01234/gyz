package service;

/**
 * ����λ������ʵ����
 * 
 * @author gyz
 * 
 */
public class LocationResult {
	/**
	 * ����λ������
	 */
	private String name;
	/**
	 * ����λ�õص�
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
	/**
	 * ʱ��
	 */
	private String time;
	
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}

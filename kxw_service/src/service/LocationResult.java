package service;

/**
 * 地理位置数据实体类
 * 
 * @author gyz
 * 
 */
public class LocationResult {
	/**
	 * 地理位置名称
	 */
	private String name;
	/**
	 * 地理位置地点
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
	/**
	 * 时间
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

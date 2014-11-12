package service;

import java.util.List;


/**
 * 附近的照片数据实体类
 * 
 * @author gyz
 * 
 */
public class NearbyPhoto {
	/**
	 * 照片ID
	 */
	private String pid;
	/**
	 * 照片内容
	 */
	private String image;
	/**
	 * 照片标题
	 */
	private String title;
	/**
	 * 照片数量
	 */
	private int count;
	/**
	 * 照片地点
	 */
	private String location;
	/**
	 * 间隔距离
	 */
	private double distance;
	/**
	 * 照片内容
	 */
	private List<NearbyPhotoDetail> images;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int describeContents() {
		return 0;
	}

	public List<NearbyPhotoDetail> getImages() {
		return images;
	}

	public void setImages(List<NearbyPhotoDetail> images) {
		this.images = images;
	}
	
	
}
